package com.pwc.sdc.sg.service;

import com.pwc.sdc.sg.model.UserSubscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pwc.sdc.sg.model.dto.UserSubscribeDto;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_subscribe(用户套餐表)】的数据库操作Service
* @createDate 2025-06-04 10:30:51
*/
public interface UserSubscribeService extends IService<UserSubscribe> {

    /**
     * 获取用户已激活的订阅
     *
     * @param userId
     * @return
     */
    UserSubscribeDto getUserEnableSubscribe(Long userId);

    List<UserSubscribeDto> listUserSubscribes(Long userId);

    void updateUserSubscribe(UserSubscribeDto userSubscribeDto);

    void switchUserSubscribe(Long id, Integer enable);
}
