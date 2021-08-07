package com.geekerstar.netty.manual.$21;

public class PlayerTest {
    public static void main(String[] args) {
        // 创建player1
        Player player1 = Player.newInstance(1L, "alan");

        // 打印player1
        System.out.println(player1);

        // ...

        // player1下线
        player1.offline();

        // 创建player2
        Player player2 = Player.newInstance(2L, "alex");

        // 打印player2
        System.out.println(player2);

        // 二者是否相等
        System.out.println(player1 == player2);

        // 假设player1又上线了呢
        player1 = Player.newInstance(1L, "alan");

        // 打印player1
        System.out.println(player1);

        // 二者是否相等
        System.out.println(player1 == player2);

    }
}
