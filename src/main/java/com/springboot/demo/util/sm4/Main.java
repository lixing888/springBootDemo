package com.springboot.demo.util.sm4;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Scanner;

@Slf4j
public class Main {

    //删除文件
    public static void deletet(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static String du(String filePath) {
        // TODO Auto-generated method stub
        String fileBase64 = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            fileBase64 = new String(IOUtils.toByteArray(fileInputStream));
            fileInputStream.close();
        } catch (Exception e) {
            log.error("读取证书失败" + e);
        }
        return fileBase64;
    }

    public static void xie(String filePath, String fileBase64) {
        // TODO Auto-generated method stub
        File file = new File(filePath);

        try {
            //创建文件字节输出流对象，准备向d.txt文件中写出数据,true表示在原有的基础上增加内容
            FileOutputStream fout = new FileOutputStream(file, true);
            Scanner sc = new Scanner(System.in);
            String msg = fileBase64;
            /******************(方法一)按字节数组写入**********************/
            //byte[] bytes = msg.getBytes();//msg.getBytes()将字符串转为字节数组

            //fout.write(bytes);//使用字节数组输出到文件
            /******************(方法一)逐字节写入**********************/
            byte[] bytes = msg.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                fout.write(bytes[i]);//逐字节写文件
            }
            fout.flush();//强制刷新输出流
            fout.close();//关闭输出流
            log.info("写入完成！");
        } catch (Exception e) {
            log.error("写入证书失败" + e);
        }
    }

    public static String jiami(String fileBase64, String random) {
        SM4Utils sm4 = new SM4Utils();
        //加密密钥，由双方协定产生
        sm4.secretKey = random;
        sm4.hexString = false;
        String encData = sm4.encryptData_ECB(fileBase64);
        return encData;
    }

    public static String jiemi(String fileBase64, String random) {
        SM4Utils sm4 = new SM4Utils();
        //加密密钥，由双方协定产生
        sm4.secretKey = random;
        sm4.hexString = false;
        String encData = sm4.decryptData_ECB(fileBase64);
        return encData;
    }

    @SuppressWarnings({"unused", "restriction"})


    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //字符串转换为ASCII码
    public static int[] StringToAsc(String s) {
        char[] chars = s.toCharArray();
        int[] ascii = new int[s.length()];
        for (int i = 0; i < chars.length; i++) {
            ascii[i] = chars[i];
        }
        return ascii;
    }

    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) {//图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // 生成随机数
        int rondom = 16;
        String randomString = Main.getRandomString(rondom);
        System.out.println("生成" + rondom + "位随机数：" + randomString);

        //密码
        String password = "123456";
//		String password = "123";
        System.out.println("加密前的密码:" + password);

        //加密后的密文
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password2 = encoder.encode(password);
        String password3 = "$2a$10$.hZRd4YGFHVlMRRSrIg2ou7WQr2gt4oA/NPBcrjM/KTgiXiFEN4g.";//123456
//		String password3= "$2a$10$ttZd.w89KMw5OiKxxDZ/LuMiEWZ8V3wk.p6mVa14xCHte0sLy1pNe";//123

        System.out.println("BCryptPasswordEncoder加密后的密码:" + password2);
        System.out.println("密码是否匹配：" + encoder.matches(password, password2));
        System.out.println("密码是否匹配：" + encoder.matches(password, password3));
    }

}
