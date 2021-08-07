package com.geekerstar.netty.manual.$999.server;

import com.geekerstar.netty.manual.$999.server.codec.MahjongFrameDecoder;
import com.geekerstar.netty.manual.$999.server.codec.MahjongFrameEncoder;
import com.geekerstar.netty.manual.$999.server.codec.MahjongRequestDecoder;
import com.geekerstar.netty.manual.$999.server.codec.MahjongResponseEncoder;
import com.geekerstar.netty.manual.$999.server.handler.MahjongServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MahjongServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {
        // 1. 声明线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 2. 服务端引导器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3. 设置线程池
            serverBootstrap.group(bossGroup, workerGroup)
                    // 4. 设置ServerSocketChannel的类型
                    .channel(NioServerSocketChannel.class)
                    // 5. 设置参数
                    .option(ChannelOption.SO_BACKLOG, 100)
                    // 6. 设置ServerSocketChannel对应的Handler，只能设置一个
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 7. 设置SocketChannel对应的Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 可以添加多个子Handler
                            p.addLast(new LoggingHandler(LogLevel.INFO));

                            p.addLast(new MahjongFrameDecoder());
                            p.addLast(new MahjongFrameEncoder());

                            p.addLast(new MahjongRequestDecoder());
                            p.addLast(new MahjongResponseEncoder());

                            p.addLast(new MahjongServerHandler());
                        }
                    });

            // 8. 绑定端口
            ChannelFuture f = serverBootstrap.bind(PORT).sync();
            // 9. 等待服务端监听端口关闭，这里会阻塞主线程
            f.channel().closeFuture().sync();
        } finally {
            // 10. 优雅地关闭两个线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
