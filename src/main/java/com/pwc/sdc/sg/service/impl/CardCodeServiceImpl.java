package com.pwc.sdc.sg.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.util.StringUtil;
import com.pwc.sdc.sg.model.CardCode;
import com.pwc.sdc.sg.model.dto.CardCodeDto;
import com.pwc.sdc.sg.service.CardCodeService;
import com.pwc.sdc.sg.mapper.CardCodeMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author Xinhua X Yang
* @description 针对表【sg_card_code(卡密表)】的数据库操作Service实现
* @createDate 2025-06-10 16:12:08
*/
@Service
public class CardCodeServiceImpl extends ServiceImpl<CardCodeMapper, CardCode>
    implements CardCodeService{

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public CardCodeDto getByCode(Long userId, String code) {
        String key = SystemConstant.USER_CARD_PREFIX + userId + "_" + code;
        String userCardCodeStr = redisTemplate.opsForValue().get(key);
        CardCodeDto cardCodeDto;
        if (!StringUtils.hasText(userCardCodeStr)) {
            cardCodeDto = baseMapper.getByCode(userId, code);
            if (cardCodeDto != null && cardCodeDto.getUserId() != null) {
                redisTemplate.opsForValue().set(key, JSON.toJSONString(cardCodeDto), 24, TimeUnit.HOURS);
            }
        } else {
            cardCodeDto = JSON.parseObject(userCardCodeStr, CardCodeDto.class);
        }
        return cardCodeDto;
    }

    @Override
    public List<String> generator(Long subscribeId, Integer validUses, Integer count) {
        List<CardCode> cardCodes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String code = StringUtil.generateActivationCode();
            CardCode cardCode = new CardCode();
            cardCode.setCode(code);
            cardCode.setSubscribeId(subscribeId);
            cardCode.setValidUses(validUses);
            cardCodes.add(cardCode);
        }
        this.saveBatch(cardCodes);
        return cardCodes.stream().map(CardCode::getCode).collect(Collectors.toList());
    }

    @Override
    public void countDown(Long id) {
        baseMapper.countDown(id);
    }
}




