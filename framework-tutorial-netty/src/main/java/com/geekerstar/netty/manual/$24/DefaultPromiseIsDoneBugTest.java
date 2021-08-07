package com.geekerstar.netty.manual.$24;

import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;

public class DefaultPromiseIsDoneBugTest {

    public static void main(String[] args) {
        // 创建一个promise
        Promise<?> promise = GlobalEventExecutor.INSTANCE.newPromise();
        // 设置为不可取消
        promise.setUncancellable();
        // 调用取消的方法
        boolean cancel = promise.cancel(false);
        // 调用是否完成的方法
        boolean isDone = promise.isDone();
        // 打印
        System.out.println(cancel);
        System.out.println(isDone);
        System.out.println(promise.isCancelled());
    }

}
