package com.springboot.demo;

import io.swagger.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

/**
 * 我的第一个springboot程序
 * 其中 @RestController 等同于 （@Controller 与 @ResponseBody）
 *
 * @author lixing
 * 这里主要关注@SpringBootApplication注解，它包括三个注解：
 * @Configuration：表示将该类作用springboot配置文件类。
 * @EnableAutoConfiguration:表示程序启动时，自动加载springboot默认的配置。
 * @ComponentScan:表示程序启动是，自动扫描当前包及子包下所有类。
 */
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class}, scanBasePackages = "com.springboot")
@ComponentScan("com.springboot")
@MapperScan(basePackages = "com.springboot")//扫描范围
@Api(tags = "swagger2") //http://127.0.0.1:8080/swagger-ui.html
//@EnableCaching
//@Profile(value = "pre")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //http://localhost:8080/demo1
    @GetMapping("/demo1")
    @ApiOperation(value = "获取用户列表", notes = "获取所有用户列表", produces = "application/json")
    @ApiImplicitParam(value = "1", name = "参数", paramType = "query", dataType = "String", required = true)
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String demo1() {
        return "Hello World!";
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        // 目的是
        return args -> {
            System.out.println("来看看 SpringBoot 默认为我们提供的 Bean:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            Arrays.stream(beanNames).forEach(System.out::println);
        };
    }
}
