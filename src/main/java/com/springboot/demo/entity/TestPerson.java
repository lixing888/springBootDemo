package com.springboot.demo.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class TestPerson {

    private Integer age;
    private String name;

    public TestPerson(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
}
