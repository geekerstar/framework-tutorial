package com.geekerstar.netty.manual.$16;

import sun.misc.Cleaner;

public class CleanerTest {
    public static void main(String[] args) {
        Object obj = new Object();
        Cleaner cleaner = Cleaner.create(obj, () -> {
            System.out.println("xxxxx");
        });

        Cleaner cleaner2 = Cleaner.create(obj, () -> {
            System.out.println("yyyyy");
        });
//        cleaner.enqueue();
//        cleaner2.enqueue();
        obj = null;
        System.gc();

    }
}
