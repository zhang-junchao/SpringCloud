package com.ajc.producer.rabbitConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.Date;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/4/14 15:01
 */
@Component
@Slf4j
public class RabbitConsumer {

    @RabbitListener(queues = "queueB")
    public void receivedD(Message message , Channel channel){
        log.info(new String(message.getBody()));
        log.info("时间 :{}" ,new Date().toString());
    }
}
