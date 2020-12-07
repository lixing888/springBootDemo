package com.springboot.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @ClassName: JwtHelper
 * @Description: token工具类
 * @author: yuanxinqi
 * 修改：chengxi
 * @date: 2018/8/17 9:59
 * @version: V 2.0.0
 * @since: (jdk_1.8)
 */
public class JWTUtil {

    /**
     * 解析token，获取其中的参数
     *
     * @param token 输入要解析的token
     * @param key   输入解析token的秘钥
     * @return
     */
    public static Claims parseToken(String token, String key) {
        if ("".equals(token)) {
            return null;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 生成token
     *
     * @param userId        token载荷中的信息
     * @param userName      token载荷中的信息
     * @param key           加密生成token的秘钥
     * @param expireMinutes token的生命周期
     * @return
     */
    public static String createToken(Integer userId, String userName, String key, int expireMinutes) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
//                .setHeaderParam("type", "JWT")
//                .setSubject(userId.toString())
                // 设置载荷信息，这个是设置的其中之一分参数，如果还有其他参数还可以链式调用的累加。如：.claim("参数名", 参数值)
                .claim("userId", userId)
                .claim("userName", userName)
                // 设置超时时间
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 目前加密的盐只能使用英文
        String token = JWTUtil.createToken(1, "小熙", "admin", 100);

        System.out.println("加密之后的值：" + token);

        Claims parseToken = JWTUtil.parseToken(token, "admin");

        System.out.println("解密后的值：" + parseToken);
    }

}


//需要导入的坐标
//<properties>
//<jjwt.version>0.7.0</jjwt.version>
//<joda-time.version>2.9.6</joda-time.version>
//</properties>
//
//<dependencies>
//<dependency>
//<groupId>io.jsonwebtoken</groupId>
//<artifactId>jjwt</artifactId>
//<version>${jjwt.version}</version>
//</dependency>
//<!-- https://mvnrepository.com/artifact/joda-time/joda-time -->
//<dependency>
//<groupId>joda-time</groupId>
//<artifactId>joda-time</artifactId>
//<version>${joda-time.version}</version>
//</dependency>
//</dependencies>
