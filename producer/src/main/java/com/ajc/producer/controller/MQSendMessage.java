package com.ajc.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description 发送延迟消息
 * @date : 2022/4/14 14:47
 */
@RestController
@RequestMapping("/ttl")
@Slf4j
public class MQSendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @GetMapping("/sendMsg/{message}")
    public String sendMsg (@PathVariable String message){
        log.info("当前时间：{},发送一条信息给两个队列，都是{},",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","来自与10s的队列 : " + message);
        rabbitTemplate.convertAndSend("X","XB","来自与40s的队列 : " + message);
        return "success" ;
    }


}
