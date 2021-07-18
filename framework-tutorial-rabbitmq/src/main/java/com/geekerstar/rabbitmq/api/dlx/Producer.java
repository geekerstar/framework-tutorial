package com.geekerstar.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {


	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.0.107");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("geek");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";

		String msg = "Hello RabbitMQ DLX Message";

		for(int i =0; i<1; i ++){

			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}

	}
}
