package com.geekerstar.netty.manual.$24;

import io.netty.util.concurrent.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class DefaultPromiseTest {
    AtomicReference reference = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        // 相当于一个线程池中的一个线程（单线程）
        EventExecutor eventExecutor = new DefaultEventExecutor();
        // 使用EventExecutor的newPromise()方法创建
        Promise<Integer> promise1 = eventExecutor.newPromise();
        // 使用构造方法创建，推荐使用上面那种方式创建Promise
        Promise<Integer> promise2 = new DefaultPromise<>(eventExecutor);

        GenericFutureListener<Future<? super Integer>> listener = future -> {
            // 成功执行完毕
            if (future.isSuccess()) {
                System.out.println("done success, result=" + future.get() + ", thread=" + Thread.currentThread().getName());
            } else {
                System.out.println("done exception, e=" + future.cause() + ", thread=" + Thread.currentThread().getName());
            }
        };
        promise1.addListener(listener);
        promise2.addListener(listener);

        // 执行两个任务
        eventExecutor.execute(() -> calc(2, promise1));
        eventExecutor.execute(() -> calc(0, promise2));

        // sync，阻塞，如果有异常会抛出异常
//        promise1.sync();
        // await，阻塞，不会抛出异常
//        promise2.await();

        System.out.println("finish");

        System.in.read();
    }

    private static void calc(int num, Promise<Integer> promise) {
        try {
            // 阻塞2秒
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
            // 注意除数
            int result = 100 / num;
            System.out.println("success, thread=" + Thread.currentThread().getName());
            // 成功执行完毕
            promise.setSuccess(result);
        } catch (Exception e) {
            System.out.println("exception caught, e=" + e + ", thread=" + Thread.currentThread().getName());
            // 执行异常
            promise.setFailure(e);
        }
    }
}
