package com.springboot.demo.vo;

import lombok.Data;

@Data
public class UserResponse {
    private String message;
    private String code;
    private Object data;
    //默认成功
    public UserResponse(){
        this.code="200";
        this.message="操作成功";

    }

}
