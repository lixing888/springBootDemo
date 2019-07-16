package com.springboot.demo;

import com.alibaba.fastjson.JSONObject;
import com.springboot.demo.entity.RootEntity;
import com.springboot.demo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    //通过实体类转换成json
    public static void BeanToJson(){

        RootEntity rootEntity = new RootEntity();
        rootEntity.setErrorCode("0");
        rootEntity.setErrorMsg("调用接口成功");

        List<UserEntity> data = new ArrayList<UserEntity>();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("userZwZ");
        userEntity.setPosition("student");

        data.add(userEntity);
        rootEntity.setData(data);

        JSONObject jsonObject = new JSONObject();
        //通过toJSONString( )将实体类转化成json对象
        System.out.println(jsonObject.toJSONString(rootEntity));
    }
    public static void main(String[] args) {
        BeanToJson();
    }

}
