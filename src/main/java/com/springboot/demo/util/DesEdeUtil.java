package com.springboot.demo.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.druid.filter.config.ConfigTools;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lixing
 * DESede加密解密
 */
public class DesEdeUtil {

    public static void main(String[] args) {
        String content = "李兴";
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, key);
        /**
         * 加密
         */
        byte[] encrypt = des.encrypt(content);
        /**
         * 解密
         */
        byte[] decrypt = des.decrypt(encrypt);
        /**
         * 加密为16进制字符串（Hex表示）
         */
        String encryptHex = des.encryptHex(content);
        /**
         * 解密为字符串
         */
        String decryptStr = des.decryptStr(encryptHex);
        System.out.println("null字符串是否blank:" + StringUtils.isNotBlank("null"));
        System.out.println("加密为16进制字符串" + encryptHex + "====解密为字符串" + decryptStr);

        //druid连接池-数据库密码的解密
        String pwd = "ezVoA1vrUGFi2TucJPjbYds8dNThmEV5T0fPJfaKnr4aYvBWfYjUQFnlv2x3PhnxWFjBlKRUjl+9Rkppo7QDjw==";
        String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAImsOUumxBBqGCC5V+kdapLDLU7F9XtgdobRnfFnI9rUYReRnSuEQ80Ap4aogCRmZFuxyCMJ8ixlqb9UAHfdZDkCAwEAAQ==";
        try {
            String depwd = ConfigTools.decrypt(pub, pwd);
            System.out.println("druid连接池-数据库密码的解密：" + depwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
