package com.geekerstar.netty.manual.$998.server.handler;

import com.geekerstar.netty.manual.$998.common.protocol.*;
import com.geekerstar.netty.manual.$998.server.threadpool.MahjongEventExecutorGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MahjongServerHandler extends SimpleChannelInboundHandler<MahjongProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MahjongProtocol mahjongProtocol) throws Exception {
        // process header
        MahjongProtocolHeader header = mahjongProtocol.getHeader();

        // we do nothing with header

        MahjongMsg body = mahjongProtocol.getBody();
        if (body instanceof MahjongRequest) {
            // 包装消息
            ReceivedMessage receivedMessage = new ReceivedMessage();
            receivedMessage.setRequest((MahjongRequest) body);
            receivedMessage.setChannel(ctx.channel());
            receivedMessage.setReceiveTime(System.currentTimeMillis());
            // 扔到麻将线程池中执行
            MahjongEventExecutorGroup.execute(receivedMessage);
        } else {
            log.error("error msgType, just discard, msgType={}", body.getClass().getSimpleName());
        }
    }
}
