package com.geekerstar.netty.manual.$998.common.msg;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;

import java.util.HashMap;
import java.util.Map;

public enum MsgTypeEnum {
    LOGIN_REQUEST(1, LoginRequest.class),
    LOGIN_RESPONSE(2, LoginResponse.class),
    CREATE_TABLE_REQUEST(3, CreateTableRequest.class),
    CREATE_TABLE_RESPONSE(4, CreateTableResponse.class),
    ENTER_TABLE_REQUEST(5, EnterTableRequest.class),
    ENTER_TABLE_RESPONSE(6, EnterTableResponse.class),
    OPERATION_REQUEST(7, OperationRequest.class),
    OPERATION_NOTIFICATION(8, OperationNotification.class),
    OPERATION_RESULT_NOTIFICATION(9, OperationResultNotification.class),
    SETTLE_NOTIFICATION(10, SettleNotification.class),
    START_GAME_MSG(11, StartGameMsg.class),
    TABLE_NOTIFICATION(12, TableNotification.class),
    ;

    private static Map<Integer, Class<? extends MahjongMsg>> cmd2bodyTypeCache;
    private static Map<Class<? extends MahjongMsg>, Integer> bodyType2cmdCache;

    private int cmd;
    private Class<? extends MahjongMsg> bodyType;

    MsgTypeEnum(int cmd, Class<? extends MahjongMsg> bodyType) {
        this.cmd = cmd;
        this.bodyType = bodyType;
    }

    public static Class<? extends MahjongMsg> parseByCmd(int cmd) {
        checkIfinitCache();
        return cmd2bodyTypeCache.get(cmd);
    }

    public static Integer parseByBodyType(Class<? extends MahjongMsg> bodyType) {
        checkIfinitCache();
        return bodyType2cmdCache.get(bodyType);
    }

    private static void checkIfinitCache() {
        if (MsgTypeEnum.cmd2bodyTypeCache == null || MsgTypeEnum.bodyType2cmdCache == null) {
            synchronized (MsgTypeEnum.class) {
                if (MsgTypeEnum.cmd2bodyTypeCache == null || MsgTypeEnum.bodyType2cmdCache == null) {
                    Map<Integer, Class<? extends MahjongMsg>> cmdCache = new HashMap<>();
                    Map<Class<? extends MahjongMsg>, Integer> bodyTypeCache = new HashMap<>();
                    for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
                        cmdCache.put(msgTypeEnum.cmd, msgTypeEnum.bodyType);
                        bodyTypeCache.put(msgTypeEnum.bodyType, msgTypeEnum.cmd);
                    }
                    MsgTypeEnum.cmd2bodyTypeCache = cmdCache;
                    MsgTypeEnum.bodyType2cmdCache = bodyTypeCache;
                }
            }
        }
    }

}
