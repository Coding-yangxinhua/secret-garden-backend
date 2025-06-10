package com.pwc.sdc.sg.mapper;

import com.pwc.sdc.sg.model.UserSubscribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pwc.sdc.sg.model.dto.UserSubscribeDto;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_subscribe(用户套餐表)】的数据库操作Mapper
* @createDate 2025-06-04 10:30:51
* @Entity generator.domain.UserSubscribe
*/
public interface UserSubscribeMapper extends BaseMapper<UserSubscribe> {

    UserSubscribeDto getUserEnableSubscribe(Long userId);

    List<UserSubscribeDto> listUserSubscribes(Long userId);

    void switchUserSubscribe(Long id, Integer enable);

    void disableByUserId(Long userId);
}




