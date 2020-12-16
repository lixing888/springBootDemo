package com.springboot.demo.entity;

/**
 * @program: springBootDemo
 * @description: 学生实体类
 * @author: lixing
 * @create: 2020-12-16 14:03
 **/

import lombok.Data;

@Data
public class Student {

    private int id;
    private String name;
    private int score;

    public Student(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
}
