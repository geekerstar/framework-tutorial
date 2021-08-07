package com.geekerstar.netty.manual.$34.server;

import com.geekerstar.netty.manual.$34.proto.HelloRequest;
import com.geekerstar.netty.manual.$34.proto.HelloResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufServerHandler extends SimpleChannelInboundHandler<HelloRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HelloRequest msg) throws Exception {
        // 响应
        HelloResponse helloResponse = HelloResponse.newBuilder()
                .setResult(true)
                .setMessage("hello " + msg.getName())
                .build();

        ctx.writeAndFlush(helloResponse);
    }
}
