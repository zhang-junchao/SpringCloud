package com.ajc.producer.service.Impl;

import com.ajc.producer.model.LoginUserDetail;
import com.ajc.producer.model.User;
import com.ajc.producer.service.UserService;
import com.ajc.producer.util.JwtUtil;
import com.ajc.producer.util.RedisCache;
import com.ajc.producer.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2022/2/11 2:50 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private RedisCache redisCache ;
    @Override
    public ResponseResult login(User user) {
        // 先调用providerManager的方法进行认证，验证用户名密码是否正确
        // 验证用户名密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate==null){
           throw new RuntimeException("authenticate 用户认证失败");
        }

        //保存用户信息，存入redis
        LoginUserDetail loginUserDetail = (LoginUserDetail) authenticate.getPrincipal();
        Long id = loginUserDetail.getUser().getId();
        redisCache.setCacheObject(id.toString() ,loginUserDetail.getUser());
        String token = JwtUtil.createJWT(id.toString());
        Map<String,String> map = new HashMap<>();
        map.put("token",token);

        return new ResponseResult(200,"登陆成功",map);
    }
}
