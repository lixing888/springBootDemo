package com.springboot.demo.util.sm4;

import com.springboot.demo.util.CheckUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SM4Utils {

    private final String iv = "";
    public String secretKey = "";
    public boolean hexString = false;

    public SM4Utils() {
    }

    public String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                //16进制字符串转换为byte[]
                keyBytes = Utils.hexStringToBytes(secretKey);
            } else {
                //字符串转化为一个byte[]
                keyBytes = secretKey.getBytes();
            }
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                CheckUtil.replaceSpecialStr(cipherText);
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Utils.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}