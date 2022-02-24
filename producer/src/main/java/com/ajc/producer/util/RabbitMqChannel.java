package com.ajc.producer.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description 获取rabbit mq 信道
 * @date : 2022/2/22 16:36
 */
public class RabbitMqChannel {
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory() ;
        factory.setHost("172.16.1.39");
        factory.setUsername("admin");
        factory.setPassword("123");

        //创建连接
        Connection connection = factory.newConnection();

        // 获取信道
        return connection.createChannel();
    }
}
