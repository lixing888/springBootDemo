package com.springboot.demo.aop;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.entity.Person;
import com.springboot.demo.enumbean.ResultState;
import com.springboot.demo.service.AmsService;
import com.springboot.demo.util.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class IsLeaderAspect {

    @Autowired
    private AmsService amsService;

    @Around("@annotation(com.springboot.demo.annotation.IsLeaderAuth)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("进入isLeader拦截器");
        //拿到当前的登录人信息
        Person user = new Person();
        //如果是leader，放行
        if (null != user && amsService.isLeader(user.getName())) {
            return joinPoint.proceed();
        }
        BaseResult result = new BaseResult();
        result.setCode("400000");
        result.setState(ResultState.FAIL);
        result.setMessage("无权限访问");
        log.info("无权限访问{}", JSON.toJSONString(result));
        return result;
    }
}
