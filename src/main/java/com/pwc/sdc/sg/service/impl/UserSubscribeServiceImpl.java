package com.pwc.sdc.sg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.common.bean.Auth;
import com.pwc.sdc.sg.common.enums.StatusEnum;
import com.pwc.sdc.sg.model.UserSubscribe;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.model.dto.UserSubscribeDto;
import com.pwc.sdc.sg.service.UserSubscribeService;
import com.pwc.sdc.sg.mapper.UserSubscribeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_subscribe(用户套餐表)】的数据库操作Service实现
* @createDate 2025-06-04 10:30:51
*/
@Service
public class UserSubscribeServiceImpl extends ServiceImpl<UserSubscribeMapper, UserSubscribe>
    implements UserSubscribeService{

    @Override
    public UserSubscribeDto getUserEnableSubscribe(Long userId) {
        return baseMapper.getUserEnableSubscribe(userId);
    }

    @Override
    public List<UserSubscribeDto> listUserSubscribes(Long userId) {
        return baseMapper.listUserSubscribes(userId);
    }

    @Override
    public void updateUserSubscribe(UserSubscribeDto userSubscribeDto) {
        this.updateById(userSubscribeDto);
    }

    @Override
    public void switchUserSubscribe(Long id, Integer enable) {

    }

    @Override
    public void disableByUserId(Long userId) {
        baseMapper.disableByUserId(userId);
    }
}




