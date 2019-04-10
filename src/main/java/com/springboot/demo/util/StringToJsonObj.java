package com.springboot.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.springboot.demo.vo.Student;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringToJsonObj {
    public static void main(String[] args) {

        String params="";
        //String转JsonObject对象
        JSONObject jSONObject=JSONObject.parseObject(params);
        String arrayStr="[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"},{\"name\":\"LIXING\",\"age\":\"28\",\"address\":\"北京市昌平区\"}]";
        //String转list对象
        List<Student> studentList = JSONObject.parseArray(arrayStr, Student.class);
        //jdk8 List转Map
        Map<String,Student> employeeInfoMap = studentList.stream().collect(Collectors.toMap(Student::getAge, Function.identity()));
        for(int i=0;i<studentList.size();i++){
            System.out.println("获取到的姓名："+studentList.get(i).getName()+"========:根据Key取值:"+employeeInfoMap.get("24").getName());
        }


    }
}
