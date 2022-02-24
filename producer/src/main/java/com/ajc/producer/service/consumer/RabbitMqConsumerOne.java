package com.ajc.producer.service.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/22 12:07
 */
public class RabbitMqConsumerOne {
    public static final String QUEUE_NAME = "test";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory() ;
        factory.setHost("172.16.1.39");
        factory.setUsername("admin");
        factory.setPassword("123");

        //创建连接
        Connection connection = factory.newConnection();

        // 获取信道
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        /**
         * 1. 消费哪个队列
         * 2.是否自动应答
         * 3.未成功调用消息回调函数
         * 4.取消消费回调
         */
        channel.basicConsume(QUEUE_NAME,false,(consumerTag, message) ->{
            System.out.println(new String(message.getBody()));
            System.out.println(new Date());
            try {
                Thread.sleep(ran1(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        } ,message -> {
            System.out.println("消息异常");
        });
    }

    public static int ran1(int bound) {
        Random r = new Random(1);
        int ran1 = r.nextInt(bound);
        return ran1;
    }
}
