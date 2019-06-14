package com.springboot.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lixing
 */
public class LambdaMap {

    private Map<String, Object> map = new HashMap<>();

    @Before
    public void initData() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", 4);
        map.put("key5", 5);
        map.put("key5", 'h');
    }


    /**
     * 遍历Map的方式一
     * 通过Map.keySet遍历key和value
     */
    @Test
    public void testErgodicWayOne() {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (String key : map.keySet()) {
            System.out.println("map.get(" + key + ") = " + map.get(key));
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.keySet().forEach(key -> System.out.println("map.get(" + key + ") = " + map.get(key)));
    }

    /**
     * 遍历Map第二种
     * 通过Map.entrySet使用Iterator遍历key和value
     */
    @Test
    public void testErgodicWayTwo() {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.entrySet().iterator().forEachRemaining(item -> System.out.println("key:value=" + item.getKey() + ":" + item.getValue()));
    }

    /**
     * 遍历Map第三种
     * 通过Map.entrySet遍历key和value，在大容量时推荐使用
     */
    @Test
    public void testErgodicWayThree() {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.entrySet().forEach(entry -> System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue()));
    }

    /**
     * 遍历Map第四种
     * 通过Map.values()遍历所有的value，但不能遍历key
     */
    @Test
    public void testErgodicWayFour() {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (Object value : map.values()) {
            System.out.println("map.value = " + value);
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.values().forEach(System.out::println); // 等价于map.values().forEach(value -> System.out.println(value));
    }

    /**
     * 遍历Map第五种
     * 通过k,v遍历，Java8独有的
     */
    @Test
    public void testErgodicWayFive() {
        System.out.println("---------------------Only JAVA8 ------------------------------");
        map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
    }

    @Test
    public void getAP12() {

        String url = "https://oa.bytedance.net/wfc_rule/rule/listEmployeesByRule";
        Map<String, Object> map = new HashMap<>();
        String appid = "wfc-rule";
        map.put("app_id", appid);
        String tims = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        map.put("timestamp", tims);
        String ruleCode = "AP1Rule";
        String area = "0";
        String depId = "288980000064975800";
        String patCode = "MDAP00000021";

        String param = "{\"rule_code\":\"" + ruleCode + "\",\"rule_data\":[{\"area\":\"" + area + "\",\"department_id\":\"" + depId + "\",\"payment_code\":\"" + patCode + "\"}]}";
        map.put("biz_params", param);
        String sign = SecureUtil.md5("12345678" + tims + param);
        map.put("sign", sign);
        System.out.println("入参："+ JSON.toJSONString(map));
        String post = HttpUtil.post(url, JSON.toJSONString(map));
        System.out.println("结果:"+JSON.toJSONString(JSON.parseObject(post), true));
    }

}
