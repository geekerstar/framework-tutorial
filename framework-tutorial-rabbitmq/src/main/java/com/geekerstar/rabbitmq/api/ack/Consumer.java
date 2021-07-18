package com.geekerstar.rabbitmq.api.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {


	public static void main(String[] args) throws Exception {


		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.0.107");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("geek");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();


		String exchangeName = "test_ack_exchange";
		String queueName = "test_ack_queue";
		String routingKey = "ack.#";

		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		// 手工签收 必须要关闭 autoAck = false
		channel.basicConsume(queueName, false, new MyConsumer(channel));


	}
}
