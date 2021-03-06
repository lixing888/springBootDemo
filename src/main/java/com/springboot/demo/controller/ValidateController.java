package com.springboot.demo.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.demo.util.RandomValidateCodeUtil;
import com.springboot.demo.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.code.kaptcha.impl.DefaultKaptcha;

/**
 * Copyright (C), 2018-2021
 * FileName: ValidateController
 * Author:   lixing
 * Date:     2021/5/8 16:24
 * Description: 图形验证码控制层
 */
@RestController
@RequestMapping("/validate")
@Api(tags = "/图形验证码")
public class ValidateController {
    @Resource
    private DefaultKaptcha captchaProducer;
    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";

    /**
     * 登录验证码图片
     * http://127.0.0.1:8888/validate/loginValidateCode
     */
    @GetMapping(value = {"/loginValidateCode"})
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RandomValidateCodeUtil.validateCode(request, response, captchaProducer, LOGIN_VALIDATE_CODE);
    }

    /**
     * 检查验证码是否正确
     * http://127.0.0.1:8888/validate/checkLoginValidateCode?validateCode=2100
     */
    @PostMapping("/checkLoginValidateCode")
    @ResponseBody
    public Result checkLoginValidateCode(HttpServletRequest request, @RequestParam("validateCode") String validateCode) {
        String loginValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        //HashMap<String, Object> map = new HashMap<String, Object>();
        Result result = new Result();
        if (loginValidateCode == null) {
            //map.put("status", null);//验证码过期
            result.setMsg("验证码过期");
            result.setData(null);
        } else if (loginValidateCode.equals(validateCode)) {
            //map.put("status", true);//验证码正确
            result.setMsg("验证码正确");
            result.setData(true);
        } else if (!loginValidateCode.equals(validateCode)) {
            //map.put("status", false);//验证码不正确
            result.setMsg("验证码不正确");
            result.setData(false);
        }
        //map.put("code", 200);
        result.setStatus(200);
        return result;
    }
}
