package com.pwc.sdc.sg.common.util;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.SystemConstant;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
public class CryptUtil {

    private static final String KEY = "PI$V1aLYs5bx5eBB!&4KtHokbpTfi46Hr38hiP3mOG9eCqlZnRCyEq8Eoapes37@";

    public static String md5(JSONArray requestArr) {
        JSONArray jsonArray = requestArr.getJSONArray(SystemConstant.START_INDEX).getJSONArray(SystemConstant.DATA_INDEX);
        List<String> dataList = jsonArray.getJSONArray(SystemConstant.PARAM_INDEX).toJavaList(String.class);
        String str = "0=" + jsonArray.getString(SystemConstant.TIMESTAMP_INDEX) +
                "&1=" + jsonArray.getString(SystemConstant.OPEN_ID_INDEX) +
                "&2=" + String.join("", dataList) +
                "&key=" + KEY;
        return md5(str);
    }

    public static String md5(String str) {
        // 加盐
        str += KEY;
        // 创建 Md5 对象
        MD5 md5 = new MD5();
        // 加密并获取 32 位 MD5 值
        String md5Hex = md5.digestHex(str);
        // 转为大写
        return md5Hex.toUpperCase();
    }

}
