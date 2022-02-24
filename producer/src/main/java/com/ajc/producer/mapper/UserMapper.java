package com.ajc.producer.mapper;

import com.ajc.producer.model.User;
import com.ajc.producer.model.user_bak;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("master")
public interface UserMapper extends BaseMapper<User> {

}
