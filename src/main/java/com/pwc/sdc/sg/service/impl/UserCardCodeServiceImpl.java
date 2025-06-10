package com.pwc.sdc.sg.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.common.bean.Auth;
import com.pwc.sdc.sg.common.enums.StatusEnum;
import com.pwc.sdc.sg.common.util.StringUtil;
import com.pwc.sdc.sg.model.Subscribe;
import com.pwc.sdc.sg.model.UserCardCode;
import com.pwc.sdc.sg.model.UserSubscribe;
import com.pwc.sdc.sg.model.dto.CardCodeDto;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.service.CardCodeService;
import com.pwc.sdc.sg.service.SubscribeService;
import com.pwc.sdc.sg.service.UserCardCodeService;
import com.pwc.sdc.sg.mapper.UserCardCodeMapper;
import com.pwc.sdc.sg.service.UserSubscribeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_card_code(用户卡密表)】的数据库操作Service实现
* @createDate 2025-06-10 16:12:08
*/
@Service
public class UserCardCodeServiceImpl extends ServiceImpl<UserCardCodeMapper, UserCardCode>
    implements UserCardCodeService{

    @Resource
    private CardCodeService cardCodeService;

    @Resource
    private SubscribeService subscribeService;


    @Resource
    private UserSubscribeService userSubscribeService;

    @Override
    @Transactional
    public void checkOrCreate(String cardCode) {
        if (!StringUtils.hasText(cardCode)) {
            return;
        }
        UserDto user = Auth.user();
        Long userId = user.getId();
        CardCodeDto cardCodeDto = cardCodeService.getByCode(userId, cardCode);
        // code不存在
        if (cardCodeDto == null) {
            return;
        }
        Long dbUserId = cardCodeDto.getUserId();
        // 用户已使用过此激活码
        if (dbUserId != null) {
            return;
        }
        // 判断使用次数
        Integer validUses = cardCodeDto.getValidUses();
        // 此激活码不能再使用
        if (validUses == 0) {
            return;
        }
        Long cardCodeId = cardCodeDto.getId();
        Long subscribeId = cardCodeDto.getSubscribeId();
        // 创建user code记录
        UserCardCode userCardCode = new UserCardCode();
        userCardCode.setCodeId(cardCodeId);
        userCardCode.setUserId(userId);
        // 找到对应套餐
        Subscribe subscribe = subscribeService.getById(subscribeId);
        // 创建user subscribe记录
        UserSubscribe userSubscribe = new UserSubscribe();
        userSubscribe.setUserId(userId);
        userSubscribe.setSubscribeId(subscribeId);
        userSubscribe.setEnable(StatusEnum.ENABLE.value());
        // 设置有效期
        if (subscribe.getValidTime() != -1) {
            // 获得n天后的一天开始
            Date utilDate = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), subscribe.getValidTime() + 1));
            userSubscribe.setValidUtil(utilDate);
        }
        userSubscribe.setRemainingUses(subscribe.getValidUses());
        // 入库
        this.save(userCardCode);
        // 禁用其他启用的订阅
        userSubscribeService.disableByUserId(userId);
        userSubscribeService.save(userSubscribe);
    }
}




