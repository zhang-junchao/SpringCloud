package com.zjc.one;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/29 22:54
 * @Description
 */
@Slf4j
//这里要将默认数据源关闭，因为暂时没有配置。
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//启用服务发现注解
@EnableDiscoveryClient
//Feign开启注解，basePackages和value功能相同，随意一个都可以。里面是feign扫描包路径
//@EnableFeignClients(basePackages = {"com"},value = {"com"})
public class OneApplication {

    public static void main(String[] args) {
        log.info("seata-mq-two准备启动");
        SpringApplication.run(OneApplication.class, args);

    }
}
