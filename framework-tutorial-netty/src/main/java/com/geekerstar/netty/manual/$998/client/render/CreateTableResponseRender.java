package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.msg.CreateTableResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateTableResponseRender implements MahjongRender<CreateTableResponse> {

    @Override
    public void render(CreateTableResponse response) {
        MockClient.createTableResponse(response);
    }
}
