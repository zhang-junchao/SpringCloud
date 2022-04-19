package com.ajc.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/4/13 14:36
 */
@Configuration
public class RabbitMqConfig {

    // 普通交换机
    public final static String X_EXCHANGE = "X" ;
    // 死信交换机
    public final static String Y_DEAD_LETTER_EXCHANGE = "Y";

    public final static String QUEUE_A = "QA" ;

    public final static String QUEUE_B = "QB" ;

    public final static String DEAD_LETTER_EXCHANGE = "QD" ;

    /**
     * @return 普通交换机
     */
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * @return 死信交换机
     */
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        arguments.put("x-message=ttl",1000*10);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_EXCHANGE).build();
    }

    /**
     * @return 死信队列
     */
    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        arguments.put("x-message=ttl",1000*40);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    @Bean
    public Binding queueA_X(@Qualifier("queueA") Queue queueA,
                            @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueB_X(@Qualifier("queueB") Queue queueB,
                            @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueD_Y(@Qualifier("queueD") Queue queueD,
                            @Qualifier("yExchange")DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }


}
