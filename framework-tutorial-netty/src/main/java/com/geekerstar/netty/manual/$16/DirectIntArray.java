package com.geekerstar.netty.manual.$16;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class DirectIntArray {
    // 一个int等于4个字节
    private static final int INT = 4;
    private long size;
    private long address;

    private static Unsafe unsafe;

    static {
        try {
            // Unsafe类有权限访问控制，只能通过反射获取其实例
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public DirectIntArray(long size) {
        this.size = size;
        // 参数字节数
        address = unsafe.allocateMemory(size * INT);
    }

    // 获取某位置的值
    public int get(long i) {
        if (i >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return unsafe.getInt(address + i * INT);
    }

    // 设置某位置的值
    public void set(long i, int value) {
        if (i >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        unsafe.putInt(address + i * INT, value);
    }

    // 数组大小
    public long size() {
        return size;
    }

    // 释放内存
    public void freeMemory() {
        unsafe.freeMemory(address);
    }

    public static void main(String[] args) {
        // 创建数组并赋值
        DirectIntArray array = new DirectIntArray(4);
        array.set(0, 1);
        array.set(1, 2);
        array.set(2, 3);
        array.set(3, 4);
        // 下面这行数组越界了
//        array.set(5, 5);

        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        // 打印10
        System.out.println(sum);

        // 最后别忘记释放内存
        array.freeMemory();
    }
}
