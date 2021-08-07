package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.msg.OperationNotification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationNotificationRender implements MahjongRender<OperationNotification> {

    @Override
    public void render(OperationNotification response) {
        MockClient.operationNotification(response);
    }
}
