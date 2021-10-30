package com.zjc.consumer.feign.fallback;

import com.zjc.consumer.feign.HelloFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 12:20
 * @Description
 */
@Component
public class HelloFeignFallBackFactory implements FallbackFactory<HelloFeignClient>{
    @Override
    public HelloFeignClient create(Throwable throwable) {
        return new HelloFeignClient() {
            @Override
            public String hello() {
                return "feign 服务熔断，prouducer 连接超时，请稍后再试";
            }
        };
    }
}
