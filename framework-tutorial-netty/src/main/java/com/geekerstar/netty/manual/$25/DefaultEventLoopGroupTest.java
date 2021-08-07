package com.geekerstar.netty.manual.$25;

import io.netty.channel.DefaultEventLoopGroup;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DefaultEventLoopGroupTest {
    public static void main(String[] args) {
        DefaultEventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(2);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                eventLoopGroup.execute(() -> {
                    System.out.println("thread: " + Thread.currentThread().getName());
                });
            } else {
                eventLoopGroup.execute(() -> {
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
                    System.out.println("thread: " + Thread.currentThread().getName());
                });
            }
        }
    }
}
