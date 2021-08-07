package com.geekerstar.netty.manual.$25;

import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DefaultEventExecutorGroupTest {

    public static void main(String[] args) {
        DefaultEventExecutorGroup executorGroup = new DefaultEventExecutorGroup(2);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                executorGroup.execute(() -> {
                    System.out.println("thread: " + Thread.currentThread().getName());
                });
            } else {
                executorGroup.execute(() -> {
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
                    System.out.println("thread: " + Thread.currentThread().getName());
                });
            }

        }
    }
}
