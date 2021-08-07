package com.geekerstar.netty.manual.$998.common.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class MahjongFrameEncoder extends LengthFieldPrepender {
    public MahjongFrameEncoder() {
        super(2);
    }
}
