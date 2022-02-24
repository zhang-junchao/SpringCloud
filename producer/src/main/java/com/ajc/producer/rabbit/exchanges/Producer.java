package com.ajc.producer.rabbit.exchanges;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/24 12:26
 */
public class Producer {

    public static final String EXCHANGER_NAME = "logs" ;
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqChannel.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.FANOUT);


        //添加发布回调
        channel.addConfirmListener( (deliveryTag,  multiple)->{
            System.out.println("发布成功");
        },(deliveryTag, multiple)->{
            System.out.println("发布失败");
        });

        while (1==1){
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            channel.basicPublish(EXCHANGER_NAME,"123", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes(StandardCharsets.UTF_8));
        }

    }
}
