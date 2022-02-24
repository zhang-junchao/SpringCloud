package com.ajc.producer;

import com.ajc.producer.config.EsConfig;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhangjunchao
 * @version 1.0
 * @date 2021/10/30 10:52
 * @Description
 */
@Slf4j
//这里要将默认数据源关闭，因为暂时没有配置。
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//启用服务发现注解
@EnableDiscoveryClient
@ServletComponentScan("com.ajc.producer.filter")
@MapperScan("com.ajc.producer.mapper")
@EnableConfigurationProperties({EsConfig.class})
public class ProducerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProducerApplication.class, args);
    }

}
