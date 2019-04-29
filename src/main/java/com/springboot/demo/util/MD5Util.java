package com.springboot.demo.util;


import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * MD5签名工具类
 *
 * @author v-lixing.ea
 */
@Slf4j
public class MD5Util {

    private MD5Util() {
    }

    /**
     * 对字符串进行md5加密
     *
     * @param md5String
     * @param md5Key
     * @return md5加密后的密文
     */
    public static String md5String(String md5String, String md5Key) {
        return md5Encoder(md5String + md5Key);
    }

    public static String md5Encoder(String s) {
        return md5Encoder(s, "utf-8");
    }

    public static String md5Encoder(String s, String charset) {
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("MD5Encoder异常：" + e.getMessage());
            return null;
        }
    }


}
