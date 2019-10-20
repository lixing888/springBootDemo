package com.springboot.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 维吉尼亚加密解密
 *
 * @author lixing
 */
public class VirginiaPassword {

    //Virginia Password table
    private static final String VIRGINIA_PASSWORD_TABLE_ARRAY[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static final List<String> VIRGINIA_PASSWORD_TABLE_ARRAY_LIST = Arrays.asList(VIRGINIA_PASSWORD_TABLE_ARRAY);

    /**
     * 判断对象是否为空，为空返回false，非空返回true
     *
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
        return Optional.ofNullable(object).isPresent();
    }

    /**
     * 判断字符串非null，并且不为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isStringNotEmpty(String str) {
        return isNotNull(str) && str.trim() != "";
    }

    /**
     * C = P + K (mod 26).
     * 维吉尼亚加密
     *
     * @param original  原文
     * @param secretKey 密钥
     * @return
     */
    public static String virginiaEncode(String original, String secretKey) {
        if (isStringNotEmpty(secretKey) && isStringNotEmpty(original)) {
            char[] secretCharArray = secretKey.toCharArray();
            char[] originalCharArray = original.toCharArray();
            int length = originalCharArray.length;

            List<Integer> list = getSecretKeyList(secretCharArray, length);

            StringBuffer sb = new StringBuffer();
            for (int m = 0; m < length; m++) {
                char ch = originalCharArray[m];
                int charIndex = VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.indexOf(String.valueOf(ch).toUpperCase());
                if (charIndex == -1) {
                    sb.append(String.valueOf(ch));
                    continue;
                }

                int size = VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.size();
                //C = P + K (mod 26). 获取偏移量索引
                int tmpIndex = (charIndex + list.get(m)) % size;
                sb.append(VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.get(tmpIndex));

            }
            return sb.toString();
        }
        return null;
    }

    /**
     * P = C - K (mod 26).
     * 维吉尼亚解密
     *
     * @param cipherText 密文
     * @param secretKey  密钥
     * @return
     */
    public static String virginiaDecode(String cipherText, String secretKey) {
        if (isStringNotEmpty(cipherText) && isStringNotEmpty(secretKey)) {
            char[] secretCharArray = secretKey.toCharArray();
            char[] cipherCharArray = cipherText.toCharArray();
            int length = cipherCharArray.length;

            List<Integer> list = getSecretKeyList(secretCharArray, length);
            StringBuffer sb = new StringBuffer();
            for (int m = 0; m < length; m++) {
                char ch = cipherCharArray[m];
                int charIndex = VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.indexOf(String.valueOf(ch).toUpperCase());
                if (charIndex == -1) {
                    sb.append(String.valueOf(ch));
                    continue;
                }

                int size = VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.size();
                //P = C - K (mod 26). 模逆运算求索引
                int len = (charIndex - list.get(m)) % size;
                //索引小于零，加模得正索引
                int tmpIndex = len < 0 ? len + size : len;
                sb.append(VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.get(tmpIndex));

            }

            return sb.toString();
        }

        return null;
    }

    /**
     * 获取密钥集合
     *
     * @param secretCharArray 密钥字符数组
     * @param length          原文或密文的长度
     * @return
     */
    private static List<Integer> getSecretKeyList(char[] secretCharArray, int length) {
        List<Integer> list = new ArrayList<Integer>();
        for (char c : secretCharArray) {
            int index = VIRGINIA_PASSWORD_TABLE_ARRAY_LIST.indexOf(String.valueOf(c).toUpperCase());
            list.add(index);
        }


        if (list.size() > length) {
            //截取和目标原文或密文相同长度的集合
            list = list.subList(0, length);
        } else {
            Integer[] keyArray = list.toArray(new Integer[list.size()]);
            int keySize = list.size();
            //整除
            int count = length / keySize;
            for (int i = 2; i <= count; i++) {
                for (Integer integer : keyArray) {
                    list.add(integer);
                }
            }
            //求余
            int mold = length % keySize;
            if (mold > 0) {
                for (int j = 0; j < mold; j++) {
                    list.add(keyArray[j]);
                }

            }
        }

        return list;
    }

    public static void main(String[] args) {
        String originStr = "hello world lixing";
        String secretKey = "ok";
        String virginiaEncode = virginiaEncode(originStr, secretKey);
        System.out.println("维吉尼亚加密：" + virginiaEncode);
        System.out.println("维吉尼亚解密：" + virginiaDecode(virginiaEncode, secretKey));
    }
}