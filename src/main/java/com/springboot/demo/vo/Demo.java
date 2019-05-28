package com.springboot.demo.vo;

import lombok.Data;

/**
 * @author lixing
 */
@Data
public class Demo {

    private String name;
    private  Integer sex;
    private  String age;

    public Demo(String age,Integer sex) {
        this.age =age;
        this.sex =sex;
    }
}
