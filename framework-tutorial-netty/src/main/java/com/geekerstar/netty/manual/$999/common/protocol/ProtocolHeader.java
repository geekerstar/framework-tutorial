package com.geekerstar.netty.manual.$999.common.protocol;

import io.netty.buffer.ByteBuf;

public interface ProtocolHeader {
    void encode(ByteBuf buf);

    void decode(ByteBuf buf);
}
