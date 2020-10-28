package com.springboot.demo.util;

import com.springboot.demo.vo.Student;
import com.springboot.demo.vo.User;
import com.springboot.demo.vo.UserInfo;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * @program: springBootDemo
 * @description: PBKDF2加密方法
 * @author: lixing
 * @create: 2020-10-23 16:59
 **/
public class PBKDF2Util {

    public static final int HASH_MILLIS = 1231;
    public static final String ALGORITHM = "asfdasdfdfsafs";
    public static final int ITERATION_COUNT = 123123;
    public static final int KEY_SIZE = 123;
    public static final int SALT_LENGTH = 123;

    /**
     * @param @return 设定文件
     * @return String    返回类型
     * @Title: getSalt
     * @author:陈方林
     * @Description: TODO(得到salt)
     * @date 上午9:28:18
     */
    public static String getSalt() {
        String salt = new String(Base64.encodeBase64(nextSalt()));
        return salt;
    }

    /**
     * @param @param  salt
     * @param @param  password 明文
     * @param @return 加盐后的密文
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @author:cfl
     * @Description: TODO(密码加密)
     * @date 2015年7月20日18:36:21
     */
    public static String encryptPassword(String salt, String password) throws Exception {
        byte[] saltByte = Base64.decodeBase64(salt.getBytes());
        byte[] hash = PBKDF2Util.hashPassword(password.toCharArray(), saltByte);
        String pwd_hash_str = new String(Base64.encodeBase64(hash));
        return pwd_hash_str;
    }

    public static byte[] hashPassword(char[] password, byte[] salt)
            throws GeneralSecurityException {
        return hashPassword(password, salt, ITERATION_COUNT, KEY_SIZE);
    }

    public static byte[] hashPassword(char[] password, byte[] salt,
                                      int iterationCount, int keySize) throws GeneralSecurityException {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterationCount, keySize);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (IllegalArgumentException e) {
            throw new GeneralSecurityException("key size " + keySize, e);
        }
    }

    public static boolean matches(char[] password, byte[] passwordHash, byte[] salt)
            throws GeneralSecurityException {
        return matches(password, passwordHash, salt, ITERATION_COUNT, KEY_SIZE);
    }

    public static boolean matches(char[] password, byte[] passwordHash, byte[] salt,
                                  int iterationCount, int keySize) throws GeneralSecurityException {
        return Arrays.equals(passwordHash, hashPassword(password, salt,
                iterationCount, keySize));
    }

    public static byte[] nextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) throws Exception {
        UserInfo userinfo = new UserInfo();
        String salt = userinfo.getSalt()+"232";
        String realPassword = userinfo.getPassword()+"123456";
        //用户输入的password
        String inputPassword="123456";
        //返回用户输入密码加密后的密文
        String encryptPassword = PBKDF2Util.encryptPassword(salt, inputPassword);
        if (encryptPassword.equals(realPassword)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
