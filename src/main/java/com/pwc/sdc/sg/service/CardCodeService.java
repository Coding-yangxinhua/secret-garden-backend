package com.pwc.sdc.sg.service;

import com.pwc.sdc.sg.model.CardCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pwc.sdc.sg.model.dto.CardCodeDto;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【sg_card_code(卡密表)】的数据库操作Service
* @createDate 2025-06-10 16:12:08
*/
public interface CardCodeService extends IService<CardCode> {
    CardCodeDto getByCode(Long userId, String code);

    List<String> generator(Long subscribeId, Integer validUses, Integer count);
}
