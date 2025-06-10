package com.pwc.sdc.sg.mapper;

import com.pwc.sdc.sg.model.CardCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pwc.sdc.sg.model.dto.CardCodeDto;

/**
* @author Xinhua X Yang
* @description 针对表【sg_card_code(卡密表)】的数据库操作Mapper
* @createDate 2025-06-10 16:12:08
* @Entity generator.domain.CardCode
*/
public interface CardCodeMapper extends BaseMapper<CardCode> {
    CardCodeDto getByCode(Long userId, String code);

    void countDown(Long id);
}




