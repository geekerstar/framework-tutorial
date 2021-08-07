package com.geekerstar.netty.manual.$998.server.data;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.OperationNotification;
import com.geekerstar.netty.manual.$998.common.msg.OperationRequest;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FastThreadLocal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据管理
 */
public class DataManager {

    public static final FastThreadLocal<Channel> CURRENT_CHANNEL = new FastThreadLocal<>();
    public static final FastThreadLocal<Long> CURRENT_TABLE_ID = new FastThreadLocal<>();
    public static final FastThreadLocal<EventExecutor> CURRENT_EXECUTOR = new FastThreadLocal<>();

    private static final Map<Channel, ChannelBinding> CHANNEL_BINDING_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, Table> TABLE_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, Channel> PLAYER_ID_2_CHANNEL_MAP = new ConcurrentHashMap<>();

    private static final Map<Long, LinkedList<Byte>> TABLE_CARDS_MAP = new ConcurrentHashMap<>();

    /**
     * 对于下面两个map，一个是等待玩家操作，一个是收到玩家操作请求，等待其他玩家操作
     * 对于同一个桌子，只有两个list相等了，才能进行下一步操作
     */
    private static final Map<Long, List<OperationNotification>> TABLE_WAITING_OPERATION_NOTIFICATION_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, List<OperationRequest>> TABLE_WAITING_OPERATION_REQUEST_MAP = new ConcurrentHashMap<>();

    public static void bindPlayerChannel(Player player, Channel channel) {
        PLAYER_ID_2_CHANNEL_MAP.put(player.getId(), channel);
    }

    public static Channel getChannelByPlayerId(Long playerId) {
        return PLAYER_ID_2_CHANNEL_MAP.get(playerId);
    }

    public static Long getTableIdByChannel(Channel channel) {
        synchronized (channel) {
            ChannelBinding channelBinding = CHANNEL_BINDING_MAP.get(channel);
            if (channelBinding == null) {
                return null;
            }
            Table table = channelBinding.getTable();
            if (table == null) {
                return null;
            }
            return table.getId();
        }
    }

    public static void bindChannelPlayer(Channel channel, Player player) {
        synchronized (channel) {
            ChannelBinding channelBinding = CHANNEL_BINDING_MAP.get(channel);
            if (channelBinding == null) {
                channelBinding = new ChannelBinding();
                CHANNEL_BINDING_MAP.put(channel, channelBinding);
            }
            channelBinding.setPlayer(player);
        }
    }

    public static void bindChannelTable(Channel channel, Table table) {
        synchronized (channel) {
            ChannelBinding channelBinding = CHANNEL_BINDING_MAP.get(channel);
            if (channelBinding == null) {
                channelBinding = new ChannelBinding();
                CHANNEL_BINDING_MAP.put(channel, channelBinding);
            }
            channelBinding.setTable(table);
        }
    }

    public static Player currentPlayer() {
        Channel channel = CURRENT_CHANNEL.get();
        ChannelBinding channelBinding = CHANNEL_BINDING_MAP.get(channel);
        return channelBinding.getPlayer();
    }

    public static Table getTableById(Long tableId) {
        return TABLE_MAP.get(tableId);
    }

    public static void putTable(Table table) {
        TABLE_MAP.put(table.getId(), table);
    }

    public static void putCards(Long tableId, LinkedList<Byte> cards) {
        TABLE_CARDS_MAP.put(tableId, cards);
    }

    public static Byte pollFirstCard(Long tableId) {
        LinkedList<Byte> cards = TABLE_CARDS_MAP.get(tableId);
        return cards.pollFirst();
    }

    public static Byte pollLastCard(Long tableId) {
        LinkedList<Byte> cards = TABLE_CARDS_MAP.get(tableId);
        return cards.pollLast();
    }

    public static void clearTable(Table table) {
        TABLE_MAP.remove(table.getId());
        TABLE_CARDS_MAP.remove(table.getId());
        TABLE_WAITING_OPERATION_NOTIFICATION_MAP.remove(table.getId());
        TABLE_WAITING_OPERATION_REQUEST_MAP.remove(table.getId());
        Player[] players = table.getPlayers();
        for (Player player : players) {
            Channel channel = PLAYER_ID_2_CHANNEL_MAP.get(player.getId());
            synchronized (channel) {
                ChannelBinding channelBinding = CHANNEL_BINDING_MAP.get(channel);
                channelBinding.setTable(null);
            }
            player.reset();
        }
    }

    public static void addTableWaitingOperationNotification(Long tableId, List<OperationNotification> list) {
        TABLE_WAITING_OPERATION_NOTIFICATION_MAP.put(tableId, list);
    }

    public static List<OperationNotification> getTableWaitingOperationNotification(Long tableId) {
        List<OperationNotification> notificationList = TABLE_WAITING_OPERATION_NOTIFICATION_MAP.get(tableId);
        return notificationList == null ? new ArrayList<>() : notificationList;
    }

    public static boolean addTableWaitingOperationRequest(Long tableId, OperationRequest operationRequest) {
        List<OperationRequest> operationRequests = TABLE_WAITING_OPERATION_REQUEST_MAP.get(tableId);
        if (operationRequests == null) {
            operationRequests = new ArrayList<>();
            TABLE_WAITING_OPERATION_REQUEST_MAP.put(tableId, operationRequests);
        }
        operationRequests.add(operationRequest);
        return operationRequests.size() == getTableWaitingOperationNotification(tableId).size();
    }

    public static List<OperationRequest> getTableWaitingOperationRequest(Long tableId) {
        List<OperationRequest> operationRequests = TABLE_WAITING_OPERATION_REQUEST_MAP.get(tableId);
        return operationRequests == null ? new ArrayList<>() : operationRequests;
    }

    public static void clearTableWaitingOperation(Long tableId) {
        TABLE_WAITING_OPERATION_NOTIFICATION_MAP.remove(tableId);
        TABLE_WAITING_OPERATION_REQUEST_MAP.remove(tableId);
    }
}
