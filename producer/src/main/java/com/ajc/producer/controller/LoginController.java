package com.ajc.producer.controller;

import com.ajc.producer.model.User;
import com.ajc.producer.service.UserService;
import com.ajc.producer.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/11 2:46 下午
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }
}
