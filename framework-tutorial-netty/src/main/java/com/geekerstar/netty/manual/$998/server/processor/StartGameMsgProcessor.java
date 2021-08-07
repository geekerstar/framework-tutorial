package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.OperationNotification;
import com.geekerstar.netty.manual.$998.common.msg.StartGameMsg;
import com.geekerstar.netty.manual.$998.common.msg.TableNotification;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import com.geekerstar.netty.manual.$998.util.CardUtils;
import com.geekerstar.netty.manual.$998.util.MsgUtils;
import com.geekerstar.netty.manual.$998.util.OperationUtils;

import java.util.Arrays;
import java.util.Collections;

public class StartGameMsgProcessor implements MahjongProcessor<StartGameMsg> {
    @Override
    public void process(StartGameMsg msg) {
        Table table = msg.getTable();
        table.setStatus(Table.STATUS_STARTING);

        // 游戏即将开始
        TableNotification tableNotification = new TableNotification();
        tableNotification.setTable(table);
        MsgUtils.sendTableNotification(tableNotification, false);

        table.incrementZhuangPos();

        // 洗牌
        DataManager.putCards(table.getId(), CardUtils.initCards());

        // 发牌（0号玩家作为庄家，庄家14张，其他13张）
        Player[] players = table.getPlayers();
        for (int i = 0; i < players.length; i++) {
            // 发牌
            byte[] cards = new byte[14];
            for (int j = 0; j < 13; j++) {
                cards[j] = DataManager.pollFirstCard(table.getId());
            }
            if (i == 0) {
                cards[13] = DataManager.pollFirstCard(table.getId());
            }
            // 排序
            Arrays.sort(cards);
            players[i].setCards(cards);
        }

        // 设置初始状态
        table.setChuPos(table.getZhuangPos());
        table.setSequence(0);
        table.setSubStatus(Table.SUBSTATUS_WAITING_CHU);

        // 通知所有玩家
        table.setStatus(Table.STATUS_PLAYING);
        MsgUtils.sendTableNotification(tableNotification, true);

        // 通知所有玩家等待庄家出牌，前端把光标指向出牌的位置，并提示此位置玩家出牌
        OperationNotification operationNotification = new OperationNotification();
        operationNotification.setOperation(OperationUtils.OPERATION_CHU);
        operationNotification.setOperationPos(table.getChuPos());
        operationNotification.setSequence(table.getSequence());
        // 首轮出牌的玩家多10秒，因为前端要播放发牌动画
        operationNotification.setDelayTime(OperationUtils.OPERATION_DEPLAY_TIME + 15);
        DataManager.addTableWaitingOperationNotification(table.getId(), Collections.singletonList(operationNotification));
        MsgUtils.send2Table(table, operationNotification);

        // 设置倒计时，若倒计时结束出牌玩家还未出牌，则帮其自动出一张牌
        OperationRequestProcessor.chuCountDown(table, operationNotification.getDelayTime());
    }
}
