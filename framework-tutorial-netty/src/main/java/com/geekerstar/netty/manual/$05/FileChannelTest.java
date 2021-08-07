package com.geekerstar.netty.manual.$05;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        // 从文件获取一个FileChannel
        FileChannel fileChannel = new RandomAccessFile("/Users/geekerstar/Downloads/QQDownload/config.json", "rw").getChannel();
        // 声明一个Byte类型的Buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 将FileChannel中的数据读出到buffer中，-1表示读取完毕
        // buffer默认为写模式
        // read()方法是相对channel而言的，相对buffer就是写
        while ((fileChannel.read(buffer)) != -1) {
            // buffer切换为读模式
            buffer.flip();
            // buffer中是否有未读数据
            while (buffer.hasRemaining()) {
                // 未读数据的长度
                int remain = buffer.remaining();
                // 声明一个字节数组
                byte[] bytes = new byte[remain];
                // 将buffer中数据读出到字节数组中
                buffer.get(bytes);
                // 打印出来
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
            // 清空buffer，为下一次写入数据做准备
            // clear()会将buffer再次切换为写模式
            buffer.clear();
        }
    }
}
