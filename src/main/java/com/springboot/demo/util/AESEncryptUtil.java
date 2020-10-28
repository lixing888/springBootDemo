package com.springboot.demo.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @program: springBootDemo
 * @description: AES加密工具类
 * @author: lixing
 * @create: 2020-10-25 11:26
 **/
public class AESEncryptUtil {
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";

    public enum AES_KEY_SIZE {
        /**
         * 秘钥长度为128
         */
        KEY_LENGTH_128(128),
        /**
         * 密钥长度为192
         */
        KEY_LENGTH_192(192),
        /**
         * 秘钥长度为256
         */
        KEY_LENGTH_256(256);

        AES_KEY_SIZE(final int size) {
            this.keyLength = size;
        }

        private final int keyLength;

        public int getKeyLength() {
            return this.keyLength;
        }
    }

    /**
     * 加密算法，分组模式，填充模式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    public static byte[] generKey() {
        return generKey(AES_KEY_SIZE.KEY_LENGTH_256);
    }

    public static byte[] generKey(final AES_KEY_SIZE keySize) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
            AES_KEY_SIZE aesKeySize = Optional.ofNullable(keySize).orElse(AES_KEY_SIZE.KEY_LENGTH_128);
            kgen.init(aesKeySize.getKeyLength());
            SecretKey secretKey = kgen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generKeyToBase64(final AES_KEY_SIZE keySize) {
        return Base64.encodeBase64String(generKey(keySize));
    }

    public static String generKeyToBase64() {
        return Base64.encodeBase64String(generKey());
    }

    protected static Key toKey(final byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    protected static Key toKey(final String pwd) {
        byte[] key = Base64.decodeBase64(pwd);
        return toKey(key);
    }


    /**
     * @param content 明文数据
     * @param pwd     Base64编码的密钥
     * @return 加密数据
     */
    public static byte[] encrypt(final byte[] content, final String pwd) {
        return encryptService(content, pwd, Cipher.ENCRYPT_MODE);
    }

    /**
     * @param content 明文数据
     * @param pwd     Base64编码的密钥
     * @return 加密数据
     */
    public static byte[] encrypt(final byte[] content, final byte[] pwd) {
        return encryptService(content, pwd, Cipher.ENCRYPT_MODE);
    }

    /**
     * @param content 密文数据
     * @param pwd     Base64编码的密钥
     * @return 明文数据
     */
    public static byte[] decrypt(final byte[] content, final String pwd) {
        return encryptService(content, pwd, Cipher.DECRYPT_MODE);
    }

    /**
     * @param content 密文数据
     * @param pwd     Base64编码的密钥
     * @return 明文数据
     */
    public static byte[] decrypt(final byte[] content, final byte[] pwd) {
        return encryptService(content, pwd, Cipher.DECRYPT_MODE);
    }

    private static byte[] encryptService(final byte[] content, final byte[] pwd, int model) {
        Objects.requireNonNull(content, "明文数据不能为空");
        Objects.requireNonNull(pwd, "密钥信息不能为空");
        try {
            initBC();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            cipher.init(model, toKey(pwd));
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }


    private static byte[] encryptService(final byte[] content, final String pwd, int model) {
        Objects.requireNonNull(content, "明文数据不能为空");
        Objects.requireNonNull(pwd, "密钥信息不能为空");
        try {
            initBC();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            cipher.init(model, toKey(pwd));
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initBC() {
        Security.addProvider(new BouncyCastleProvider());
    }
}
