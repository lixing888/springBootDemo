package com.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixing
 * http://127.0.0.1:8888/provider/hello
 */
@RestController
public class DemoController {

    @Value("${server.port}")
    private Integer port;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/provider/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello from " + applicationName + ", service port=" + port);
    }
}
