package com.geekerstar.rocketmq.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class FilterByTagProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("rocketmqOS:9876");
        producer.start();

        // 发送的消息均包含Tag，为以下三种Tag之一
        String[] tags = {"myTagA","myTagB","myTagC"};
        for (int i = 0; i < 10; i++) {
            byte[] body = ("Hi," + i).getBytes();
            String tag =   tags[i % tags.length];
            Message msg = new Message("TopicC", tag,body);
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }
}
