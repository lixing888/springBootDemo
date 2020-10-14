package com.springboot.demo.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TestPerson {

    private int age;
    private String name;

    public TestPerson(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
