package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.msg.*;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * Processor无状态，可以单例
 */
public enum ProcessorEnum {
    LOGIN_REQUEST_PROCESSOR(LoginRequest.class, new LoginRequestProcessor()),
    CREATE_TABLE_REQUEST_PROCESSOR(CreateTableRequest.class, new CreateTableRequestProcessor()),
    ENTER_TABLE_REQUEST_PROCESSOR(EnterTableRequest.class, new EnterTableRequestProcessor()),
    START_GAME_MSG_PROCESSOR(StartGameMsg.class, new StartGameMsgProcessor()),
    OPERATION_REQUEST_PROCESSOR(OperationRequest.class, new OperationRequestProcessor()),
    ;

    private static Map<Class<? extends MahjongMsg>, MahjongProcessor> cache;

    private Class<? extends MahjongMsg> requestType;
    private MahjongProcessor processor;

    ProcessorEnum(Class<? extends MahjongMsg> requestType, MahjongProcessor processor) {
        this.requestType = requestType;
        this.processor = processor;
    }

    public static MahjongProcessor getProcessor(Class<? extends MahjongMsg> msg) {
        checkIfInitCache();
        return cache.get(msg);
    }

    private static void checkIfInitCache() {
        if (ProcessorEnum.cache == null) {
            synchronized (ProcessorEnum.class) {
                if (ProcessorEnum.cache == null) {
                    Map<Class<? extends MahjongMsg>, MahjongProcessor> cache = new HashMap<>();
                    for (ProcessorEnum processorEnum : ProcessorEnum.values()) {
                        cache.put(processorEnum.requestType, processorEnum.processor);
                    }
                    ProcessorEnum.cache = cache;
                }
            }
        }
    }
}
