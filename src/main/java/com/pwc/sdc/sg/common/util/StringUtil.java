package com.pwc.sdc.sg.common.util;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Xinhua X Yang
 */
public class StringUtil {

    public static String generateActivationCode() {
        // 定义字符集：大写字母和数字
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // 生成4组随机字符
        String part1 = RandomUtil.randomString(chars, 4);
        String part2 = RandomUtil.randomString(chars, 4);
        String part3 = RandomUtil.randomString(chars, 4);
        String part4 = RandomUtil.randomString(chars, 4);
        // 拼接成最终的激活码格式
        return part1 + "-" + part2 + "-" + part3 + "-" + part4;
    }
}
