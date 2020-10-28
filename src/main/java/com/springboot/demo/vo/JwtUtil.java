package com.springboot.demo.vo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @program: springBootDemo
 * @description: 简单版jwt工具类
 * @author: lixing
 * @create: 2020-10-26 10:43
 **/

public class JwtUtil {

    public static final int CALENDAR_FIELD = Calendar.MINUTE;
    //签名有效时间
    public static final Long timeOut = Long.valueOf(60 * 24);
    //签名私钥
    private static final String SECRET_KEY = "6A50A18D70FA63636645C65459F1D78A";

    /**
     * 设置认证token
     * id:用户id
     * subject:用户名
     */
    public static String createJwt(String id, Map<String, Object> map, String name) {
        //1.设置失效时间
        long nowTime = System.currentTimeMillis();//当前毫秒
        long expirationTime = nowTime + timeOut;
        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder();
        //3.根据map设置claims
        //当设置的是整个map时，就需放在最前面，下面的setId等参数才会有效，否则会把前面set的值置空
        jwtBuilder.setClaims(map);
        jwtBuilder.setId(id);
        jwtBuilder.setSubject(name);
        jwtBuilder.setIssuedAt(new Date());//设置当前时间
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);//设置加密方式
        jwtBuilder.setExpiration(new Date(expirationTime));//设置过期时间
        //4.创建token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims;
    }
}