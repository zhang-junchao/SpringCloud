package com.ajc.producer.service;

import com.ajc.producer.model.User;
import com.ajc.producer.util.ResponseResult;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/11 2:49 下午
 */
public interface UserService {


    ResponseResult login(User user);
}
