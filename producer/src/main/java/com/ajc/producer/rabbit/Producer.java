package com.ajc.producer.rabbit;

import com.ajc.producer.util.RabbitMqChannel;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/22 16:38
 */
public class Producer {


    public static final String zhang = "test";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {


        Channel channel = RabbitMqChannel.getChannel();
        /**
         * 1. 队列名称
         * 2. 对面里面的消息是否持久化 默认存在内存中
         * 3. 该队列是否允许多个消费者消费  默认允许（true）  false 不允许
         * 4. 是 rue 自动删除 false 不自动删除
         * 5. 其他
         */
        channel.queueDeclare(zhang, true, false, false, null);

        //开启发布确认消息
        channel.confirmSelect();

        ConcurrentSkipListMap<Long,String> outStandingConfirms = new ConcurrentSkipListMap<>();


        ConfirmCallback confirmCallback = (deliveryTag, multiple) ->
        {
            //判断是否是批量确认
            if(multiple){
                ConcurrentNavigableMap<Long, String> headMap = outStandingConfirms.headMap(deliveryTag);
                headMap.clear();
            }else {
                outStandingConfirms.remove(deliveryTag);
            }
            System.out.println("确认的消息" + deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("失败确认消息"+deliveryTag);
        };
        int n = 0;
        // 在发布消息之前添加消息监听器
        channel.addConfirmListener(confirmCallback, nackCallback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {

            String message = n + "";
            /**
             * channel.getNextPublishSeqNo() 获取下一个消息标记序列号
             * 通过序列号与消息体进行一个关联
             * 最后筛选里面存的应该全都是处理失败的消息
             */
            outStandingConfirms.put(channel.getNextPublishSeqNo(),message);
            /**
             * 1.发送到哪个交换机 ,默认""
             * 2.路由的key
             * 3.消息保存策略
             * 4.发送消息内容
             */
            channel.basicPublish("", zhang, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            // 单个确认
            //channel.waitForConfirms();
            //异步确认方式，执行效率最高

            n++;
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "ms");
    }

    public static int ran1(int bound) {
        Random r = new Random(1);
        int ran1 = r.nextInt(bound);
        return ran1;
    }
}
