package com.geekerstar.netty.manual.$23;

import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;

public class MpscArrayQueueTest {

    public static final MpscArrayQueue<String> QUEUE = new MpscArrayQueue<>(5);

    public static void main(String[] args) {
        // 入队，如果队列满了则会抛出异常
        QUEUE.add("1");
        // 入队，返回是否成功
        QUEUE.offer("2");
        QUEUE.offer("3");
        QUEUE.offer("4");

        // 存储了多少元素
        System.out.println("队列大小：" + QUEUE.size());
        // 容量，可以存储多少元素，会按2次方对齐，所以这里为8
        System.out.println("队列容量" + QUEUE.capacity());

        // 出队，如果队列为空则会抛出异常
        System.out.println("出队：" + QUEUE.remove());
        // 出队，如果队列为空返回null
        System.out.println("出队：" + QUEUE.poll());
        // 查看队列头元素，如果队列为空则会抛出异常
        System.out.println("查看队列头元素：" + QUEUE.element());
        // 查看队列头元素，如果队列为空则返回null
        System.out.println("查看队列头元素：" + QUEUE.peek());
    }
}

class MemoryLayout {
    String a;
    private long b;
    int c;
    Object d;
    Byte e;
}
