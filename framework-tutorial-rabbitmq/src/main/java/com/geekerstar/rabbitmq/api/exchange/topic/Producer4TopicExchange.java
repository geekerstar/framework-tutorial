package com.geekerstar.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4TopicExchange {


	public static void main(String[] args) throws Exception {

		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.0.107");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("geek");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();
		//4 声明
		String exchangeName = "test_topic_exchange";
		String routingKey1 = "user.save";
		String routingKey2 = "user.update";
		String routingKey3 = "user.delete.abc";
		//5 发送

		String msg = "Hello World RabbitMQ 4 Topic Exchange Message ...";
		channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes());
		channel.basicPublish(exchangeName, routingKey2 , null , msg.getBytes());
		channel.basicPublish(exchangeName, routingKey3 , null , msg.getBytes());
		channel.close();
        connection.close();
	}

}
