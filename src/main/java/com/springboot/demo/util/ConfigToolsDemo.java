package com.springboot.demo.util;

import java.text.MessageFormat;
import com.alibaba.druid.filter.config.ConfigTools;

/**
 * Copyright (C), 2018-2021
 * FileName: ConfigToolsDemo
 * Author:   李兴
 * Date:     2021/7/12 17:28
 * Description: 密码公私钥加密
 */
public class ConfigToolsDemo {

    /**
     * 使用 ConfigTools 中的默认 key进行加密解密
     *
     * @throws Exception
     */
    public static void demo1() throws Exception {

        System.out.println("**** 开始: 使用 ConfigTools 中的默认 key进行加密解密");
        //密码明文，也就是数据库的密码
        String plainText = "ioz2zZYr1t";

        /** 1. 使用 ConfigTools 中的默认 key进行加密解密 */
        String isnull = null;

        // 加密后的密码串
        String pText = ConfigTools.encrypt(isnull, plainText);
        // 解密
        System.out.println(MessageFormat.format("明文密码: {0} 加密后的密码为: {1}", plainText, pText));
        System.out.println(MessageFormat.format("明文密码: {0} 解密后密码为: {1}", plainText, ConfigTools.decrypt(isnull, pText)));
    }

    /**
     * 生成密钥 key, 进行加密解密
     */
    public static void demo2() throws Exception {

        System.out.println("**** 开始: 生成密钥 key, 进行加密解密");
        //密码明文，也就是数据库的密码
        String plainText = "yshd";

        /** 1. 生成 key */
        String[] keyPair = ConfigTools.genKeyPair(2048);
        String PRIVATE_KEY_STRING = keyPair[0];
        String PUBLIC_KEY_STRING = keyPair[1];
        System.out.println(MessageFormat.format("生成的私钥[private Key]: {0} ", PRIVATE_KEY_STRING));
        System.out.println(MessageFormat.format("生成的公钥[public  Key]: {0} ", PUBLIC_KEY_STRING));

        /** 2. 用生成的key加密/解密 */
        // 加密后的密码串
        String pText = ConfigTools.encrypt(PRIVATE_KEY_STRING, plainText);

        System.out.println(MessageFormat.format("明文密码: {0} 加密后的密码为: {1}", plainText, pText));
        System.out.println(MessageFormat.format("明文密码: {0} 解密后密码为: {1}", plainText, ConfigTools.decrypt(PUBLIC_KEY_STRING, pText)));
    }


    /**
     * 指定 key, 进行加密解密
     */
    public static void demo3() throws Exception {

        System.out.println("**** 开始: 指定 key, 进行加密解密");
        //密码明文，也就是数据库的密码
        String plainText = "ioz2zZYr1t";

        /** 指定 key进行加密解密 */
        String PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4P05ueQepxMJdZtm73eFEmL0wTZb7XN+PL7qdvttPhRrgSPLyTGh4sELFCLurOBC2R/rTysJ0K8Z5wuC1N2RYZ+SRQIIa9FvdlO30VrCstfJUGKlsuOqFKqcdm4nG/qOOAu0OKX2MYiwCnoyNl9fLS4jb6I9aNFJRAzlkzKXTH2gVuQxTyKT9z08uMfuxi7BxrTTY9+Ypqp8hqHDo+mN4i1CFKBx0xmUEp9TasiFCPabdbX2KmoPpH2iZZrvg0wSIxwRN42fwmuz+vZYytxq8x0vqFMr2GTpqMCBLYI3Q4JT2YHIfNc7y1LaHJliogL9HlPqz0Aox087vOTjomuXxQIDAQAB";
        String PRIVATE_KEY_STRING = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDg/Tm55B6nEwl1m2bvd4USYvTBNlvtc348vup2+20+FGuBI8vJMaHiwQsUIu6s4ELZH+tPKwnQrxnnC4LU3ZFhn5JFAghr0W92U7fRWsKy18lQYqWy46oUqpx2bicb+o44C7Q4pfYxiLAKejI2X18tLiNvoj1o0UlEDOWTMpdMfaBW5DFPIpP3PTy4x+7GLsHGtNNj35imqnyGocOj6Y3iLUIUoHHTGZQSn1NqyIUI9pt1tfYqag+kfaJlmu+DTBIjHBE3jZ/Ca7P69ljK3GrzHS+oUyvYZOmowIEtgjdDglPZgch81zvLUtocmWKiAv0eU+rPQCjHTzu85OOia5fFAgMBAAECggEAGwZPqMR96hOfT0EbXCg/E7ZPL+YXpK36u9Ey2Jfax1osj5Z9I+2SRGprqj+H67wES5BJ83h39VkTA22eiXMTw2Yna6KS4GCHwu7LZZpBSLoitEUlIhe2lkToBlG04Od3rcO6IhPNPqXsf1WuMlnCVOr11rLmLXD2G/ZwnP22rrkuoZQjkUbvUqYLcSXP+6uS3ao4DaK92600M8FuW8mN1S8T2bojQRUHmC1eUtuBtlkKs5bf3FatsYr0A5ePc1CwcECpO071J495KojZMpJXUAczKivMmJK85tkkCDRDtIhXGgHobxBDzEVL5Lb6riBs7JvHB2oVIFFLeUdHvjjIAQKBgQDwfjgBfof1+Vx4MFgDf/oJg8+7Pav7CwJ+cKoIwoZg+M1+d3TUaYJg9lnMlHX7LzFqO+/vo7hwX1pgqKmJRV6s2HgB2tlRPnn5v2000f4uzEwKCLCGSvdFk28W2asUaEIpdTSEUkAXYabutAAfePldN4AuXIGuV5x2hPUTTjq5vQKBgQDvfxYqb3tgzVAt7NG0ElFioKXy82EHzknRTn/8iCeLpt94ZpoFogljl21hXc6/YkQeFUytcjBBWBvKoYJ4nrQJrwciOZOC2zVYmzXEYZk7CK++CMZUPVHiuxrym+VRZk5bOdzL8I0puovqCtjC/oe0SnJRKE0o7WiAMRU+dXmCqQKBgCRENHftN92dFBe/2pX0h6VFrl7jJvs9psuge9PEWfVnr72BkCm0G4g5vkL+pGgTrtUBYuZ1fwvbJUrsRFzCDEvufPJyqVD6JfTGrFZJ0JYIEvN0agJmbbGdUSRWANmb+1Y7KYdGzAl6nWzj0hzs5lXrV7YmFxxOc/bfiAG4S1zVAoGARRX69ejMxWLUgzryTyiNk47V8Bxm3BKU1qz3XIiGJ9HOWvP18DQK01DGO7UMw8Z78dWhFdN/ugnJko0+jr7xHm3/9852D+ReIh47x1eEFTRvRblQRNKfgXEJ1/xCjVWeutAZMjdE8UmfvLa+SpmT2d0z1tdyUqERaRE/mJHYqGkCgYEA3lUCfHNRlwXZguU4kN/lWwDVZenpurq3UhiJIE9KhYtXMuC8YNOdF9RER+hYYnsCDu/PIET7UMG8Fhe/k+IwYpBkmxZg2uBpl/tFzcCVHLYjcuYxUVo4bGmi42WXhE4U+3F22lwERRhViPf0HCB3qLNCdbPrBWlIv8EIrvUQlIg=";
        // 加密后的密码串
        String pText = "g31I5n/mlZBjZ3exuGppj12e9ofGUsTas12Xel4v+QB4uL04NqShZRLo1JqSLE9AjjdHgip/4p83GfQYdlf7VaIuVqbueSwIGSpqJu4eI/DKwdO6PqM4SUGoTXbkl4rbEC5SoNgOSZC+jk2PtFW3lEKBHXAkVWnvDAidD9Znd+GNMb7uHOYxKQxPkWyKI1eYYjAn9r70VAX5JYpCnINhcLG1p6EgzI0jXZSzyw2BW3eVdgaDfSHAYqL0inxGcvheXW65LuPHNbNSM2tK2md6mmG1U8GhAvUF/to3pax6QfJ7Lt1EEBb+fCKShQTFOO8i8bXV4TyIVkwlmxm3r9BnXA==";
        System.out.println(MessageFormat.format("明文密码: {0} 加密后的密码为: {1}", plainText, ConfigTools.encrypt(PRIVATE_KEY_STRING, plainText)));
        System.out.println(MessageFormat.format("明文密码: {0} 解密后密码为: {1}", plainText, ConfigTools.decrypt(PUBLIC_KEY_STRING, pText)));
    }

    public static void main(String[] args) {

        try {

            // 使用 ConfigTools 中的默认 key进行加密解密
            demo1();

            // 生成密钥 key, 进行加密解密
            demo2();

            // 指定 key, 进行加密解密
            demo3();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
