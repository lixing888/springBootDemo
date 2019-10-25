package com.springboot.demo;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.demo.entity.TestPerson;

import java.util.ArrayList;
import java.util.List;

public class TestFastJson {
    public static void main(String[] args) {
        TestPerson json = new TestPerson(19, "李明");
        List<TestPerson> list = new ArrayList<TestPerson>();
        list.add(json);
        list.add(new TestPerson(12, "张三"));
        //将集合或者对象序例化成JSON
        //   System.out.println(JSON.toJSON(json));//{"name":"李明","age":19}
        //    System.out.println( JSON.toJSON(list) );//[{"name":"李明","age":19},{"name":"张三","age":12}]
        //Json串反序列化成对象
        TestPerson person = JSON.parseObject("{\"name\":\"李明\",\"age\":19}", TestPerson.class);
        //    System.out.printf("name:%s,age:%d\n",person.getName(),person.getAge());//name:李明,age:19

        String str = "[{\"name\":\"李明\",\"age\":19},{\"name\":\"张三\",\"age\":12}]";
        //数组对象反序列化成集合
        List<TestPerson> listPerson = JSON.parseArray(str, TestPerson.class);

        for (TestPerson item : listPerson) {
            //         System.out.println( item.getName() );//李明 张三
            //          System.out.println( item.getAge());  //19    12
        }

        //没有对象直接解析JSON对象
        JSONObject jobj = JSON.parseObject("{\"name\":\"李明\",\"age\":19}");
        //  System.out.printf("name:%s,age:%d\n",jobj.getString("name"),jobj.getBigInteger("age"));
        //name:李明,age:19

        //没有对象直接解析JSON数组
        JSONArray jarr = JSON.parseArray("[{'name':'李明','age':18},{'name':'小四','age':21}]");
        //和下面使用  转义字符  效果是一致的
        /*   JSONArray jarr = JSON.parseArray("[{\"name\":\"李明\",\"age\":19},{\"name\":\"张三\",\"age\":12}]");*/
        System.out.println(jarr);//[{"name":"李明","age":18},{"name":"小四","age":21}]
        for (int i = 0, len = jarr.size(); i < len; i++) {
            JSONObject temp = jarr.getJSONObject(i);
            System.out.println(temp.getString("name") + "," + temp.getBigInteger("age"));
            /* System.out.printf("name:%s,age:%d\n", temp.getString("name"), temp.getBigInteger("age"));*/
            //name:李明,age:19    name:张三,age:12
        }

        /*{"name":"李明","age":19}
        {"name":"张三","age":12}*/
        for (Object obj : jarr) {
            System.out.println(obj.toString());
            System.out.println("md5加密：" + SecureUtil.md5(obj.toString()));
        }

    }
}
