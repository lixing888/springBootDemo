package com.springboot.demo.third;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: springBootDemo
 * @description: 腾讯云短信
 * @author: lixing
 * @create: 2020-11-07 21:22
 **/
@Data
@ConfigurationProperties(prefix = "tencentcloud.sms")
public class SendSmsProperties {

    private String secretId;
    private String secretKey;
    private String appId;

}
