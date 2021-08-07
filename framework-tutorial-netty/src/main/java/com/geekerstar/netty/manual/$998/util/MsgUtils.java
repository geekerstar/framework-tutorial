package com.geekerstar.netty.manual.$998.util;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.TableNotification;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongProtocol;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MsgUtils {

    public static void send(MahjongMsg msg) {
        Channel channel = DataManager.CURRENT_CHANNEL.get();
        send(channel, msg);
    }

    public static void send(Channel channel, MahjongMsg msg) {
        if (channel != null && channel.isActive() && channel.isWritable()) {
//            log.info("send channel msg: {}", JSON.toJSONString(msg));
            channel.writeAndFlush(new MahjongProtocol<>(msg));
        } else {
            log.error("channel unavailable, msgType={}", msg.getClass().getSimpleName());
        }
    }

    public static void send2Table(Table table, MahjongMsg msg) {
        for (Player player : table.getPlayers()) {
            if (player != null) {
                send2Player(player, msg);
            }
        }
    }

    public static void send2Player(Player player, MahjongMsg msg) {
        Channel channel = DataManager.getChannelByPlayerId(player.getId());
        send(channel, msg);
    }

    public static void sendTableNotification(TableNotification notification, boolean needHideOtherPlayerCards) {
        notification = notification.clone();
        Table table = notification.getTable();
        Player[] players = table.getPlayers();
        for (Player player : players) {
            if (player != null) {
                // 深拷贝且将其他玩家的牌隐藏起来
                if (needHideOtherPlayerCards) {
                    TableNotification clone = notification.clone();
                    hideOtherPlayerCards(player, clone.getTable());
                    send2Player(player, clone);
                } else {
                    send2Player(player, notification);
                }
            }
        }
    }

    private static void hideOtherPlayerCards(Player currentPlayer, Table table) {
        Player[] players = table.getPlayers();
        for (Player player : players) {
            if (player != null && player.getId() != currentPlayer.getId()) {
                byte[] cards = player.getCards();
                for (int i = 0; i < cards.length; i++) {
                    if (cards[i] != 0) {
                        cards[i] = 1;
                    }
                }
            }
        }
    }

    public static void send2TableExcept(Table table, MahjongMsg msg, List<Long> exceptPlayerIds) {
        System.out.println(exceptPlayerIds);
        for (Player player : table.getPlayers()) {
            System.out.println(player);
            if (player != null && !exceptPlayerIds.contains(player.getId())) {
                send2Player(player, msg);
            }
        }
    }
}
