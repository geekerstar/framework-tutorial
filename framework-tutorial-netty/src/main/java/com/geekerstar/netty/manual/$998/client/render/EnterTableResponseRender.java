package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.msg.EnterTableResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnterTableResponseRender implements MahjongRender<EnterTableResponse> {

    @Override
    public void render(EnterTableResponse response) {
        MockClient.enterTableResponse(response);
    }
}
