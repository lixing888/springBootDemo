package com.springboot.demo.util;


import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.demo.vo.MyBean;
import com.springboot.demo.vo.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixing
 */
public class jsonStrToJava {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 统一日期格式yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    /**
     * Object转json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse object to String error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Object转json字符串并格式化美化
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse object to String error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转object
     * @param str json字符串
     * @param clazz 被转对象class
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isObjectNotEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T) str :objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转object
     * @param str json字符串
     * @param typeReference 被转对象引用类型
     * @param <T>
     * @return
     */
//    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
//        if (StringUtils.isObjectNotEmpty(str) || typeReference == null){
//            return null;
//        }
//        try {
//            return (T)(typeReference.getType().equals(String.class)? str :objectMapper.readValue(str,typeReference));
//        } catch (IOException e) {
//            System.out.println("Parse String to Object error");
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * string转object 用于转为集合对象
     * @param str json字符串
     * @param collectionClass 被转集合class
     * @param elementClasses 被转集合中对象类型class
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }

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
        String objectStr = "{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"},{\"name\":\"LIXING\",\"age\":\"28\",\"address\":\"北京市昌平区\"}]";
        //1、使用JSONObject
        JSONObject jsonObject = JSONObject.fromObject(objectStr);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);

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

    }
}



