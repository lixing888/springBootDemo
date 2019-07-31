package com.springboot.demo.config;

import com.springboot.demo.interceptor.UserSecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author lixing
 */
@Profile({"pre", "test"})
public class MySpringMvcConfig implements WebMvcConfigurer {

    @Value("${wfc-rule.run.environment}")
    protected String runEnvironment;

    public static void main(String[] args) {
        SpringApplication.run(MySpringMvcConfig.class, args);
    }

    /**
     * 配置拦截器
     *
     * @param registry
     * @author lixing
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> interceptors = Arrays.asList("/user/getUserById", "/jcUserNewController/**", "/test/**");
        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns(interceptors);
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



