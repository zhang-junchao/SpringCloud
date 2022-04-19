package com.ajc.producer.model;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoginUserDetailTest {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Test
    public void test(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        System.out.println(passwordEncoder.matches("123123123",encode));
    }
    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Test
    public void mq(){
        rabbitTemplate.convertAndSend("X","XA","来自与10s的队列 : " );

    }
}