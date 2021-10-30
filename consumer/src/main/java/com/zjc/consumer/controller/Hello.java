package com.zjc.consumer.controller;

import com.zjc.consumer.feign.HelloFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 11:39
 * @Description
 */
@RequestMapping("feign")
@RestController
public class Hello {
    @Autowired
    private HelloFeignClient helloFeignClient ;

    @RequestMapping("/hello")
    public String hello(){
        String hello = helloFeignClient.hello();
        return "消费 >>>" + hello ;
    }
}
