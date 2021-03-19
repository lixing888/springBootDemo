package com.springboot.demo.util;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.Security;

/**
 * @author lixing
 */
public class SM3Utils {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(File contractFile) {
        String resultHexString = "";
        byte[] srcData = ByteFileUtils.File2byte(contractFile);
        byte[] resultHash = hash(srcData);
        resultHexString = ByteFileUtils.byte2HexStr(resultHash);
        return resultHexString;
    }

    private static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    public static void main(String[] args) {
        String result = "";
        File file = new File("D:\\天津养老保险转出凭证.pdf");
        result = SM3Utils.encrypt(file);
        System.out.println("SM3加密："+result);
    }

}
