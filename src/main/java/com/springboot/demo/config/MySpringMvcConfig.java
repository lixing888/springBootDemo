package com.springboot.demo.config;

import com.springboot.demo.interceptor.UserSecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lixing
 */
@Configuration
@Profile({"pre", "test"})
public class MySpringMvcConfig implements WebMvcConfigurer {

    @Value("${wfc-rule.run.environment}")
    protected String runEnvironment;

    public static void main(String[] args) {
        SpringApplication.run(MySpringMvcConfig.class, args);
    }
    /**
     * 配置拦截器
     * @author lixing
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/user/getUserById","/jcUserNewController/**","/test/**");
    }
   }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        //registry.addInterceptor(new SignAppidInterceptor(redissonClient, appKey, runEnvironment, wfcApiAppManager)).addPathPatterns("/user/getUserById","/jcUserNewController/**","/test/**");
//
//        //registry.addInterceptor(userInterceptor).addPathPatterns("/**");
//        //registry.addInterceptor(logMdcInterceptor).addPathPatterns("/**");
//    }



