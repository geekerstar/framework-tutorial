package com.geekerstar.netty.manual.$998.client.mock;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.*;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;
import com.geekerstar.netty.manual.$998.util.CardUtils;
import com.geekerstar.netty.manual.$998.util.MsgUtils;
import com.geekerstar.netty.manual.$998.util.OperationUtils;
import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoopGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MockClient {

    private static DefaultEventLoopGroup executorGroup;
    private static Channel channel;
    private static Table table;
    private static Player player;
    private static Scanner scanner = new Scanner(System.in);
    private static DefaultEventLoopGroup asyncExecutorGroup = new DefaultEventLoopGroup(1);

    public static void setExecutorGroup(DefaultEventLoopGroup executorGroup) {
        MockClient.executorGroup = executorGroup;
    }

    public static void setChannel(Channel channel) {
        MockClient.channel = channel;
    }

    public static void start() {
        login();
    }

    public static void login() {
        oneOperationExecute("\n请您登录，用户名和密码，以空格分隔：",
                (line) -> line.split(" ").length == 2,
                (line) -> {
                    String[] strings = line.split(" ");
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(strings[0]);
                    loginRequest.setPassword(strings[1]);
                    return loginRequest;
                });
    }

    public static void loginResponse(LoginResponse response) {
        if (response.isResult()) {
            player = response.getPlayer();
            System.out.println("\n恭喜您，登录成功，您的信息如下：\n" + player);
            afterLoginResponse();
        } else {
            System.out.println("\n登录失败：" + response.getMsg());
            login();
        }
    }

    private static void afterLoginResponse() {
        twoOperationExecute("\n请选择您要进行的操作：1. 创建房间，2. 加入房间",
                "\n正在为您创建房间，请稍候...",
                null,
                (line) -> {
                    CreateTableRequest request = new CreateTableRequest();
                    request.setBaseScore(5);
                    request.setPlayerNum(3);
                    return request;
                },
                "\n请输入您要加入的房间号：",
                (line) -> {
                    try {
                        Long.parseLong(line);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                },
                (line) -> {
                    EnterTableRequest request = new EnterTableRequest();
                    request.setTableId(Long.parseLong(line));
                    return request;
                }
        );
    }

    public static void createTableResponse(CreateTableResponse response) {
        table = response.getTable();
        System.out.println("\n恭喜您，创建房间成功，房间信息如下：\n" + table);
        System.out.println("\n请稍等，正在为您匹配玩家...");
    }

    public static void enterTableResponse(EnterTableResponse response) {
        if (response.isResult()) {
            System.out.println("\n恭喜您，加入房间成功，房间信息如下：\n" + response.getTable());
        } else {
            System.out.println("\n不好意思，加入房间失败：" + response.getMsg());
            afterLoginResponse();
        }
    }

    public static void tableNotification(TableNotification notification) {
        table = notification.getTable();
        for (Player p : table.getPlayers()) {
            if (p.getId() == player.getId()) {
                player = p;
            }
        }
        if (table.getStatus() == Table.STATUS_WAITING) {
            System.out.println("\n有人进入房间，刷新房间信息如下：\n" + table);
        } else if (table.getStatus() == Table.STATUS_STARTING) {
            System.out.println("\n所有玩家已就绪，游戏即将开始~~");
        } else if (table.getStatus() == Table.STATUS_PLAYING) {
            int operation = notification.getOperation();
            if (operation == OperationUtils.OPERATION_CHU) {
                System.out.println("\n刷新牌局信息（出）：");
            }
            if (operation == OperationUtils.OPERATION_PENG) {
                System.out.println("\n刷新牌局信息（碰）：");
            }
            if (operation == OperationUtils.OPERATION_GANG) {
                System.out.println("\n刷新牌局信息（杠）：");
            }
            if (operation == OperationUtils.OPERATION_GRAB) {
                System.out.println("\n刷新牌局信息（摸）：");
            }
            if (operation == OperationUtils.OPERATION_HU) {
                System.out.println("\n刷新牌局信息（胡）：");
            }
            printCardsOf(table);
        } else {
            System.out.println("\n游戏已结束！");
            System.out.println("\n各玩家手牌信息：");
            printCardsOf(table);
        }
    }

    public static void operationNotification(OperationNotification notification) {
        if (notification.getOperationPos() == player.getPos()) {
            if (notification.getOperation() == OperationUtils.OPERATION_CHU) {
                chu(notification);
            } else {
                // 其它操作有这么几种组合：
                // 碰 取消、杠 取消、胡 取消、碰 杠 取消、 碰 胡 取消、 杠 胡 取消、碰 杠 胡 取消
                String tips = "\n请您操作：";
                int i = 1;
                List<MsgBuilder> msgBuilderList = new ArrayList<>();
                if ((notification.getOperation() & OperationUtils.OPERATION_PENG) == OperationUtils.OPERATION_PENG) {
                    tips += i++ + ". 碰 ";
                    msgBuilderList.add(line -> {
                        OperationRequest operationRequest = new OperationRequest();
                        operationRequest.setOperation(OperationUtils.OPERATION_PENG);
                        operationRequest.setOperationPos(player.getPos());
                        operationRequest.setSequence(notification.getSequence());
                        operationRequest.setCards(new byte[]{notification.getCard(), notification.getCard()});
                        return operationRequest;
                    });
                }
                if ((notification.getOperation() & OperationUtils.OPERATION_GANG) == OperationUtils.OPERATION_GANG) {
                    tips += i++ + ". 杠 ";
                    msgBuilderList.add(line -> {
                        OperationRequest operationRequest = new OperationRequest();
                        operationRequest.setOperation(OperationUtils.OPERATION_GANG);
                        operationRequest.setOperationPos(player.getPos());
                        operationRequest.setSequence(notification.getSequence());
                        operationRequest.setCards(new byte[]{notification.getCard(), notification.getCard(), notification.getCard()});
                        return operationRequest;
                    });
                }
                if ((notification.getOperation() & OperationUtils.OPERATION_HU) == OperationUtils.OPERATION_HU) {
                    tips += i++ + ". 胡 ";
                    msgBuilderList.add(line -> {
                        OperationRequest operationRequest = new OperationRequest();
                        operationRequest.setOperation(OperationUtils.OPERATION_HU);
                        operationRequest.setOperationPos(player.getPos());
                        operationRequest.setSequence(notification.getSequence());
                        operationRequest.setCards(new byte[]{notification.getCard()});
                        return operationRequest;
                    });
                }
                tips += i + ". 取消（倒计时 " + notification.getDelayTime() + "秒）";
                msgBuilderList.add(line -> {
                    OperationRequest operationRequest = new OperationRequest();
                    operationRequest.setOperation(OperationUtils.OPERATION_CANCEL);
                    operationRequest.setOperationPos(player.getPos());
                    operationRequest.setSequence(notification.getSequence());
                    operationRequest.setCards(new byte[]{notification.getCard()});
                    return operationRequest;
                });

                if (i == 2) {
                    twoOperationExecute(tips, "", null, msgBuilderList.get(0), "", null, msgBuilderList.get(1));
                }
                if (i == 3) {
                    threeOperationExecute(tips, "", null, msgBuilderList.get(0), "", null, msgBuilderList.get(1), "", null, msgBuilderList.get(2));
                }
                if (i == 4) {
                    fourOperationExecute(tips, "", null, msgBuilderList.get(0), "", null, msgBuilderList.get(1), "", null, msgBuilderList.get(2), "", null, msgBuilderList.get(3));
                }
            }
        } else {
            if (notification.getOperation() == OperationUtils.OPERATION_CHU) {
                System.out.println("\n请等待 玩家" + (notification.getOperationPos() + 1) + " 出牌（倒计时 " + notification.getDelayTime() + "秒）");
            } else {
                System.out.println("\n请等待其他玩家操作（倒计时 " + notification.getDelayTime() + "秒）");
            }
        }
    }

    public static void operationResultNotification(OperationResultNotification notification) {
        if (notification.getOperationPos() == player.getPos()) {
            System.out.println("\n您 " + operation(notification.getOperation()) + " 了 " + formatCards(notification.getCards()));
        } else {
            System.out.println("\n玩家" + (notification.getOperationPos() + 1) + " " + operation(notification.getOperation()) + " 了 " + formatCards(notification.getCards()));
        }
    }

    public static void settleNotification(SettleNotification notification) {
        if (player.getPos() == notification.getWinnerPos()) {
            System.out.println("\n恭喜您，赢钱了赢钱了~~");
        } else {
            System.out.println("\n不好意思，输了哟，再接再厉~~");
        }
        for (Player player : table.getPlayers()) {
            if (player.getPos() == notification.getWinnerPos()) {
                System.out.println("玩家" + (player.getPos() + 1) + ": +" + notification.getBaseScore() * (table.getMaxPlayerNum() - 1));
            } else {
                System.out.println("玩家" + (player.getPos() + 1) + ": -" + notification.getBaseScore());
            }
        }
        afterLoginResponse();
    }

    private static String operation(int operation) {
        if (operation == OperationUtils.OPERATION_CHU) {
            return "出";
        }
        if (operation == OperationUtils.OPERATION_PENG) {
            return "碰";
        }
        if (operation == OperationUtils.OPERATION_GANG) {
            return "杠";
        }
        if (operation == OperationUtils.OPERATION_HU) {
            return "胡";
        }
        return null;
    }

    private static void chu(OperationNotification notification) {
        oneOperationExecute("\n请您出牌（倒计时 " + notification.getDelayTime() + "秒）：", line -> {
            try {
                int num = Integer.parseInt(line);
                if (num < 1 || num > 14) {
                    return false;
                }
                int i = 1;
                for (byte card : player.getCards()) {
                    if (card != 0) {
                        if (i++ == num) {
                            return true;
                        }
                    }
                }

            } catch (Exception e) {
                // just ignore
            }
            return false;
        }, line -> {
            int num = Integer.parseInt(line);

            byte card = 0;
            int i = 1;
            for (byte c : player.getCards()) {
                if (c != 0) {
                    if (i++ == num) {
                        card = c;
                        break;
                    }
                }
            }

            OperationRequest operationRequest = new OperationRequest();
            operationRequest.setOperation(OperationUtils.OPERATION_CHU);
            operationRequest.setOperationPos(player.getPos());
            operationRequest.setCards(new byte[]{card});
            operationRequest.setSequence(notification.getSequence());
            return operationRequest;
        }, true);
    }

    private static void printCardsOf(Table table) {
        Player[] players = table.getPlayers();
        for (Player player : players) {
            if (player.getId() == MockClient.player.getId()) {
                MockClient.player = player;
                System.out.println("你的牌：" + formatCards(player.getCards()));
            } else {
                System.out.println("玩家" + (player.getPos() + 1) + "的牌：" + formatCards(player.getCards()));
            }
        }
    }

    private static String formatCards(byte[] cards) {
        Arrays.sort(cards);
        List<String> list = new ArrayList<>();
        int i = 1;
        for (byte card : cards) {
            if (card != 0) {
                list.add(toChinese(card) + "(" + i++ + ")");
            }
        }
        return list.toString();
    }

    private static String toChinese(byte card) {
        if (CardUtils.isWan(card)) {
            return CardUtils.value(card) + "万";
        }

        if (CardUtils.isTiao(card)) {
            return CardUtils.value(card) + "条";
        }

        if (CardUtils.isTong(card)) {
            return CardUtils.value(card) + "筒";
        }

        return null;
    }

    private interface Conditin {
        boolean run(String line);
    }

    private interface MsgBuilder {
        MahjongMsg build(String line);
    }

    private static void oneOperationExecute(String tips, Conditin condition, MsgBuilder msgBuilder) {
        oneOperationExecute(tips, condition, msgBuilder, true);
    }

    private static void oneOperationExecute(String tips, Conditin condition, MsgBuilder msgBuilder, boolean async) {
        DefaultEventLoopGroup executors = executorGroup;
        if (async) {
            executors = asyncExecutorGroup;
        }

        executors.execute(() -> {
            System.out.println(tips);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (condition.run(line)) {
                    MahjongMsg msg = msgBuilder.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else {
                    System.out.println("错误的输入，请重新输入：");
                }
            }
        });
    }

    private static void twoOperationExecute(String tips, String tips1, Conditin condition1, MsgBuilder msgBuilder1, String tips2, Conditin condition2, MsgBuilder msgBuilder2) {
        executorGroup.execute(() -> {
            System.out.println(tips);
            boolean flag1 = false;
            boolean flag2 = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!flag1 && !flag2 && "1".equals(line)) {
                    flag1 = true;
                    System.out.println(tips1);
                    if (condition1 == null) {
                        MahjongMsg msg = msgBuilder1.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && "2".equals(line)) {
                    flag2 = true;
                    System.out.println(tips2);
                    if (condition2 == null) {
                        MahjongMsg msg = msgBuilder2.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (flag1 && condition1.run(line)) {
                    MahjongMsg msg = msgBuilder1.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag2 && condition2.run(line)) {
                    MahjongMsg msg = msgBuilder2.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else {
                    System.out.println("错误的输入，请重新输入：");
                }
            }
        });
    }

    private static void threeOperationExecute(String tips, String tips1, Conditin condition1, MsgBuilder msgBuilder1,
                                              String tips2, Conditin condition2, MsgBuilder msgBuilder2,
                                              String tips3, Conditin condition3, MsgBuilder msgBuilder3) {
        executorGroup.execute(() -> {
            System.out.println(tips);
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!flag1 && !flag2 && !flag3 && "1".equals(line)) {
                    flag1 = true;
                    System.out.println(tips1);
                    if (condition1 == null) {
                        MahjongMsg msg = msgBuilder1.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && !flag3 && "2".equals(line)) {
                    flag2 = true;
                    System.out.println(tips2);
                    if (condition2 == null) {
                        MahjongMsg msg = msgBuilder2.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && !flag3 && "3".equals(line)) {
                    flag2 = true;
                    System.out.println(tips3);
                    if (condition3 == null) {
                        MahjongMsg msg = msgBuilder3.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (flag1 && condition1.run(line)) {
                    MahjongMsg msg = msgBuilder1.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag2 && condition2.run(line)) {
                    MahjongMsg msg = msgBuilder2.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag3 && condition3.run(line)) {
                    MahjongMsg msg = msgBuilder3.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else {
                    System.out.println("错误的输入，请重新输入：");
                }
            }
        });
    }

    private static void fourOperationExecute(String tips, String tips1, Conditin condition1, MsgBuilder msgBuilder1,
                                             String tips2, Conditin condition2, MsgBuilder msgBuilder2,
                                             String tips3, Conditin condition3, MsgBuilder msgBuilder3,
                                             String tips4, Conditin condition4, MsgBuilder msgBuilder4) {
        executorGroup.execute(() -> {
            System.out.println(tips);
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            boolean flag4 = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!flag1 && !flag2 && !flag3 && !flag4 && "1".equals(line)) {
                    flag1 = true;
                    System.out.println(tips1);
                    if (condition1 == null) {
                        MahjongMsg msg = msgBuilder1.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && !flag3 && !flag4 && "2".equals(line)) {
                    flag2 = true;
                    System.out.println(tips2);
                    if (condition2 == null) {
                        MahjongMsg msg = msgBuilder2.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && !flag3 && !flag4 && "3".equals(line)) {
                    flag2 = true;
                    System.out.println(tips3);
                    if (condition3 == null) {
                        MahjongMsg msg = msgBuilder3.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (!flag1 && !flag2 && !flag3 && !flag4 && "4".equals(line)) {
                    flag2 = true;
                    System.out.println(tips4);
                    if (condition4 == null) {
                        MahjongMsg msg = msgBuilder4.build(line);
                        MsgUtils.send(channel, msg);
                        break;
                    }
                } else if (flag1 && condition1.run(line)) {
                    MahjongMsg msg = msgBuilder1.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag2 && condition2.run(line)) {
                    MahjongMsg msg = msgBuilder2.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag3 && condition3.run(line)) {
                    MahjongMsg msg = msgBuilder3.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else if (flag4 && condition4.run(line)) {
                    MahjongMsg msg = msgBuilder4.build(line);
                    MsgUtils.send(channel, msg);
                    break;
                } else {
                    System.out.println("错误的输入，请重新输入：");
                }
            }
        });
    }
}
