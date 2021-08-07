package com.geekerstar.netty.manual.$16;

import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {
        // 创建一个堆内存实现的ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(12);
        // 写入值
        buffer.putInt(1);
        buffer.putInt(2);
        buffer.putInt(3);

        // 切换为读模式
        buffer.flip();

        // 读取值
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());

    }
}
