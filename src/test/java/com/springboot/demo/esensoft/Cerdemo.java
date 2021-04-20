package com.springboot.demo.esensoft;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Cerdemo {

    public static void main(String[] args) throws Exception{

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(new FileInputStream("D:\\work\\zxl\\data\\窦.cer"));
        PublicKey publicKey = cert.getPublicKey();
        BASE64Encoder base64Encoder=new BASE64Encoder();
        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");
    }
}