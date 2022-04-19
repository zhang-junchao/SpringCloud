package com.ajc.producer.rabbit.test_mq;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/4/12 15:14
 */
public class ProducerTest {

    private final static String EXCHANGER_NAME = "test";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqChannel.getChannel();

        // 创建交换机 exchange 主题模式
        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.TOPIC);

        while (true){
            System.out.println("控制台输入routingKey");
            Scanner sc = new Scanner(System.in);
            String routingKey = sc.nextLine();

            sc = new Scanner(System.in);
            String data = sc.nextLine();

            channel.basicPublish(EXCHANGER_NAME,routingKey,null,data.getBytes(StandardCharsets.UTF_8));
        }

    }

}
