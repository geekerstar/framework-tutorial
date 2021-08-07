package com.geekerstar.netty.manual.$998.util;


import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class IdUtils {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1);

    public static long generateId() {
        return ATOMIC_LONG.getAndIncrement();
    }

    public static long randomLong() {
        return randomLong(Long.MAX_VALUE);
    }

    public static long randomLong(long bound) {
        return ThreadLocalRandom.current().nextLong(bound);
    }

    public static int randomInt() {
        return randomInt(Integer.MAX_VALUE);
    }

    public static int randomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static void main(String[] args) {
        System.out.println(randomInt());
        System.out.println(randomInt());
        System.out.println(randomInt());
        System.out.println(randomInt());
        System.out.println(randomInt());
        System.out.println(randomInt());
        System.out.println(randomInt());
    }
}
