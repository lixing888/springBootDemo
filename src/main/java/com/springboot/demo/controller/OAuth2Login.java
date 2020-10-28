package com.springboot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.util.HttpClientUtil;
import com.springboot.demo.vo.JwtUtil;
import com.springboot.demo.vo.UserInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springBootDemo
 * @description: Oauth2的使用（第三方授权登录）
 * @author: lixing
 * @create: 2020-10-26 10:28
 **/
@RestController
@RequestMapping("/OAuth2Login")
@Api(tags = "/OAuth2单点登录")
public class OAuth2Login {

    @RequestMapping("vlogin")
    public String vlogin(String code, HttpServletRequest request) {

        // 换取access_token
        String access_token_url = "https://api.weibo.com/oauth2/access_token?client_id= 25920146&client_secret=dc8de1392f642a01259b136ff8e970b9&grant_type=authorization_code&redirect_uri=http://passport.gmall.com:8090/vlogin&code=e211655dd6c78a66fcfcfdff552424f6";

        Map<String,String> map = new HashMap<String,String>();
        map.put("client_id","25920146");
        map.put("client_secret","dc8de1392f642a01259b136ff8e970b9");
        map.put("grant_type","authorization_code");
        map.put("redirect_uri","http://passport.gmall.com:8090/vlogin");
        map.put("code",code);
        String access_json = HttpClientUtil.doPost("https://api.weibo.com/oauth2/access_token", map);
        System.out.println(access_json);

        Map<String,String> map_access_json =  new HashMap<String,String>();
        Map access_map = JSON.parseObject(access_json, map_access_json.getClass());


        // 获得第三方用户数据
        String access_token = (String)access_map.get("access_token");
        String uid = (String)access_map.get("uid");// uid uidStr
        UserInfo umsMember = new UserInfo();
//        umsMember = userService.isUidExists(uid);
//
//        if(umsMember==null){
//
//            String show_url = "https://api.weibo.com/2/users/show.json?access_token="+access_token+"&uid="+uid;
//
//            String user_json = HttpClientUtil.doGet(show_url);
//            Map<String,String> map_user_json =  new HashMap<String,String>();
//            Map user_map = JSON.parseObject(user_json, map_user_json.getClass());
//            System.out.println(user_map);
//
//            // 存入数据库
//            umsMember.setNickname((String)user_map.get("screen_name"));
//            umsMember.setUsername((String)user_map.get("name"));
//            umsMember.setSourceType("2");
//            umsMember.setSourceUid((String)user_map.get("idstr"));
//            umsMember.setCreateTime(new Date());
//            umsMember.setAccessToken(access_token);
//            umsMember.setAccessCode(code);
//            umsMember = userService.addUser(umsMember);
//        }


        // 根据用户信息生成token
        String key = "athouruisso";
        String ip = request.getRemoteAddr();
        Map<String,Object> token_map = new HashMap<>();
        token_map.put("nickname",umsMember.getNickname());
        token_map.put("memberId",umsMember.getId());
        String token = JwtUtil.createJwt(key, token_map, ip);

        // 将生成的token和登录用户信息保存在缓存中一分
        //userService.addUserCache(token,umsMember);
        return "redirect:http://192.168.5.3：8090/index?newToken="+token;
    }
}
