package com.springboot.demo.util;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.xml.bind.Binder;

/**
 * @author lixing
 * 获取当前环境参数  exp: dev,prd,test
 */
//@Component
//public class ProfileUtil implements ApplicationContextAware {
//    @Autowired
//    private static ApplicationContext context;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException {
//        context = SpringApplication.run(Application.class);
//    }
//
//    /**
//     * 获取当前环境参数  exp: dev,prd,test
//     */
//    public static String getActiveProfile() {
//        String[] profiles = context.getEnvironment().getActiveProfiles();
//        if (!ArrayUtils.isEmpty(profiles)) {
//            return profiles[0];
//        }
//        return "";
//    }
//
//    public static void main(String[] args) {
//
//        System.out.println("获取环境：" + getActiveProfile());
//
//    }
//}
