package com.geekerstar.netty.manual.$997.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    public FrameDecoder() {
        super(65535, 0, 2, 0, 2);
    }
}
