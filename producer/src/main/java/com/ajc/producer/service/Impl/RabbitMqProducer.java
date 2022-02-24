package com.ajc.producer.service.Impl;

import com.ajc.producer.service.RabbitProducerOne;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/22 10:49
 */
public class RabbitMqProducer implements RabbitProducerOne {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.1.39");
        factory.setUsername("admin");
        factory.setPassword("123");

        //创建连接
        Connection connection = factory.newConnection();

        // 获取信道
        Channel channel = connection.createChannel();

        /**
         * 1. 队列名称
         * 2. 对面里面的消息是否持久化 默认存在内存中
         * 3. 该队列是否允许多个消费者消费  默认允许（true）  false 不允许
         * 4. 是否自动删除 true 自动删除 false 不自动删除
         * 5. 其他
         */
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "HELLO WORLD !!!";
        /**
         * 1.发送到哪个交换机
         * 2.路由的key
         * 3.其他
         * 4.发送消息内容
         */

        for (int i = 0; i < 1000000; i++) {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕~" + i );
        }


        System.out.println("消息发送完毕~");
        channel.close();
        connection.close();
    }

    public static int ran1(int bound) {
        Random r = new Random(1);
        int ran1 = r.nextInt(bound);
        return ran1;
    }
}
