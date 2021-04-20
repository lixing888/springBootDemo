package com.springboot.demo.esensoft;

import com.esensoft.service.impl.OfficialServiceImpl;
import com.esensoft.utils.CheckUtil;
import com.esensoft.utils.base64Util.Base64;
import com.esensoft.utils.base64Util.Base64Utlis;
import com.esensoft.utils.ecc.ECCUtil;
import com.esensoft.utils.sm4.Main;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println(Main.jiami("-----BEGIN PRIVATE KEY-----\n" +
                "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgum0sriTpzInnnTjn7AIgWMe/YKDT\n" +
                "/0W4ryPufe8OWaCgCgYIKoZIzj0DAQehRANCAARuWOzSkEY+SjISrgG9vQN6ZibZvZAqFQ3nO2rQ\n" +
                "Fkk3t5cO5Q5VENDt0vANjVMrxPITmJkZcwoYU5o1Ze0kSeYK\n" +
                "-----END PRIVATE KEY-----","1234567890123456"));

        String randomString = Main.getRandomString(16);
       String BASE64ZIP = Main.jiami("123", randomString);
        X509Certificate cert = null;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        byte[] bytes1 = Main.du("D:\\as\\Admin.pem").getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes1);
        cert = (X509Certificate) cf.generateCertificate(stream);
        PublicKey publicKey = cert.getPublicKey();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        ECPublicKey ecPublicKey = ECCUtil.string2PublicKey(publicKeyString);
            // 使用银行公钥加密sm4的密钥
            byte[] publicEncrypt = ECCUtil.publicEncrypt("1".getBytes(), ecPublicKey);
            String sign = Base64.encode(publicEncrypt);
            System.out.println(sign);
          /*  try{
       System.out.println( jiemi(sign,BASE64ZIP,randomString,"-----BEGIN PRIVATE KEY-----\n" +
                "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgum0sriTpzInnnTjn7AIgWMe/YKDT\n" +
                "/0W4ryPufe8OWaCgCgYIKoZIzj0DAQehRANCAARuWOzSkEY+SjISrgG9vQN6ZibZvZAqFQ3nO2rQ\n" +
                "Fkk3t5cO5Q5VENDt0vANjVMrxPITmJkZcwoYU5o1Ze0kSeYK\n" +
                "-----END PRIVATE KEY-----"));
            }catch (Exception e){
                e.printStackTrace();
            }*/
    }
    private static String jiemi(String yhSign, String base64ZIPString, String password, String privateKeyStr) throws
            Exception {
        //解密部分
        //通过判断sender是不是bjtax来判断解密银行签名还是税务签名 例：sender是bjtax 那么就回去银行签名做以下操作
        //BASE64转二进制
        //转换16位
        String checkout = CheckUtil.checkout(password);
        //解密
      //
        privateKeyStr = CheckUtil.replaceSpecialStr(privateKeyStr.substring(
                "-----BEGIN PRIVATE KEY-----".length(),
                privateKeyStr.length() - ("-----END PRIVATE KEY-----".length() + 1)));
        ECPrivateKey privateKey1 = ECCUtil.string2PrivateKey(privateKeyStr);
        byte[] base64String2ByteFun = Base64Utlis.base64String2ByteFun(yhSign);
        //二进制密文通过ecc私钥解密 非对称解密
        byte[] privateDecrypt = ECCUtil.privateDecrypt(base64String2ByteFun, privateKey1);
        //对称解密
        base64ZIPString = Main.jiemi(base64ZIPString, new String(privateDecrypt));
        return base64ZIPString;
    }
}
