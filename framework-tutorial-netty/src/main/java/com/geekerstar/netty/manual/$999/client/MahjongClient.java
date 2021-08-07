package com.geekerstar.netty.manual.$999.client;

import com.alibaba.fastjson.JSON;
import com.geekerstar.netty.manual.$999.client.codec.MahjongFrameDecoder;
import com.geekerstar.netty.manual.$999.client.codec.MahjongFrameEncoder;
import com.geekerstar.netty.manual.$999.client.codec.MahjongRequestEncoder;
import com.geekerstar.netty.manual.$999.client.codec.MahjongResponseDecoder;
import com.geekerstar.netty.manual.$999.common.protocol.domain.LoginRequest;
import com.geekerstar.netty.manual.$999.common.protocol.domain.OperationEnum;
import com.geekerstar.netty.manual.$999.common.protocol.mahjong.MahjongProtocolHeader;
import com.geekerstar.netty.manual.$999.common.protocol.mahjong.RequestMahjongProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author: tangtong
 * @date: 2020/6/5
 */
public class MahjongClient {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                    pipeline.addLast(new MahjongFrameDecoder());
                    pipeline.addLast(new MahjongFrameEncoder());

                    pipeline.addLast(new MahjongResponseDecoder());
                    pipeline.addLast(new MahjongRequestEncoder());

//                    pipeline.addLast(new MahjongClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(PORT)).sync();

            LoginRequest loginRequest = new LoginRequest("tt", "123456");
            System.out.println("request: " + JSON.toJSONString(loginRequest));

            RequestMahjongProtocol requestMahjongProtocol = new RequestMahjongProtocol();
            requestMahjongProtocol.setHeader(new MahjongProtocolHeader(OperationEnum.Login.getOpcode()));
            requestMahjongProtocol.setBody(loginRequest);

            future.channel().writeAndFlush(requestMahjongProtocol);

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
