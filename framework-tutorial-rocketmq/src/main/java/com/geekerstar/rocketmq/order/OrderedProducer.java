package com.geekerstar.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 若为全局有序，则需要设置Queue数量为1
        // producer.setDefaultTopicQueueNums(1);

        producer.start();

        for (int i = 0; i < 100; i++) {
            // 为了演示简单，使用整型数作为orderId
            Integer orderId = i;
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("TopicA", "TagA", body);
            // 将orderId作为消息key
            msg.setKeys(orderId.toString());
            // send()的第三个参数值会传递给选择器的select()的第三个参数
            // 该send()为同步发送
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {

                // 具体的选择算法在该方法中定义
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // 以下是使用消息key作为选择的选择算法
                    String keys = msg.getKeys();
                    int id = Integer.parseInt(keys);

                    // 以下是使用arg作为选择key的选择算法
                    // Integer id = (Integer) arg;

                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);

            System.out.println(sendResult);
        }
        producer.shutdown();
    }
}
