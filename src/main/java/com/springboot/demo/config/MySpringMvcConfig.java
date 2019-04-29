package com.springboot.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile({"pre", "test"})
public class MySpringMvcConfig implements WebMvcConfigurer {

    @Value("${wfc-rule.run.environment}")
    protected String runEnvironment;

}
