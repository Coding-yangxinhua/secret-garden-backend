package com.pwc.sdc.sg.common.bean;

import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.util.CryptUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Xinhua X Yang
 */
@Getter
@Setter
public class Param implements Serializable {
    JSONArray requestArr;

    String sign;

    public Param(JSONArray requestArr) {
        this.requestArr = requestArr;
        this.sign = CryptUtil.md5(requestArr);
    }
}
