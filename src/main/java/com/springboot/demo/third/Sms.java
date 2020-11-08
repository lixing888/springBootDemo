package com.springboot.demo.third;

import lombok.Data;

/**
 * @program: springBootDemo
 * @description: 短信参数
 * @author: lixing
 * @create: 2020-11-07 21:24
 **/
@Data
public class Sms {

    /**
     * 签名
     */
    private String sign;
    /**
     * 模板
     */
    private String templateId;
    /**
     * 手机号
     */
    private String[] mobile;
    /**
     * 模板参数 {}
     */
    private String[] params;
}
