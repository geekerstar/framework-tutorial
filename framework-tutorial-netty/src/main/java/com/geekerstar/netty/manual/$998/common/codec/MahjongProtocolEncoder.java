package com.geekerstar.netty.manual.$998.common.codec;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MahjongProtocolEncoder extends MessageToMessageEncoder<MahjongProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MahjongProtocol msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
