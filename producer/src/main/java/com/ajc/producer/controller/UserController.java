package com.ajc.producer.controller;

import com.ajc.producer.mapper.UserMapper;
import com.ajc.producer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/1 3:11 下午
 */
@RestController
@RequestMapping("/user1")
public class UserController {
    @Autowired
    private UserMapper userMapper ;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userMapper.selectList(null);
    }

}
