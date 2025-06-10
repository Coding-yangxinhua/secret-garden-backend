package com.pwc.sdc.sg.controller.api;

import com.pwc.sdc.sg.common.bean.Auth;
import com.pwc.sdc.sg.common.enums.StatusEnum;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.model.dto.UserSubscribeDto;
import com.pwc.sdc.sg.service.UserSubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("userSubscribe")
@Slf4j
public class UserSubscribeController {
    @Resource
    private UserSubscribeService userSubscribeService;

    @GetMapping("list")
    public ResponseEntity<List<UserSubscribeDto>> list() {
        UserDto user = Auth.user();
        return ResponseEntity.ok(userSubscribeService.listUserSubscribes(user.getId()));
    }

    @GetMapping("switchSubscribe")
    @Transactional
    public ResponseEntity<List<UserSubscribeDto>> switchSubscribe(Long subscribeId, Integer enable) {
        UserDto user = Auth.user();
        // 如果是要开启订阅，先关闭用户其他订阅
        if (StatusEnum.ENABLE.value().equals(enable)) {
            UserSubscribeDto userSubscribeDto = new UserSubscribeDto();
            userSubscribeDto.setUserId(subscribeId);
            userSubscribeDto.setEnable(StatusEnum.DISABLE.value());
        }
        // 关闭所有订阅
        return ResponseEntity.ok(userSubscribeService.listUserSubscribes(user.getId()));
    }

}
