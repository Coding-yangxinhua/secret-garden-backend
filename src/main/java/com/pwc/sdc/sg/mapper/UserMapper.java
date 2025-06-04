package com.pwc.sdc.sg.mapper;

import com.pwc.sdc.sg.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pwc.sdc.sg.model.dto.UserDto;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user(用户表)】的数据库操作Mapper
* @createDate 2025-06-04 10:30:50
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    UserDto getByOpenId(String openId);

}




