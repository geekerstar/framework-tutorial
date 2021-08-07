package com.geekerstar.netty.manual.$999.client.handler;

import com.alibaba.fastjson.JSON;
import com.geekerstar.netty.manual.$999.common.protocol.mahjong.ResponseMahjongProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: tangtong
 * @date: 2020/6/5
 */
public class MahjongClientHandler extends SimpleChannelInboundHandler<ResponseMahjongProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMahjongProtocol msg) throws Exception {
        System.out.println("response: " + JSON.toJSONString(msg));
    }
}
