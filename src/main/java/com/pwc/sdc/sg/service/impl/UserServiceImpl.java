package com.pwc.sdc.sg.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.model.User;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.service.UserService;
import com.pwc.sdc.sg.mapper.UserMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user(用户表)】的数据库操作Service实现
* @createDate 2025-06-04 10:30:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public UserDto queryOrCreateUser(String openId, String ip) {
        // 从缓存中取用户
        String userKey = SystemConstant.USER_PREFIX + openId;
        String userIpKey = SystemConstant.USER_IP_PREFIX + ip;
        String userStr = "";
        redisTemplate.opsForValue().get(userKey);
        // 从数据库取
        if (!StringUtils.hasText(userStr)) {
            UserDto user = this.getByOpenId(openId);
            // 用户不存在，创建
            if (user == null) {
                User newUser = new User();
                newUser.setOpenId(openId);
                this.save(newUser);
                user = new UserDto();
                user.setOpenId(openId);
                user.setId(newUser.getId());
            }
            user.setIp(ip);
            // 存入缓存
            redisTemplate.opsForValue().set(userKey, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(userIpKey, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
            return user;
        } else {
            return JSON.parseObject(userStr, UserDto.class);
        }
    }

    @Override
    public UserDto getByOpenId(String openId) {
        return baseMapper.getByOpenId(openId);
    }
}




