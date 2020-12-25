package com.springboot.demo.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @program: springBootDemo
 * @description: 基于国标SM4算法封装的工具类.
 * @author: lixing
 * @create: 2020-12-21 18:20
 **/
@Slf4j
public class SM4Util {

    // 初始化算法提供者信息.
    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据编码.
     */
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 秘钥空间大小.
     */
    public static final int SM4_KEY_SIZE = 128;

    /**
     * 默认秘钥空间为128，Key的长度是16.
     */
    public static final int SM4_KEY_LENGTH = 16;

    /**
     * 算法编号.
     */
    public static final String SM4_NAME = "SM4";

    /**
     * CBC模式串.
     */
    public static final String SM4_NAME_ECB = "SM4/CBC/PKCS5Padding";

    /**
     * 首次加密初始向量.
     */
    public static final byte[] SM4_KEY_IV = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31};

    /**
     * 生成SM4算法的KEY.
     *
     * @return 生成的SM4秘钥.
     * @throws Exception .
     */
    public static String generateSm4Key() throws Exception {
        return Base64.encodeBase64String(generateSm4Key(SM4_KEY_SIZE));
    }

    /**
     * 生成SM4算法的KEY.
     *
     * @param sm4Key 指定秘钥空间大小.
     * @return 生成的SM4秘钥.
     * @throws Exception .
     */
    private static byte[] generateSm4Key(int sm4Key) throws Exception {
        KeyGenerator keyGenerator = null;

        keyGenerator = KeyGenerator.getInstance(SM4_NAME, BouncyCastleProvider.PROVIDER_NAME);
        keyGenerator.init(sm4Key, new SecureRandom());
        return keyGenerator.generateKey().getEncoded();
    }

    /**
     * 对文本内容进行加密.
     *
     * @param plainText 待加密明文内容.
     * @param sm4Key    SM4秘钥.
     * @return 加密的密文.
     */
    public static String encodeText(String plainText, String sm4Key) throws Exception {
        return encodeByCbc(plainText, sm4Key);
    }

    /**
     * 对文本密文进行解密.
     *
     * @param cipherText 待解密密文.
     * @param sm4Key     SM4秘钥.
     * @return 解密的明文.
     * @throws Exception .
     */
    public static String decodeText(String cipherText, String sm4Key) throws Exception {
        return decodeByCbc(cipherText, sm4Key);
    }

    /**
     * 对字节数组内容进行加密.
     *
     * @param plainBytes 待加密明文内容.
     * @param sm4Key     SM4秘钥.
     * @return 加密的密文.
     */
    public static byte[] encodeBytes(byte[] plainBytes, String sm4Key) throws Exception {
        byte[] sm4KeyBytes = null;
        byte[] cipherBytes = null;

        try {
            // 秘钥位数处理转换.
            sm4Key = sm4KeyPadding(sm4Key);
            // base64格式秘钥转换：sm4Key to byte[].
            sm4KeyBytes = Base64.decodeBase64(sm4Key);
            // 使用转换后的原文和秘钥进行加密操作.
            cipherBytes = encodeCbcPadding(plainBytes, sm4KeyBytes);
            // 对加密结果使用base64进行编码：cipherBytes to Base64格式.
        } catch (Exception ex) {
            log.error("SM4Util.encodeByCbc.", ex);
            cipherBytes = new byte[]{1};
        }
        return cipherBytes;
    }

    /**
     * 对字节数组密文进行解密.
     *
     * @param cipherBytes 待解密密文.
     * @param sm4Key      SM4秘钥.
     * @return 解密的明文.
     */
    public static byte[] decodeBytes(byte[] cipherBytes, String sm4Key) throws Exception {
        byte[] keyBts = null;
        byte[] plainBytes = new byte[0];

        try {
            // 秘钥位数处理转换.
            sm4Key = sm4KeyPadding(sm4Key);
            // base64格式秘钥转换：sm4Key to byte[].
            keyBts = Base64.decodeBase64(sm4Key);
            // 使用转换后的密文和秘钥进行解密操作
            plainBytes = decryptCbcPadding(cipherBytes, keyBts);
        } catch (Exception ex) {
            log.error("SM4Util.decodeByCbc.", ex);
            plainBytes = new byte[]{0};
        }
        return plainBytes;
    }

    /**
     * 基于CBC模式进行SM4加密.
     *
     * @param plainText 待加密明文.
     * @param sm4Key    Base64格式秘钥.
     * @return 加密后Base64格式密文.
     * @throws Exception 可能异常.
     */
    private static String encodeByCbc(String plainText, String sm4Key) throws Exception {
        String cipherText = "";
        byte[] sm4KeyBytes = null;
        byte[] plainBytes = null;
        byte[] cipherBytes = null;

        try {
            // 秘钥位数处理转换.
            sm4Key = sm4KeyPadding(sm4Key);
            // base64格式秘钥转换：sm4Key to byte[].
            sm4KeyBytes = Base64.decodeBase64(sm4Key);
            // String格式原文转换：plainText to byte[].
            plainBytes = plainText.getBytes(CHARSET_UTF8);
            // 使用转换后的原文和秘钥进行加密操作.
            cipherBytes = encodeCbcPadding(plainBytes, sm4KeyBytes);
            // 对加密结果使用base64进行编码：cipherBytes to Base64格式.
            cipherText = Base64.encodeBase64String(cipherBytes);
        } catch (Exception ex) {
            log.error("SM4Util.encodeByCbc.", ex);
            cipherText = "";
        }
        return cipherText;
    }

    /**
     * SM4算法的CBC模式加密.
     *
     * @param plainBytes 待加密明文.
     * @param sm4Key     Base64格式秘钥.
     * @return 加密后byte[]格式密文.
     * @throws Exception 可能异常.
     */
    private static byte[] encodeCbcPadding(byte[] plainBytes, byte[] sm4Key) throws Exception {
        Cipher cipher = null;

        cipher = generateSm4EcbCipher(SM4_NAME_ECB, Cipher.ENCRYPT_MODE, sm4Key);
        return cipher.doFinal(plainBytes);
    }

    /**
     * 基于CBC模式进行SM4解密.
     *
     * @param cipherText 待解密密文.
     * @param sm4Key     Base64格式秘钥.
     * @return 解密后原文.
     * @throws Exception 可能异常.
     */
    private static String decodeByCbc(String cipherText, String sm4Key) throws Exception {
        String plainText = "";
        byte[] keyBts = null;
        byte[] cipherBts = null;
        byte[] plainBytes = new byte[0];

        try {
            // 秘钥位数处理转换.
            sm4Key = sm4KeyPadding(sm4Key);
            // base64格式秘钥转换：sm4Key to byte[].
            keyBts = Base64.decodeBase64(sm4Key);
            // base64格式密文转换：cipherText to byte[].
            cipherBts = Base64.decodeBase64(cipherText);
            // 使用转换后的密文和秘钥进行解密操作
            plainBytes = decryptCbcPadding(cipherBts, keyBts);
            // 将解密结果转换为字符串：srcData to String.
            plainText = new String(plainBytes, CHARSET_UTF8);
        } catch (Exception ex) {
            log.error("SM4Util.decodeByCbc.", ex);
            plainText = "";
        }
        return plainText;
    }

    /**
     * SM4算法的CBC模式解密.
     *
     * @param cipherBytes 待加密密文.
     * @param sm4Key      Base64格式秘钥.
     * @return 解密后byte[]格式密文.
     * @throws Exception 可能异常.
     */
    private static byte[] decryptCbcPadding(byte[] cipherBytes, byte[] sm4Key) throws Exception {
        Cipher cipher = null;

        cipher = generateSm4EcbCipher(SM4_NAME_ECB, Cipher.DECRYPT_MODE, sm4Key);
        return cipher.doFinal(cipherBytes);
    }

    /**
     * 针对错误的秘钥进行补齐或除余操作.
     *
     * @param sm4Key Base64格式秘钥.
     * @return 补齐或除余后的结果.
     */
    private static String sm4KeyPadding(String sm4Key) {
        String targetSm4Key = null;
        byte[] sm4KeyBytes = null;
        byte[] targetSm4KeyBts = null;

        try {
            if (null == sm4Key) {
                targetSm4Key = "";
                return targetSm4Key;
            }
            sm4KeyBytes = Base64.decodeBase64(sm4Key);
            // 若Key超长，则除去多余的内容.
            if (sm4KeyBytes.length > SM4_KEY_LENGTH) {
                targetSm4KeyBts = new byte[SM4_KEY_LENGTH];
                System.arraycopy(sm4KeyBytes, 0, targetSm4KeyBts, 0, SM4_KEY_LENGTH);

            }
            // 若Key较短，则补齐多余的内容.
            else if (sm4KeyBytes.length < SM4_KEY_LENGTH) {
                targetSm4KeyBts = new byte[SM4_KEY_LENGTH];
                System.arraycopy(sm4KeyBytes, 0, targetSm4KeyBts, 0, sm4KeyBytes.length);
                Arrays.fill(targetSm4KeyBts, sm4KeyBytes.length, SM4_KEY_LENGTH, (byte) 1);
            } else {
                targetSm4KeyBts = sm4KeyBytes;
            }
        } catch (Exception ex) {
            log.error("SM4Util.sm4KeyPadding.", ex);
            targetSm4KeyBts = new byte[0];
        }
        // 以Base64格式返回Key.
        return Base64.encodeBase64String(targetSm4KeyBts);
    }

    /**
     * 生成SM4算法实例.
     *
     * @param sm4Name 算法名称.
     * @param sm4Mode 加密模式.
     * @param sm4Key  秘钥内容.
     * @return SM4算法实例.
     * @throws Exception 可能异常.
     */
    private static Cipher generateSm4EcbCipher(String sm4Name, int sm4Mode, byte[] sm4Key) throws Exception {
        Cipher cipher = null;
        Key secretKey = null;
        IvParameterSpec ivParameterSpec = null;

        cipher = Cipher.getInstance(sm4Name, BouncyCastleProvider.PROVIDER_NAME);
        secretKey = new SecretKeySpec(sm4Key, SM4_NAME);
        ivParameterSpec = new IvParameterSpec(SM4_KEY_IV);
        cipher.init(sm4Mode, secretKey, ivParameterSpec);
        return cipher;
    }

    public static void main(String[] args) throws Exception {
        String sm4Key = null;
        String plainText = "This is 一段明文内容！";
        String cipherText = null;

        System.out.println("----------------------- 获取SM4秘钥 -------------------------");
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            log.info("运行环境没有BouncyCastleProvider security provider BC not found");
            Security.addProvider(new BouncyCastleProvider());
        }
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) != null) {
            double version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).getVersion();
            log.info("原有version=" + version);
        }
        sm4Key = SM4Util.generateSm4Key();
        System.out.println("秘钥：" + sm4Key);
        System.out.println();
        System.out.println("----------------------- 文本加解密测试 -------------------------");
        // 文本加解密测试.
        System.out.println("明文：" + plainText);
        cipherText = SM4Util.encodeText(plainText, sm4Key);
        System.out.println("密文：" + cipherText);
        plainText = SM4Util.decodeText(cipherText, sm4Key);
        System.out.println("解密明文：" + plainText);
        System.out.println();
        System.out.println("----------------------- 字节数组加解密测试 -------------------------");
        // 字节数组加解密测试.
        byte[] plainBytes = plainText.getBytes("UTF-8");
        byte[] cipherBytes = null;
        System.out.println("明文：" + Arrays.toString(plainBytes));
        cipherBytes = SM4Util.encodeBytes(plainBytes, sm4Key);
        System.out.println("密文：" + Arrays.toString(cipherBytes));
        plainBytes = SM4Util.decodeBytes(cipherBytes, sm4Key);
        System.out.println("解密明文：" + Arrays.toString(plainBytes));
        System.out.println();
    }

}
