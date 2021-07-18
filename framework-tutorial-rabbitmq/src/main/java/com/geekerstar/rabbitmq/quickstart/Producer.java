package com.geekerstar.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.107");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("geek");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建一个Channel
        Channel channel = connection.createChannel();

        //4.通过channel发送数据
        for (int i = 0; i < 5000; i++) {
            String msg = "hello rabbitmq " + i;
            //1.exchange 2.routingKey
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        //5.记得要关闭相关的连接
        channel.close();
        connection.close();
    }
}

