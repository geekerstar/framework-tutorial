package com.geekerstar.netty.manual.$997.codec;

import com.geekerstar.netty.manual.$997.msg.BaseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class ProtoEncoder extends MessageToMessageEncoder<BaseMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMsg msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
