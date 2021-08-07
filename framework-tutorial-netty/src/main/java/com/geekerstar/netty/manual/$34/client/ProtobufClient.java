package com.geekerstar.netty.manual.$34.client;

import com.geekerstar.netty.manual.$34.proto.HelloRequest;
import com.geekerstar.netty.manual.$34.proto.HelloResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.net.InetSocketAddress;

public class ProtobufClient {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    // 一次编解码
                    p.addLast(new ProtobufVarint32FrameDecoder());
                    p.addLast(new ProtobufVarint32LengthFieldPrepender());
                    // 二次编解码，解码器必须传入具体的类型
                    p.addLast(new ProtobufDecoder(HelloResponse.getDefaultInstance()));
                    p.addLast(new ProtobufEncoder());
                    // 客户端处理器
                    p.addLast(new ProtobufClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(PORT)).sync();

            System.out.println("已连接到服务器...");

            // 发送HelloRequest消息
            HelloRequest helloRequest = HelloRequest.newBuilder().setName("彤哥").build();
            future.channel().writeAndFlush(helloRequest);

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
