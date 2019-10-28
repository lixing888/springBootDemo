package com.springboot.demo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.springboot.demo.vo.MyBean;
import com.springboot.demo.vo.Student;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixing
 */
@Slf4j
public class jsonStrToJava {
    public static void main(String[] args) {

        Student student = new Student();
        student.setName("JSON");
        student.setAge("23");
        student.setAddress("北京市西城区");

        //1、使用JSONObject
        JSONObject json = JSONObject.fromObject(student);
        //2、使用JSONArray
        JSONArray array = JSONArray.fromObject(student);

        String strJson = json.toString();
        String strArray = array.toString();

        System.out.println("strJson:" + strJson);
        System.out.println("strArray:" + strArray);


        //定义两种不同格式的字符串============================
        String objectStr = "{\"name\":\"LIXING\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"},{\"name\":\"LIXING\",\"age\":\"28\",\"address\":\"北京市昌平区\"}]";
        //1、使用JSONObject
        JSONObject jsonObject = JSONObject.fromObject(objectStr);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);

        //=================
        ObjectMapper mapper = new ObjectMapper();
        //驼峰转换
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            Student student1 = mapper.readValue(objectStr, Student.class);
            System.out.println("学生姓名:" + student1.getName());
        } catch (IOException e) {
            log.info(e.toString());
        }
        //===========
        //2、使用JSONArray
        JSONArray jsonArray = JSONArray.fromObject(arrayStr);
        //获得jsonArray的第一个元素
        Object o = jsonArray.get(0);
        JSONObject jsonObject2 = JSONObject.fromObject(o);
        Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
        System.out.println("stu:" + stu);
        System.out.println("stu2:" + stu2);

        //map--》》json字符串
        Student stu1 = new Student();
        stu1.setName("JSON");
        stu1.setAge("23");
        stu1.setAddress("中国上海");
        Map<String, Student> map = new HashMap<String, Student>();
        map.put("first", stu1);

        //1、JSONObject
        JSONObject mapObject = JSONObject.fromObject(map);
        System.out.println("mapObject" + mapObject.toString());

        //2、JSONArray
        JSONArray mapArray = JSONArray.fromObject(map);
        System.out.println("mapArray:" + mapArray.toString());

        //json字符串--》》map
        String strObject = "{\"first\":{\"address\":\"中国上海\",\"age\":\"23\",\"name\":\"JSON\"}}";

        //JSONObject
        JSONObject jsonObject1 = JSONObject.fromObject(strObject);
        Map map1 = new HashMap();
        map1.put("first", Student.class);

        //使用了toBean方法，需要三个参数
        MyBean my = (MyBean) JSONObject.toBean(jsonObject1, MyBean.class, map);
        System.out.println(my.getFirst());

        //反射获取方式1
        Student t =new Student();
        Class c1=t.getClass();
        System.out.println("反射获取方式1:"+c1.getName());

        //反射获取方式1
        Class c2=Student.class;
        System.out.println("反射获取方式2:"+c2.getName());

        //这里需要注意，通过类的全路径名获取Class对象会抛出一个异常，如果根据类路径找不到这个类那么就会抛出这个异常。
        try {
            Class c3=Class.forName("com.springboot.demo.vo.Student");
            System.out.println("反射获取方式3:"+c3.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}



