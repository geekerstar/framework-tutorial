package com.geekerstar.netty.manual.$999.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author: tangtong
 * @date: 2020/6/5
 */
public class MahjongFrameDecoder extends LengthFieldBasedFrameDecoder {
    public MahjongFrameDecoder() {
        super(65535, 0, 2, 0, 2);
    }
}
