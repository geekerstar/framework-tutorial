package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.msg.TableNotification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableNotificationRender implements MahjongRender<TableNotification> {

    @Override
    public void render(TableNotification response) {
        MockClient.tableNotification(response);
    }
}
