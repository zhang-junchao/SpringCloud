package com.ajc.producer.rabbit;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/22 16:49
 */
public class ConsumerOne {

    public static final String zhang = "test" ;
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqChannel.getChannel();

        /**
         * 设置消息分发策略 (默认0轮训分发 ，1 不公平分发 其他值：预取值  )
         */
        channel.basicQos(1);

        /**
         * 1. 消费哪个队列
         * 2.是否自动应答
         * 3.未成功调用消息回调函数
         * 4.取消消费回调
         */
        channel.basicConsume(zhang,false,(consumerTag, message) ->{
            System.out.println(new String(message.getBody()));
            System.out.println("正在提交");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 1. 消息标记位置
             * 2. 是否批量提交 ，提交会整个信道都提交（不建议）
             */
            channel.basicAck( message.getEnvelope().getDeliveryTag(),false);
            System.out.println("提交成功");
        } ,message -> {
            System.out.println("消息异常");
        });
    }
}
