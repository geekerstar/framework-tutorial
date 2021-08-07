package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.common.msg.*;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 渲染器，可以为单例
 */
public enum RenderEnum {
    LOGIN_RESPONSE_RENDER(LoginResponse.class, new LoginResponseRender()),
    CREATE_TABLE_RESPONSE_RENDER(CreateTableResponse.class, new CreateTableResponseRender()),
    ENTER_TABLE_RESPONSE_RENDER(EnterTableResponse.class, new EnterTableResponseRender()),
    TABLE_NOTIFICATION_RENDER(TableNotification.class, new TableNotificationRender()),
    OPERATION_NOTIFICATION_RENDER(OperationNotification.class, new OperationNotificationRender()),
    OPERATION_RESULT_NOTIFICATION_RENDER(OperationResultNotification.class, new OperationResultNotificationRender()),
    SETTLE_NOTIFICATION_RENDER(SettleNotification.class, new SettleNotificationRender()),
    ;

    private static Map<Class<? extends MahjongResponse>, MahjongRender> cache;
    private Class<? extends MahjongResponse> responseType;
    private MahjongRender render;

    RenderEnum(Class<? extends MahjongResponse> responseType, MahjongRender render) {
        this.responseType = responseType;
        this.render = render;
    }

    public static MahjongRender getRender(Class<? extends MahjongResponse> responseType) {
        checkIfInitCache();
        return cache.get(responseType);
    }

    private static void checkIfInitCache() {
        if (RenderEnum.cache == null) {
            synchronized (RenderEnum.class) {
                if (RenderEnum.cache == null) {
                    Map<Class<? extends MahjongResponse>, MahjongRender> cache = new HashMap<>();
                    for (RenderEnum renderEnum : RenderEnum.values()) {
                        cache.put(renderEnum.responseType, renderEnum.render);
                    }
                    RenderEnum.cache = cache;
                }
            }
        }

    }
}
