package com.geekerstar.netty.manual.$22;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

public class FastThreadLocalTest {

    private static FastThreadLocal<User> USER_THREAD_LOCAL = new FastThreadLocal<>();

    public static void main(String[] args) {
        // thread1
        new FastThreadLocalThread(() -> {
            User user = new User(1L, "test001");
            USER_THREAD_LOCAL.set(user);

            System.out.println("001：" + USER_THREAD_LOCAL.get());
        }, "thread-001").start();

        // thread2
        new FastThreadLocalThread(() -> {
            User user = new User(2L, "test002");
            USER_THREAD_LOCAL.set(user);

            System.out.println("002：" + USER_THREAD_LOCAL.get());
        }, "thread-002").start();
    }

    static class User {
        Long id;
        String name;

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "id=" + id + ", name=" + name;
        }
    }
}
