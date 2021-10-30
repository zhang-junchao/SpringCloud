package com.zjc.consumer.feign;

import com.zjc.consumer.feign.fallback.HelloFeignFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 11:35
 * @Description 远程调用生产者服务
 */
@FeignClient(value = "prouducer",fallbackFactory = HelloFeignFallBackFactory.class) // 注册到nacos注册中心的服务名
public interface HelloFeignClient {
    @RequestMapping("/hello/run")
    public String hello();
}
