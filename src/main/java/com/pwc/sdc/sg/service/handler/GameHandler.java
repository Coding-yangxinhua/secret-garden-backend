package com.pwc.sdc.sg.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.common.util.CryptUtil;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.model.dto.UserSubscribeDto;
import com.pwc.sdc.sg.service.UserService;
import com.pwc.sdc.sg.service.UserSubscribeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
@Service
public class GameHandler {
    @Resource
    private UserService userService;

    @Resource
    private UserSubscribeService userSubscribeService;


    public List<Param> handle(String openId, String ip, String data) {
        // 0. 判断请求是否plant.harvestNew
        JSONArray requestArr = JSON.parseArray(data);
        String op = requestArr.getJSONArray(SystemConstant.START_INDEX).getString(SystemConstant.OP_INDEX);
        if (!"plant.harvestNew".equals(op)) {
            return Collections.emptyList();
        }
        // 1. 创建或者查询系统用户
        UserDto user = userService.queryOrCreateUser(openId, ip);
        Long id = user.getId();
        // 2. 查询激活并有效的套餐
        UserSubscribeDto enableSubscribe = userSubscribeService.getUserEnableSubscribe(id);
        if (enableSubscribe == null) {
            return Collections.emptyList();
        }
        // 3. 修改倍率，并重新签名
        return modifyRatio(requestArr, enableSubscribe);
    }

    private List<Param> modifyRatio(JSONArray requestArr, UserSubscribeDto enableSubscribe) {
        JSONArray dataArr = requestArr.getJSONArray(SystemConstant.START_INDEX).getJSONArray(SystemConstant.PARAM_INDEX).getJSONArray(SystemConstant.DATA_INDEX);
        List<Param> requestList = new ArrayList<>();
        int ratio = enableSubscribe.getRatio();
        // 原数组大小，如果size * ratio大于500，则需要拆分成多个请求
        int size = dataArr.size();
        JSONArray ratioArr = new JSONArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < ratio; j++) {
                // 根据ratio复制数量
                ratioArr.add(dataArr.getString(i));
                // > 500拆分到另一个请求
                if (ratioArr.size() > 500) {
                    requestList.add(new Param(copyRequestArr2Str(requestArr, ratioArr)));
                    // 重新存储
                    ratioArr.clear();
                }
            }
        }
        // 将剩余部分放入一个请求
        if (!ratioArr.isEmpty()) {
            requestList.add(new Param(copyRequestArr2Str(requestArr, ratioArr)));
            // 重新存储
            ratioArr.clear();
        }
        return requestList;
    }

    /**
     * 重新签名
     * @param requestList
     * @return
     */
    private List<String> reSign(List<JSONArray> requestList) {
        List<String> signs = new ArrayList<>();
        for (JSONArray jsonArray : requestList) {
            signs.add(CryptUtil.md5(jsonArray));
        }
        return signs;
    }

    /**
     * 修改数据并复制
     * @param requestArr
     * @param ratioArr
     * @return
     */
    private JSONArray copyRequestArr2Str(JSONArray requestArr, JSONArray ratioArr) {
        JSONArray anotherRequestArr = JSON.parseArray(JSON.toJSONString(requestArr));
        anotherRequestArr.getJSONArray(SystemConstant.START_INDEX).getJSONArray(SystemConstant.PARAM_INDEX).set(SystemConstant.DATA_INDEX, JSON.parseArray(ratioArr.toJSONString()));
        return anotherRequestArr;
    }
}
