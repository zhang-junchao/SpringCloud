package com.ajc.producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 11:10
 * @Description
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/run")
    public String hello(){
        return "This is My Producer Server ! ! ! " ;
    }

}
