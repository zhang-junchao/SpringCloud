package com.ajc.producer.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/1 3:04 下午
 */
@Data
public class user {
    private Integer id ;
    private String name ;
    private Integer age ;
    private String email ;
    @TableLogic
    private Integer deleted ;
}
