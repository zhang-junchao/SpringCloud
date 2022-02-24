package com.ajc.producer.rabbit.exchanges;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description 消费信道1
 * @date : 2022/2/24 11:54
 */
public class ConsumerOne {
    public static final String EXCHANGER_NAME = "logs" ;

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqChannel.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.FANOUT);

        //声明消费队列 临时列表，连接关闭自动删除
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName,EXCHANGER_NAME,"123");

        channel.basicConsume(queueName,true,(consumerTag, message)->{
            System.out.println("C1 接收到的消息 "+new String(message.getBody(),"UTF-8"));
        },consumerTag->{

        });

    }
}
