package com.geekerstar.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Procuder {


	public static void main(String[] args) throws Exception {
		//1 创建一个ConnectionFactory, 并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.0.107");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("geek");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		//3 通过connection创建一个Channel
		Channel channel = connection.createChannel();

		Map<String, Object> headers = new HashMap<>();
		headers.put("my1", "111");
		headers.put("my2", "222");


		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				.deliveryMode(2)
				.contentEncoding("UTF-8")
				.expiration("10000")
				.headers(headers)
				.build();

		//4 通过Channel发送数据
		for(int i=0; i < 5; i++){
			String msg = "Hello RabbitMQ!";
			//1 exchange   2 routingKey
			channel.basicPublish("", "test001", properties, msg.getBytes());
		}

		//5 记得要关闭相关的连接
		channel.close();
		connection.close();
	}
}
