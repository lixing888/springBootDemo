package com.springboot.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class RootEntity {
    private String errorCode;//私有化属性
    private String errorMsg;
    private List<UserEntity> data;//类似于定义json中的数组
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public List<UserEntity> getData() {
        return data;
    }
    public void setData(List<UserEntity> data) {
        this.data = data;
    }

}
