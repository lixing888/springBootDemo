package com.springboot.demo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student {
    //姓名
    @NotBlank
    private String name;
    //年龄
    private String age;
    //性别
    private Integer sex;
    //住址
    private String address;

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", address="
                + address + "]";
    }


}