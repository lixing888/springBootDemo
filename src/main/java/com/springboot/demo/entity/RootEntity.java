package com.springboot.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RootEntity {
    //私有化属性
    private String errorCode;
    private String errorMsg;
    //类似于定义json中的数组
    private List<UserEntity> data;
}
