package com.zjc.two;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 10:52
 * @Description
 */
@Slf4j
//这里要将默认数据源关闭，因为暂时没有配置。
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//启用服务发现注解
@EnableDiscoveryClient
public class ApplicationTwo {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTwo.class,args);
    }

}
