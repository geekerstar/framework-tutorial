package com.geekerstar.netty.atguigu.netty.dubborpc.provider;

import com.geekerstar.netty.atguigu.netty.dubborpc.netty.NettyServer;

//ServerBootstrap 会启动一个服务提供者，就是 NettyServer
public class ServerBootstrap {
    public static void main(String[] args) {

        //代码代填..
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
