package com.geekerstar.netty.manual.$998.client;

import com.geekerstar.netty.manual.$998.client.handler.MahjongClientHandler;
import com.geekerstar.netty.manual.$998.client.mock.MockClient;
import com.geekerstar.netty.manual.$998.common.codec.MahjongFrameDecoder;
import com.geekerstar.netty.manual.$998.common.codec.MahjongFrameEncoder;
import com.geekerstar.netty.manual.$998.common.codec.MahjongProtocolDecoder;
import com.geekerstar.netty.manual.$998.common.codec.MahjongProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class MahjongClient {

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

//                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                    pipeline.addLast(new MahjongFrameDecoder());
                    pipeline.addLast(new MahjongFrameEncoder());

                    pipeline.addLast(new MahjongProtocolDecoder());
                    pipeline.addLast(new MahjongProtocolEncoder());

                    pipeline.addLast(businessGroup, new MahjongClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(PORT)).sync();

            System.out.println("已连接到服务器...");
            // 模拟客户端
            MockClient.setExecutorGroup(businessGroup);
            MockClient.setChannel(future.channel());
            MockClient.start();

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
