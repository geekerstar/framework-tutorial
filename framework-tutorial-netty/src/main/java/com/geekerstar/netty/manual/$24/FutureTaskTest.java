package com.geekerstar.netty.manual.$24;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class FutureTaskTest {

    // 创建一个线程池
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(8);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        // 执行第一个任务
        Future<Integer> future1 = THREAD_POOL.submit(() -> {
            // 阻塞1秒
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
            // 返回1
            return 1;
        });
        // 执行第二个任务
        Future<Integer> future2 = THREAD_POOL.submit(() -> {
            // 阻塞2秒
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
            // 返回2
            return 2;
        });

        // 把两个任务执行的结果相加
        int result = future1.get() + future2.get();
        // 打印
        System.out.println("result=" + result);

        System.in.read();
    }
}
