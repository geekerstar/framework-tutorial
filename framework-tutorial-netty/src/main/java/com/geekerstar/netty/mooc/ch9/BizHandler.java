package com.geekerstar.netty.mooc.ch9;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author
 */
public class BizHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //...

        User user = new User(19, "zhangsan");

        ctx.channel().writeAndFlush(user);
    }
}
