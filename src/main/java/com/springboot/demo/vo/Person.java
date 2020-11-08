package com.springboot.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: springBootDemo
 * @description: 用户person实体类
 * @author: lixing
 * @create: 2020-11-08 17:21
 **/
@Data
public class Person {
    @ApiModelProperty(value = "id")
    private int id;
    @ApiModelProperty(value = "姓名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "等级")
    private String perms;
}
