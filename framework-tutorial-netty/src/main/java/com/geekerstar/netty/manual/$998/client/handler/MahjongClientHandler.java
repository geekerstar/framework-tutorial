package com.geekerstar.netty.manual.$998.client.handler;

import com.geekerstar.netty.manual.$998.client.render.MahjongRender;
import com.geekerstar.netty.manual.$998.client.render.RenderEnum;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongProtocol;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongProtocolHeader;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MahjongClientHandler extends SimpleChannelInboundHandler<MahjongProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MahjongProtocol mahjongProtocol) throws Exception {
        // process header
        MahjongProtocolHeader header = mahjongProtocol.getHeader();

        // we do nothing with header

        MahjongMsg body = mahjongProtocol.getBody();
        if (body instanceof MahjongResponse) {
            // render response
            MahjongResponse response = (MahjongResponse) body;
            MahjongRender render = RenderEnum.getRender(response.getClass());
            if (render != null) {
                try {
                    render.render(response);
                } catch (Exception e) {
                    log.error("渲染时出错", e);
                }
            } else {
                log.error("not found response render, msgType={}", body.getClass().getSimpleName());
            }
        } else {
            log.error("error msgType, just discard, msgType={}", body.getClass().getSimpleName());
        }
    }
}
