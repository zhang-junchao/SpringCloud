package com.ajc.producer.filter;

import com.ajc.producer.model.LoginUserDetail;
import com.ajc.producer.model.User;
import com.ajc.producer.util.JwtUtil;
import com.ajc.producer.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.bouncycastle.cms.PasswordRecipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO 自定义拦截器，验证是否登陆或token的合法性
 * @date : 2022/2/11 4:17 下午
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private RedisCache redisCache ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 没token ，放行，交给后面的过滤器执行
            filterChain.doFilter(request, response);
            return;
        }
        String subject;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        // 将用户信息放入Authentication
        User user = redisCache.getCacheObject(subject);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户未登陆");
        }
        LoginUserDetail loginUserDetail = new LoginUserDetail(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
