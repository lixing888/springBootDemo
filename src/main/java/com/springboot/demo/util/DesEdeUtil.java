package com.springboot.demo.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author lixing
 * DESede加密解密
 */
public class DesEdeUtil {

    public static void main(String[] args) {
        String content = "test中文";
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
        System.out.println("加密为16进制字符串"+encryptHex+"====解密为字符串"+decryptStr);
    }


}
