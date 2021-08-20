package com.springboot.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * @author lixing
 * @version 1.0.0
 * @ClassName EncryptionUtil.java
 * @Description 加密工具类
 * @createTime 2021年08月20日 11:20:00
 */
public class EncryptionUtil {
    /**
     * 先生成一个10位的随机字符串(这个随意)
     */
    public static String dealPassword(String password) throws UnsupportedEncodingException {
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        System.out.println(salt + "---111111");
        try {

            // salt.getBytes("UTF-8"):字符串转成UTF-8的字节数组
            return dealPasswordWithSalt(password, salt.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过SHA-1和Base64处理之后，给字符串数据加密在返回字符串
     */
    public static String dealPasswordWithSalt(String password, byte[] salt)
            throws Exception, UnsupportedEncodingException {
        if (password == null) {
            throw new Exception("Parameter is null");
        }
        // 将两个数组合2为1
        byte[] msg = byteCat(password.getBytes("UTF-8"), salt);
        String dealedPassword = null;

        /*
         * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
         * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
         */
        MessageDigest md;
        try {

            // 返回实现指定摘要算法的 MessageDigest
            // 对象。MessageDigest是java自带加密工具类，通过SHA-1加密，也可以采用MD5
            md = MessageDigest.getInstance("SHA-1");

            // 使用指定的 byte 数组更新摘要。
            md.update(msg);

            // 通过执行诸如填充之类的最终操作完成哈希计算。digest 方法只能被调用一次。在调用 digest
            // 之后，MessageDigest 对象被重新设置成其初始状态。
            byte[] dig = md.digest();

            // 在合2为1
            byte[] passb = byteCat(dig, salt);

            // 最后通过BASE64算法转换二进 制数据为ASCII字符串格式。
            dealedPassword = new String(Base64.encodeBase64(passb));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dealedPassword;
    }

    /**
     * 这个方法的目的是将两个数组合成一个数组
     */
    private static byte[] byteCat(byte[] l, byte[] r) {
        byte[] b = new byte[l.length + r.length];
        System.arraycopy(l, 0, b, 0, l.length);
        System.arraycopy(r, 0, b, l.length, r.length);
        return b;
    }

    /**
     * 通过上面生成的字符串来进行解密，其最终目的就是获得上面随机生成的UUID
     * 这样只要你password一致，UUID一致，那dealPasswordWithSalt方法产生的加密字符串就会一致
     */
    public static byte[] getSalt(String dealedPassword) throws Exception {
        /*
         * 解码：这里获得的是上面passb数组,因为SHA-1是固定20个字节，所以从20位置开始截取，MD516个字节。
         */
        byte[] decoded = Base64.decodeBase64(dealedPassword);
        byte[][] bs = null;
        bs = byteSplit(decoded, 20);
        byte[] salt = bs[1];
        System.out.println(new String(salt) + "---222222");
        return salt;
    }

    /**
     * 将数组1分为2，其实就是获得上面uuid所产生的数组
     * 第一个数组是上面dig数组，第二个是salt数组
     */
    private static byte[][] byteSplit(byte[] src, int n) {
        byte[] l, r;
        if (src == null || src.length <= n) {
            l = src;
            r = new byte[0];
        } else {
            l = new byte[n];
            r = new byte[src.length - n];
            System.arraycopy(src, 0, l, 0, n);
            System.arraycopy(src, n, r, 0, r.length);
        }
        byte[][] lr = {l, r};
        return lr;
    }

    public static void main(String[] args) {
        try {
            //模拟获得用户注册的密码
            String password = "zhang123456";
            //通过加密获得的密码，存放到数据库
            String plusPassword = EncryptionUtil.dealPassword(password);
            System.out.println("加密后:" + plusPassword);
            //模拟用户登录
            String loginpassword = "zhang123456";
            String LessPassword = EncryptionUtil.dealPasswordWithSalt(loginpassword, EncryptionUtil.getSalt(plusPassword));
            System.out.println("再加密:" + LessPassword);
            System.out.println(plusPassword.equals(LessPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}