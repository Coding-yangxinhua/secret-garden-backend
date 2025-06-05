package com.pwc.sdc.sg.common.util;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.SystemConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
public class CryptUtil {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("15561595", "17x2818x28", "1749125076");
        list.sort(String::compareTo);
        System.out.println(list);
    }

    private static final String KEY = "PI$V1aLYs5bx5eBB!&4KtHokbpTfi46Hr38hiP3mOG9eCqlZnRCyEq8Eoapes37@";

    public static String md5(JSONArray requestArr) {
        JSONArray jsonArray = requestArr.getJSONArray(SystemConstant.START_INDEX).getJSONArray(SystemConstant.DATA_INDEX);
        List<String> dataList = jsonArray.getJSONArray(SystemConstant.PARAM_INDEX).toJavaList(String.class);
        String timeStamp = jsonArray.getString(SystemConstant.TIMESTAMP_INDEX);
        String openId = jsonArray.getString(SystemConstant.OPEN_ID_INDEX);
        String data = String.join("", dataList);
        List<String> sortList = Arrays.asList(openId, data, timeStamp);
        sortList.sort(String::compareTo);
        String str = "0=" + sortList.get(0) +
                "&1=" + sortList.get(1) +
                "&2=" + sortList.get(2) +
                "&key=" + KEY;
        return md5(str);
    }

    public static String md5(String str) {
        // 创建 Md5 对象
        MD5 md5 = new MD5();
        // 加密并获取 32 位 MD5 值
        String md5Hex = md5.digestHex(str);
        // 转为大写
        return md5Hex.toUpperCase();
    }

    public static String urlEncode(String str) {
        StringBuilder encodedUrl = new StringBuilder();
        for (char c : str.toCharArray()) {
            switch (c) {
                case '[':
                    encodedUrl.append("%5B");
                    break;
                case ']':
                    encodedUrl.append("%5D");
                    break;
                case '"':
                    encodedUrl.append("%22");
                    break;
                default:
                    encodedUrl.append(c);
                    break;
            }
        }
        return encodedUrl.toString();

    }

}
