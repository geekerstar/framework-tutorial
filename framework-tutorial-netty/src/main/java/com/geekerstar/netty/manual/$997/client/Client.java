package com.geekerstar.netty.manual.$997.client;

import com.geekerstar.netty.manual.$997.codec.FrameDecoder;
import com.geekerstar.netty.manual.$997.codec.FrameEncoder;
import com.geekerstar.netty.manual.$997.codec.ProtoDecoder;
import com.geekerstar.netty.manual.$997.codec.ProtoEncoder;
import com.geekerstar.netty.manual.$997.msg.BaseMsg;
import com.geekerstar.netty.manual.$997.proto.LoginRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class Client {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {

        DefaultEventLoopGroup businessGroup = new DefaultEventLoopGroup(1);
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

                    pipeline.addLast(new FrameDecoder());
                    pipeline.addLast(new FrameEncoder());

                    pipeline.addLast(new ProtoDecoder());
                    pipeline.addLast(new ProtoEncoder());

                    pipeline.addLast(businessGroup, new ClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(PORT)).sync();

            System.out.println("已连接到服务器...");

            LoginRequest loginRequest = LoginRequest.newBuilder().setUsername("tt").setPassword("123").build();
            BaseMsg baseMsg = new BaseMsg();
            baseMsg.setCmd(1);
            baseMsg.setVersion(1);
            baseMsg.setReqId(1);
            baseMsg.setBody(loginRequest);
            future.channel().writeAndFlush(baseMsg);

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
