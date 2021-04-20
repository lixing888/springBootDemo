package com.springboot.demo.esensoft;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.esensoft.utils.CheckUtil;
import com.esensoft.utils.ZipUtil;
import com.esensoft.utils.base64Util.Base64;
import com.esensoft.utils.ecc.ECCUtil;
import com.esensoft.utils.sm4.Main;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

import java.io.File;

public class jiemiTest {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader("D:/qklFile/华宇.txt");
        //读取文件字节码
        String base64PDFString = fileReader.readString();
        String swSkStr = "IA1Fx0EkjFHKltXErPTjG10UEdT/tW7CMP+7K4MZLqRklnaVxbL0YEyATUpJ8W/gZDS/PT5eferlJdaO2JmvAux" +
                "WL22MMX5+v+ASWV5u+Y5hk65b9oBQqxHdYUTLAdH9rf6VZv7v9jdLA5t3pAcvK5AWV8ndGp7i79vR0lDqq73/zHCmtNqNlNG" +
                "MrNONafkGvlpDXpUf+uHWfu+CdvoIF4fNH8tbmyxN5HehNBh6p6eETu2sZFPB23jaL9RVqNE+AIZqfzjs4GeLsaPuuNC5Ysv" +
                "QqJQ5fYjbap58R6u7vD4LD8rQIpmq9DJgWG+44QqielrrefHdTKIo4GWB7fSz0A/zhqYmWqhO5dfeHkeLrcI=";
        String yhSign = "BK7j0dPWndMb7qDjtk6zG7kLF6y+8YlYHUOSO7TfKKQZkcYu5y67cytTJhnP/2MG5fCDUvoJlrLqq/H6m676otdv5pBDfnNmXMohBxZK9eSBcx3L8fxCMj+juEWzr8tGbEN6+2M=";
        try {
            base64PDFString = jiemi(yhSign, base64PDFString, swSkStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] decode = Base64.decode(base64PDFString);
        String decompress = ZipUtil.decompress(decode, "UTF-8");
        byte[] decode1 = Base64.decode(decompress);
        FileWriter fileWriter = new FileWriter("d:/qklFile/华宇税务1.xlsx");
        File write = fileWriter.write(decode1, 0, decode1.length, false);
        System.out.println(write);
    }

    private static String jiemi(String yhSign, String base64ZIPString ,String privateKeyStr) throws
            Exception {
        //解密部分
        //通过判断sender是不是bjtax来判断解密银行签名还是税务签名 例：sender是bjtax 那么就回去银行签名做以下操作
        //BASE64转二进制
        //解密
        privateKeyStr = Main.jiemi(privateKeyStr, "1234567890123456");
        privateKeyStr = CheckUtil.replaceSpecialStr(privateKeyStr.substring(
                "-----BEGIN PRIVATE KEY-----".length(),
                privateKeyStr.length() - ("-----END PRIVATE KEY-----".length() + 1)));
        ECPrivateKey privateKey1 = ECCUtil.string2PrivateKey(privateKeyStr);
        byte[] base64String2ByteFun = Base64.decode(yhSign);
        //二进制密文通过ecc私钥解密 非对称解密
        byte[] privateDecrypt = ECCUtil.privateDecrypt(base64String2ByteFun, privateKey1);
        //对称解密
        base64ZIPString = Main.jiemi(base64ZIPString, new String(privateDecrypt));
        return base64ZIPString;
    }
}
