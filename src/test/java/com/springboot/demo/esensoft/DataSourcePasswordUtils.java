package com.springboot.demo.esensoft;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author： dongao
 * @create: 2019/7/22
 * 数据库密码加密工具类
 */
public class DataSourcePasswordUtils {

    /**
     * 加密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String encrypt(String cipherText) throws Exception {
        String encrypt = ConfigTools.encrypt(cipherText);
        return encrypt;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(String cipherText) throws Exception {
        String decrypt = ConfigTools.decrypt(cipherText);
        return decrypt;
    }

    public static void main(String[] args) throws Exception {
        String password = "Shift_123";
        String encrypt = encrypt(password);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);

    }
}

