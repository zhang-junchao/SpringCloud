package com.ajc.producer.mapper;

import com.ajc.producer.model.user;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("master")
public interface UserMapper extends BaseMapper<user> {

}
