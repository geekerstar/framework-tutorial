package com.geekerstar.netty.manual.$21;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

public class ByteBufTest3 {
    public static void main(String[] args) throws InterruptedException {
        // 参数是preferDirect，即是否偏向于使用直接内存
        ByteBufAllocator allocator = new PooledByteBufAllocator(false);
        // 分配一个40B的ByteBuf
        ByteBuf byteBuf = allocator.heapBuffer(40);
        // 写入数据
        byteBuf.writeInt(4);
        // 读取数据
        System.out.println(byteBuf.readInt());
        // 回收内存
        ReferenceCountUtil.release(byteBuf);
        // 休息1秒，保证完全释放
        Thread.sleep(1000);
        new Thread(() -> {
            // 再次分配一个40B的ByteBuf
            ByteBuf byteBuf2 = allocator.heapBuffer(30);
            // 其它处理
        }).start();
    }
}
