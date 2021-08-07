package com.geekerstar.netty.manual.$24;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class CompletableFutureTest {

    // 创建一个线程池
    private static Executor threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 执行一个有返回值的任务
        CompletableFuture<?> future = CompletableFuture.supplyAsync(() -> {
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
            System.out.println("inner thread: " + Thread.currentThread().getName());
//            throw new RuntimeException();
            return "aaaaa";
            // 如果执行异常，会进入下面这个回调
        }, threadPool).exceptionally(e -> {
            System.out.println("exception: " + e + ", thread: " + Thread.currentThread().getName());
            return "bbbbb";
            // 执行一个有返回值的回调
        }).thenApplyAsync(s -> {
            System.out.println("result: " + s + ", thread: " + Thread.currentThread().getName());
            return "ccccc";
            // 执行一个无返回值的回调
        }, threadPool).thenAcceptAsync(s -> {
            System.out.println("result2: " + s + ", thread: " + Thread.currentThread().getName());
            // 再执行一个有返回值的回调
        }, threadPool).thenApplyAsync(s -> {
            System.out.println("result3: " + s + ", thread: " + Thread.currentThread().getName());
            return "ddddd";
        }, threadPool);

        System.out.println("completableFuture running!");

        // 调用get()方法
        System.out.println("get: " + future.get());

    }
}
