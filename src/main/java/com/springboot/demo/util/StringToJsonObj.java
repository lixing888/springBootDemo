package com.springboot.demo.util;

import com.alibaba.fastjson.JSONObject;

public class StringToJsonObj {
    public static void main(String[] args) {

        String params="";
        //String转JsonObject对象
        JSONObject jSONObject=JSONObject.parseObject(params);


    }
}
