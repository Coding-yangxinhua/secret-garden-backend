package com.pwc.sdc.sg.service;

import com.pwc.sdc.sg.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pwc.sdc.sg.model.dto.UserDto;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user(用户表)】的数据库操作Service
* @createDate 2025-06-04 10:30:50
*/
public interface UserService extends IService<User> {

    /**
     * 查询或创建用户
     * @param openId
     * @return
     */
    UserDto queryOrCreateUser(String openId, String ip);

    UserDto getByOpenId(String openId);
}
