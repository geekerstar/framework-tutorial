package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.*;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import com.geekerstar.netty.manual.$998.util.IdUtils;
import com.geekerstar.netty.manual.$998.util.MsgUtils;
import com.geekerstar.netty.manual.$998.util.OperationUtils;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OperationRequestProcessor implements MahjongProcessor<OperationRequest> {

    @Override
    public void process(OperationRequest msg) {
        Long tableId = DataManager.CURRENT_TABLE_ID.get();
        Table table = DataManager.getTableById(tableId);
        if (table == null) {
            log.error("table not exist, tableId={}", tableId);
            return;
        }
        if (table.getStatus() == Table.STATUS_WAITING) {
            log.error("table status is waiting");
            return;
        }
        if (table.getStatus() == Table.STATUS_GAME_OVER) {
            log.error("table game is over");
            return;
        }
        // 检查序列号是否一致，不一致表示过时的消息，直接丢弃
        if (msg.getSequence() != table.getSequence()) {
            log.error("msg sequence error, msgSequence={}, tableSequence={}", msg.getSequence(), table.getSequence());
            return;
        }
        // 检查桌子状态对不对
        int subStatus = table.getSubStatus();
        if (subStatus == Table.SUBSTATUS_WAITING_CHU && msg.getOperation() != OperationUtils.OPERATION_CHU) {
            log.error("table substatus error, substatus={}, operation={}", subStatus, msg.getOperation());
            return;
        }
        if (subStatus == Table.SUBSTATUS_WAITING_OPERATE && msg.getOperation() == OperationUtils.OPERATION_CHU) {
            log.error("table substatus error, substatus={}, operation={}", subStatus, msg.getOperation());
            return;
        }

        // 检查当前请求是否合法
        if (!checkOperationAllowed(msg, table)) {
            log.error("illegal request for not allowed");
            return;
        }

        // 检查同一个玩家是否重复发消息
        if (checkOperationDuplicated(msg, table)) {
            log.error("illegal request for duplicated");
            return;
        }

        msg.setOperationPos(DataManager.currentPlayer().getPos());

        // 添加到等待队列中，等待所有玩家都操作完了再决定执行哪个操作
        // todo 这里有个优化的点，其实操作是有先后顺序的
        // todo 比如，胡>杠>碰>吃，对于同一个操作离玩家越近优先级越高，比如两家都可以胡，下家>对家
        // todo 所以，实际上并不需要所有玩家都操作完，只要高优先级的玩家操作了就可以直接下一步了
        if (DataManager.addTableWaitingOperationRequest(tableId, msg)) {
            // 所有玩家都操作了，则处理这些请求
            dealOperationRequest(table);
        }

    }

    private boolean checkOperationAllowed(OperationRequest msg, Table table) {
        Player player = DataManager.currentPlayer();
        // 检查是否有操作权限
        List<OperationNotification> tableWaitingOperationNotification = DataManager.getTableWaitingOperationNotification(table.getId());
        for (OperationNotification operationNotification : tableWaitingOperationNotification) {
            if (operationNotification.getOperationPos() == player.getPos()) {
                // 比如，一个人既可以碰也可以胡，那么，notification中的是 4|16=100|1000=1100
                // 他选择的是胡，那么，request中的是 16=1000，此时两者做与操作，结果应等于后者
                if ((operationNotification.getOperation() & msg.getOperation()) == msg.getOperation() || msg.getOperation() == OperationUtils.OPERATION_CANCEL) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean checkOperationDuplicated(OperationRequest msg, Table table) {
        // 检查是否重复操作
        List<OperationRequest> tableWaitingOperationRequest = DataManager.getTableWaitingOperationRequest(table.getId());
        for (OperationRequest operationRequest : tableWaitingOperationRequest) {
            if (operationRequest.getOperationPos() == msg.getOperationPos()) {
                return true;
            }
        }
        return false;
    }

    private void chu(Table table, OperationRequest msg) {
        // 检查是不是轮到该玩家操作
        // channel为空表示是超时自动操作
        Channel channel = DataManager.CURRENT_CHANNEL.get();
        Player chuPlayer = table.chuPlayer();
        if (channel != null && chuPlayer.getId() != DataManager.currentPlayer().getId()) {
            return;
        }

        // 检查出牌是否正确
        if (msg.getCards().length != 1) {
            log.error("chu card must be length 1, playerId={}", chuPlayer.getId());
            return;
        }
        if (!chuPlayer.containCards(msg.getCards())) {
            log.error("chu card not found in player, playerId={}, chuCard={}", chuPlayer.getId(), msg.getCards());
            return;
        }

        // 出牌，即把这张牌从玩家手里移除
        byte chuCard = msg.getCards()[0];
        chuPlayer.removeCard(chuCard);

        // 通知所有玩家谁出了一张什么牌
        OperationResultNotification operationResultNotification = new OperationResultNotification();
        operationResultNotification.setOperation(OperationUtils.OPERATION_CHU);
        operationResultNotification.setOperationPos(table.getChuPos());
        operationResultNotification.setCards(new byte[]{chuCard});
        MsgUtils.send2Table(table, operationResultNotification);

        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        tableNotification.setOperation(OperationUtils.OPERATION_CHU);
        MsgUtils.sendTableNotification(tableNotification, true);

        // 检查其它玩家有没有可以吃碰杠胡这张牌，并通知对应的玩家
        if (checkOtherCanOperate(chuPlayer, chuCard, table)) {
            table.setSubStatus(Table.SUBSTATUS_WAITING_OPERATE);
            // 设置倒计时，倒计时结束后还没有人操作，则进行下一步操作
            final int preSequence = table.getSequence();
            EventExecutor executor = DataManager.CURRENT_EXECUTOR.get();
            executor.schedule(() -> {
                DataManager.CURRENT_TABLE_ID.set(table.getId());
                DataManager.CURRENT_CHANNEL.set(channel);
                DataManager.CURRENT_EXECUTOR.set(executor);
                try {
                    // 不一致，说明有人操作了，丢弃此消息
                    if (preSequence == table.getSequence()) {
                        // 也可能是部分人没操作导致了超时
                        if (!checkPartOperation(table)) {
                            moveToNext(table);
                        }
                    }
                } finally {
                    DataManager.CURRENT_TABLE_ID.remove();
                    DataManager.CURRENT_CHANNEL.remove();
                    DataManager.CURRENT_EXECUTOR.remove();
                }
            }, OperationUtils.OPERATION_DEPLAY_TIME, TimeUnit.SECONDS);
        } else {
            // 如果所有玩家都没有操作，则把光标移到下一个玩家，让其摸牌，并通知其出牌，并启动倒计时
            moveToNext(table);
        }
    }

    private boolean checkPartOperation(Table table) {
        // 部分人操作导致超时
        List<OperationRequest> tableWaitingOperationRequestList = DataManager.getTableWaitingOperationRequest(table.getId());
        if (tableWaitingOperationRequestList != null && !tableWaitingOperationRequestList.isEmpty()) {
            dealOperationRequest(table);
            return true;
        }

        return false;
    }

    private void dealOperationRequest(Table table) {
        table.incrementSequence();

        List<OperationNotification> notificationList = DataManager.getTableWaitingOperationNotification(table.getId());
        List<OperationRequest> requestList = DataManager.getTableWaitingOperationRequest(table.getId());
        // 清除缓存，后面的出牌又要使用这两个list
        DataManager.clearTableWaitingOperation(table.getId());

        int allOperation = 0;
        for (OperationNotification operationNotification : notificationList) {
            allOperation |= operationNotification.getOperation();
        }

        int chuPos = table.getChuPos();
        // 把requestList按座位号排序，按离出牌玩家从近到远排序
        requestList.sort((o1, o2) -> {
            int o1DeltaPos = o1.getOperationPos() - chuPos;
            if (o1DeltaPos < 0) {
                o1DeltaPos += table.getMaxPlayerNum();
            }
            int o2DeltaPos = o2.getOperationPos() - chuPos;
            if (o2DeltaPos < 0) {
                o2DeltaPos += table.getMaxPlayerNum();
            }
            return o1DeltaPos - o2DeltaPos;
        });

        // 检查是否是出牌
        if ((allOperation & OperationUtils.OPERATION_CHU) == OperationUtils.OPERATION_CHU) {
            for (OperationRequest operationRequest : requestList) {
                if (operationRequest.getOperation() == OperationUtils.OPERATION_CHU) {
                    chu(table, operationRequest);
                    return;
                }
            }
        }

        // 检查是否包含胡
        if ((allOperation & OperationUtils.OPERATION_HU) == OperationUtils.OPERATION_HU) {
            // 从requestList找到第一个hu，让他胡
            for (OperationRequest operationRequest : requestList) {
                if (operationRequest.getOperation() == OperationUtils.OPERATION_HU) {
                    hu(table, operationRequest);
                    return;
                }
            }
        }

        // 检查是否包含杠
        if ((allOperation & OperationUtils.OPERATION_GANG) == OperationUtils.OPERATION_GANG) {
            // 从requestList找到第一个gang，让他杠
            for (OperationRequest operationRequest : requestList) {
                if (operationRequest.getOperation() == OperationUtils.OPERATION_GANG) {
                    gang(table, operationRequest);
                    return;
                }
            }
        }

        // 检查是否包含碰
        if ((allOperation & OperationUtils.OPERATION_PENG) == OperationUtils.OPERATION_PENG) {
            // 从requestList找到第一个peng，让他碰
            for (OperationRequest operationRequest : requestList) {
                if (operationRequest.getOperation() == OperationUtils.OPERATION_PENG) {
                    peng(table, operationRequest);
                    return;
                }
            }
        }

        // 以上都不包含，说明都是取消的操作，则移到到下一个人摸牌、出牌等
        moveToNext(table);
    }

    private void moveToNext(Table table) {
        // 出牌光标移到下一个玩家
        table.moveToNext();
        // 下一个玩家摸一张牌，并通知他
        Byte card = DataManager.pollFirstCard(table.getId());
        if (card == null) {
            // 牌摸完了，通知所有玩家游戏结束，并清理数据
            gameOver(table, null);
            return;
        }
        grabCard(table, card);
    }

    /**
     * 出牌倒计时
     */
    public static void chuCountDown(Table table, long delayTime) {
        table.setSubStatus(Table.SUBSTATUS_WAITING_CHU);
        int sequence = table.getSequence();
        Channel channel = DataManager.getChannelByPlayerId(table.chuPlayer().getId());
        EventExecutor executor = DataManager.CURRENT_EXECUTOR.get();
        executor.schedule(() -> {
            DataManager.CURRENT_TABLE_ID.set(table.getId());
            DataManager.CURRENT_CHANNEL.set(channel);
            DataManager.CURRENT_EXECUTOR.set(executor);
            try {
                // 模拟一个OperationRequest
                OperationRequest operationRequest = new OperationRequest();
                operationRequest.setSequence(sequence);
                operationRequest.setOperation(OperationUtils.OPERATION_CHU);
                operationRequest.setCards(new byte[]{table.getPlayers()[table.getChuPos()].lastCard()});
                // 处理这个消息
                MahjongProcessor.processMsg(operationRequest);
            } finally {
                DataManager.CURRENT_TABLE_ID.remove();
                DataManager.CURRENT_CHANNEL.remove();
                DataManager.CURRENT_EXECUTOR.remove();
            }
        }, delayTime, TimeUnit.SECONDS);
    }

    private void gameOver(Table table, Player winner) {
        table.setStatus(Table.STATUS_GAME_OVER);
        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        // 可以看到其他玩家手里的牌
        MsgUtils.sendTableNotification(tableNotification, false);

        SettleNotification settleNotification = new SettleNotification();
        if (winner != null) {
            // 计算得分
            Player[] players = table.getPlayers();
            for (Player player : players) {
                if (player.getId() == winner.getId()) {
                    player.setScore(player.getScore() + table.getBaseScore() * (table.getMaxPlayerNum() - 1));
                } else {
                    player.setScore(player.getScore() - table.getBaseScore());
                }
            }
            settleNotification.setWinnerPos(winner.getPos());
            settleNotification.setBaseScore(table.getBaseScore());
        } else {
            settleNotification.setWinnerPos(-1);
            settleNotification.setBaseScore(table.getBaseScore());
        }

        // 发送结算消息
        MsgUtils.send2Table(table, settleNotification);

        // 清理资源
        DataManager.clearTable(table);
    }

    private boolean checkOtherCanOperate(Player chuPlayer, byte chuCard, Table table) {
        // 如果有可以操作的玩家，通知所有玩家等待，并启动倒计时
        Player[] players = table.getPlayers();
        List<OperationNotification> notificationList = new ArrayList<>();
        Player player = chuPlayer;
        for (; ; ) {
            player = table.nextPlayer(player);
            if (player != null) {
                if (player.getId() == chuPlayer.getId()) {
                    break;
                }
                int operation = 0;
                byte[] cards = player.getCards();
                // 检查碰
                if (player.containCards(chuCard, chuCard)) {
                    operation |= OperationUtils.OPERATION_PENG;
                }

                // 检查明杠
                if (player.containCards(chuCard, chuCard, chuCard)) {
                    operation |= OperationUtils.OPERATION_GANG;
                }

                // todo 检查胡（规则复杂，不便于公开，这里使用随机判断是否可胡）
                if (IdUtils.randomInt(10) == 1) {
                    operation |= OperationUtils.OPERATION_HU;
                }

                if (operation != 0) {
                    OperationNotification notification = new OperationNotification();
                    notification.setSequence(table.getSequence());
                    notification.setOperation(operation);
                    notification.setOperationPos(player.getPos());
                    notification.setCard(chuCard);
                    notification.setDelayTime(OperationUtils.OPERATION_DEPLAY_TIME);

                    notificationList.add(notification);
                }
            }
        }

        if (!notificationList.isEmpty()) {
            List<Long> notificationPlayerIds = new ArrayList<>();
            for (OperationNotification notification : notificationList) {
                Player notificationPlayer = players[notification.getOperationPos()];
                notificationPlayerIds.add(notificationPlayer.getId());
                MsgUtils.send2Player(notificationPlayer, notification);
            }
            // 针对全体玩家，再发送一个等待的消息，让没收到可操作消息的玩家知道要等待
            OperationNotification notification = new OperationNotification();
            notification.setDelayTime(OperationUtils.OPERATION_DEPLAY_TIME);
            notification.setOperationPos(-1);
            // 其它字段没有意义
            MsgUtils.send2TableExcept(table, notification, notificationPlayerIds);

            // 记录下来这个list，后面要根据收到的消息做出不同的响应
            DataManager.addTableWaitingOperationNotification(table.getId(), notificationList);
        }

        return !notificationList.isEmpty();
    }

    private void peng(Table table, OperationRequest msg) {
        // 从自己手里移除两张一样的牌（这里没有做牌的合法性检验）
        Player[] players = table.getPlayers();
        Player player = players[msg.getOperationPos()];
        player.removeCard(msg.getCards());
        player.addPeng(msg.getCards()[0]);

        // 出牌光标移到当前玩家
        table.moveTo(msg.getOperationPos());

        // 通知所有玩家谁碰了牌
        OperationResultNotification operationResultNotification = new OperationResultNotification();
        operationResultNotification.setOperationPos(msg.getOperationPos());
        operationResultNotification.setOperation(msg.getOperation());
        operationResultNotification.setCards(msg.getCards());
        MsgUtils.send2Table(table, operationResultNotification);

        // 刷新牌桌
        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        tableNotification.setOperation(OperationUtils.OPERATION_PENG);
        MsgUtils.sendTableNotification(tableNotification, true);

        // 通知其出牌，并启动倒计时
        OperationNotification operationNotification = new OperationNotification();
        operationNotification.setOperation(OperationUtils.OPERATION_CHU);
        operationNotification.setOperationPos(table.getChuPos());
        operationNotification.setSequence(table.getSequence());
        operationNotification.setDelayTime(OperationUtils.OPERATION_DEPLAY_TIME);
        DataManager.addTableWaitingOperationNotification(table.getId(), Collections.singletonList(operationNotification));
        MsgUtils.send2Table(table, operationNotification);

        // 出牌倒计时
        chuCountDown(table, OperationUtils.OPERATION_DEPLAY_TIME);
    }

    private void gang(Table table, OperationRequest msg) {
        // 从自己手里移除三张一样的牌（这里没有做牌的合法性检验）
        Player[] players = table.getPlayers();
        Player player = players[msg.getOperationPos()];
        player.removeCard(msg.getCards());
        player.addGang(msg.getCards()[0]);

        // 通知所有玩家谁杠了牌
        OperationResultNotification operationResultNotification = new OperationResultNotification();
        operationResultNotification.setOperationPos(msg.getOperationPos());
        operationResultNotification.setOperation(msg.getOperation());
        operationResultNotification.setCards(msg.getCards());
        MsgUtils.send2Table(table, operationResultNotification);

        // 刷新牌桌
        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        tableNotification.setOperation(OperationUtils.OPERATION_GANG);
        MsgUtils.sendTableNotification(tableNotification, true);

        // 出牌光标移到当前玩家
        table.moveTo(msg.getOperationPos());
        // 摸一张牌
        Byte card = DataManager.pollLastCard(table.getId());
        if (card == null) {
            // 牌摸完了，通知所有玩家游戏结束，并清理数据
            gameOver(table, null);
            return;
        }
        grabCard(table, card);
    }

    private void grabCard(Table table, Byte card) {
        Player player = table.getPlayers()[table.getChuPos()];
        player.addCard(card);

        // 刷新牌桌
        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        tableNotification.setOperation(OperationUtils.OPERATION_GRAB);
        MsgUtils.sendTableNotification(tableNotification, true);

        // todo 假设摸牌强制杠和胡如果有的话
        if (IdUtils.randomInt(10) == 1) {
            gameOver(table, player);
            return;
        }

        // 检查暗杠
        if (player.containCards(card, card, card, card)) {
            player.removeCard(card, card, card, card);
            player.addGang(card);

            // 通知所有玩家谁杠了牌
            OperationResultNotification operationResultNotification = new OperationResultNotification();
            operationResultNotification.setOperationPos(player.getPos());
            operationResultNotification.setOperation(OperationUtils.OPERATION_GANG);
            operationResultNotification.setCards(new byte[]{card, card, card, card});
            MsgUtils.send2Table(table, operationResultNotification);

            // 刷新牌桌
            MsgUtils.sendTableNotification(tableNotification, true);

            // 摸一张牌
            Byte grabCard = DataManager.pollLastCard(table.getId());
            if (grabCard == null) {
                // 牌摸完了，通知所有玩家游戏结束，并清理数据
                gameOver(table, null);
                return;
            }
            grabCard(table, grabCard);
            return;
        }

        // todo 判断补杠

        // 通知其出牌，并启动倒计时
        OperationNotification operationNotification = new OperationNotification();
        operationNotification.setOperation(OperationUtils.OPERATION_CHU);
        operationNotification.setOperationPos(table.getChuPos());
        operationNotification.setSequence(table.getSequence());
        operationNotification.setDelayTime(OperationUtils.OPERATION_DEPLAY_TIME);
        DataManager.addTableWaitingOperationNotification(table.getId(), Collections.singletonList(operationNotification));
        MsgUtils.send2Table(table, operationNotification);

        // 出牌倒计时
        chuCountDown(table, OperationUtils.OPERATION_DEPLAY_TIME);
    }

    private void hu(Table table, OperationRequest msg) {
        Player winner = table.getPlayers()[msg.getOperationPos()];
        winner.addCard(msg.getCards()[0]);
        gameOver(table, winner);
    }
}
