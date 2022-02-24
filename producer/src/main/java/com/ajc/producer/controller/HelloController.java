package com.ajc.producer.controller;

import com.ajc.producer.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EsService esService ;
    @RequestMapping("/run")
    public String hello(){
        return "This is My Producer Server ! ! ! " ;
    }


    @RequestMapping("/addIndex")
    public String add_index(){
        esService.addIndex();

        return "success";
    }

}
