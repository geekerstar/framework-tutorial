package com.geekerstar.netty.manual.$22;

public class ThreadLocalTest {
    // threadLocal1
    private static ThreadLocal<String> STRING_THREAD_LOCAL = ThreadLocal.withInitial(() -> Thread.currentThread().getName());
    // threadLocal2
    private static ThreadLocal<Long> LONG_THREAD_LOCAL = ThreadLocal.withInitial(() -> Thread.currentThread().getId());
    // threadLocal3
    private static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        // thread1
        new Thread(() -> {
            User user = new User(1L, "test001");
            USER_THREAD_LOCAL.set(user);

            System.out.println("001：threadName: " + STRING_THREAD_LOCAL.get());
            System.out.println("001：threadId: " + LONG_THREAD_LOCAL.get());
            System.out.println("001：" + USER_THREAD_LOCAL.get());
        }, "thread-001").start();

        // thread2
        new Thread(() -> {
            User user = new User(2L, "test002");
            USER_THREAD_LOCAL.set(user);

            System.out.println("002：threadName: " + STRING_THREAD_LOCAL.get());
            System.out.println("002：threadId: " + LONG_THREAD_LOCAL.get());
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
