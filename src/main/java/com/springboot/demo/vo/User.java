package com.springboot.demo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户实体类
 *
 * @author suddev
 * @create 2018-02-26 上午11:03
 **/
@Data
public class User {
    private long id;
    private String username;
    private String birthday;
    private int age;
    private BigDecimal money;

    public User(long id,BigDecimal money) {
        this.id = id;
        this.money = money;
    }


}

