package com.pwc.sdc.sg.service;

import com.pwc.sdc.sg.model.UserCardCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_card_code(用户卡密表)】的数据库操作Service
* @createDate 2025-06-10 16:12:08
*/
public interface UserCardCodeService extends IService<UserCardCode> {

    void checkOrCreate(String cardCode);

}
