package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.msg.OperationResultNotification;

public class OperationResultNotificationRender implements MahjongRender<OperationResultNotification> {
    @Override
    public void render(OperationResultNotification response) {
        MockClient.operationResultNotification(response);
    }
}
