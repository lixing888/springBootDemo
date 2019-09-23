package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lixing
 * @version 1.0
 * @name TokenUtil
 * @description 用来处理token
 */
@Slf4j
public class TokenUtil {

    TokenUtil(){
        /* set something here */
    }
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final  String PRIVATE_KEY = "88fe68c5-8fec-45df-b94c-84c07689aa9f";
    public static final  int MINUTE = 5;

    /**
     *
     * @param accessToken
     * @param timestamp
     * @return
     * @throws Exception
     */
    public static boolean judgeToken(String accessToken, String timestamp, String randomNum) {
        Long time = Long.valueOf(timestamp);
        Long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - time <= MINUTE * 60) {
            String tokenStr = PRIVATE_KEY + timestamp + randomNum;
            String localtoken = getToken(tokenStr);
            if (localtoken != null && localtoken.equals(accessToken)) {
                return true;
            }
        }
        return false;
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 这里的str 由secertKey+timestamp+11位randomNumber组成
     * @param str
     * @return
     */
    public static String getToken(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            log.error("TokenUtil:getToken:Exception", e);
            return null;
        }
    }

    /**
     * wfc字符串加密生成token
     * @param source 需要加密的字符串
     * @param hashType 加密类型
     * @return
     */
    public static String getHash(String source, String hashType) {
        String restr = "";
        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            md.update(source.getBytes()); // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] encryptStr = md.digest(); // 获得密文完成哈希计算,产生128 位的长整数
            char[] str = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
                byte byte0 = encryptStr[i]; // 取第 i 个字节
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = HEX_DIGITS[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            restr = new String(str);
        } catch (NoSuchAlgorithmException e) {
            log.error("TokenUtil:getHash:Exception", e);
        }
        return restr; // 换后的结果转换为字符串
    }

}
