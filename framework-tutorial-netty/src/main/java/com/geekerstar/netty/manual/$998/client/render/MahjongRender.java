package com.geekerstar.netty.manual.$998.client.render;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongResponse;

public interface MahjongRender<T extends MahjongResponse> {
    void render(T response);
}
