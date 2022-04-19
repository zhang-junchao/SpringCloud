package com.ajc.producer.rabbit.test_mq;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.ibatis.logging.stdout.StdOutImpl;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/4/12 16:06
 */
public class Consumer1 {

    private final static String EXCHANGER_NAME = "test";

    private final static String QUEUE_NAME = "Q1";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqChannel.getChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,"*.orange.*");
        System.out.println("Q1 正在准备接受消息");
        DeliverCallback deliverCallback = (consumerTag,message) ->{
            System.out.println("接收到的消息为"+ new String(message.getBody(),"UTF-8"));
            System.out.println("绑定的键关系为" + message.getEnvelope().getRoutingKey() );
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag ->{
            System.out.println();
        });
    }
}
