package com.geekerstar.netty.manual.$997.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class FrameEncoder extends LengthFieldPrepender {

    public FrameEncoder() {
        super(2);
    }
}
