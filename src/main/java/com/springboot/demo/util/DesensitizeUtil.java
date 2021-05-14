package com.springboot.demo.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (C), 2018-2021
 * FileName: DesensitizeUtil
 * Author:   lixing
 * Date:     2021/5/14 17:13
 * Description: 脱敏工具类
 */
public class DesensitizeUtil {
    /**
     * @Description: 保留前面几位 比如 姓名 张**
     * @Author: 张颖辉(yh)
     * @Date: 2018/9/20 16:53
     * @param: [fullName, index]
     * @return: java.lang.String
     * @Version: 1.0
     */
    public static String left(String str, int index) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String name = StringUtils.left(str, index);
        return StringUtils.rightPad(name, StringUtils.length(str), "*");
    }

    /**
     * @Description: 前面保留 index 位明文，后面保留 end 位明文,如：[身份证号] 110****58，前面保留3位明文，后面保留2位明文
     * @Author: 张颖辉(yh)
     * @Date: 2018/9/20 16:47
     * @param: [name, index, end]
     * @return: java.lang.String
     * @Version: 1.0
     */
    public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }


    /**
     * @Description: 保留后面几位 如手机号 *******5678
     * @Author: 张颖辉(yh)
     * @Date: 2018/9/20 16:53
     * @param: [num, end]
     * @return: java.lang.String
     * @Version: 1.0
     */
    public static String right(String str, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*");
    }
}
