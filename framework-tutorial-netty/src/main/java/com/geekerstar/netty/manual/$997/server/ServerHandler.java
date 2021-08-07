package com.geekerstar.netty.manual.$997.server;

import com.geekerstar.netty.manual.$997.msg.BaseMsg;
import com.geekerstar.netty.manual.$997.proto.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<BaseMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {
        System.out.println(msg);

        BaseMsg response = new BaseMsg();
        response.setCmd(2);
        response.setReqId(msg.getReqId());
        response.setVersion(msg.getVersion());
        response.setBody(LoginResponse.newBuilder().setResult(true).setMessage("success").build());

        ctx.writeAndFlush(response);
    }
}
