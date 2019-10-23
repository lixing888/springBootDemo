package com.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.springboot.demo.entity.RootEntity;
import com.springboot.demo.entity.UserEntity;
import com.springboot.demo.util.JsonUtils;
import com.springboot.demo.vo.UserJson;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTest {
    //通过实体类转换成json
    public static void BeanToJson() {


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
        //string转json
        String str = "{\"result\":\"success\",\"message\":\"成功！\"}";
        JSONObject strTojson = JSONObject.parseObject(str);
        System.out.println("string转json后获取result值:" + strTojson.get("result"));

        JSONObject json = new JSONObject();
        json.put("aa", "11");
        json.put("bb", "22");
        json.put("cc", "33");
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        // {"aa":"11","bb":"22","cc":"33"}
        System.out.println(JSONObject.parseObject(jsonStr).get("aa"));
        // 11
        String o = "{'area':{'area':'linqing','pagetype':'home'},'pagetype':'home'}";
        System.out.println(((Map) JSONObject.parseObject(o).get("area")).get("area"));
        // linqing
        String text = JSON.toJSONString(o);
        Map<String, Object> userMap =
                JSON.parseObject(o, new TypeReference<Map<String, Object>>() {
                });
        System.out.println(((Map) userMap.get("area")).get("NotExsit"));
        // null
        System.out.println(JSON.toJSONString((Map) userMap.get("area")));
        // {"area":"linqing","pagetype":"home"}

        String jsonStr1 = "{\"203810\":{\"364062609684836353\":{\"amount\":1999.00,\"budgetItemCode\":\"MDBS00000006\",\"costDeptId\":\"288980000064976051\",\"currency\":\"CNY\"}},\"203811\":{\"364062924966473729\":{\"amount\":1000.00,\"budgetItemCode\":\"MDBS00000010\",\"costDeptId\":\"288980000064976048\",\"currency\":\"CNY\"}}}";

        JSONObject strTojson1 = JSONObject.parseObject(jsonStr1);

        strTojson1.get("amount");

        String costItems = "{\n" +
                "  \"203810\": {\n" +
                "    \"364062609684836353\": {\n" +
                "      \"amount\": 1999.00,\n" +
                "      \"budgetItemCode\": \"MDBS00000006\",\n" +
                "      \"costDeptId\": \"288980000064976051\",\n" +
                "      \"currency\": \"USD\"\n" +
                "    },\n" +
                "    \"364062609684836354\": {\n" +
                "      \"amount\": 100.00,\n" +
                "      \"budgetItemCode\": \"MDBS00000016\",\n" +
                "      \"costDeptId\": \"2889800000649760541\",\n" +
                "      \"currency\": \"CNY\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"203811\": {\n" +
                "    \"364062924966473729\": {\n" +
                "      \"amount\": 1000.00,\n" +
                "      \"budgetItemCode\": \"MDBS00000010\",\n" +
                "      \"costDeptId\": \"288980000064976048\",\n" +
                "      \"currency\": \"CNY\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String strMsg = "{\"costDeptItemList\":" + JSON.toJSONString(costItems) + "}";
        System.out.println("=====" + strMsg);
        //
        JSONObject jsonObject = JSON.parseObject(costItems);
        if (!jsonObject.isEmpty()) {
            Collection<Object> values = jsonObject.values();
            for (Object value : values) {
                JSONObject val = (JSONObject) value;
                Collection<Object> collections = val.values();
                for (Object collection : collections) {
                    JSONObject v = (JSONObject) collection;
                    //如果存在amount
                    BigDecimal amount = v.getBigDecimal("amount");
                    String budgetItemCode = v.getString("budgetItemCode");
                    String costDeptId = v.getString("costDeptId");
                    String currency = v.getString("currency");
                    if ("CNY".equals(currency)) {
                        continue;
                    }
                    //替换成要变更的数据
                    v.put("amount", new BigDecimal("100"));
                    v.put("currency", "CNY");
                    System.out.println(amount + "  " + budgetItemCode + "  " + costDeptId + "  " + currency);
                }
            }
            System.out.println("币种转换后的：" + jsonObject.toString());
        }

        /*
         * JSON的驼峰和下划线互转
         */
        UserJson user = new UserJson("李兴", "8923914");
        try {
            String json1 = JsonUtils.toUnderlineJSONString(user);
            System.out.println("对象转下划线：" + json1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String json2 = "{\"userName\":\"张三\",\"order_no\":\"1111111\"}";
        try {
            UserJson user1 = JsonUtils.toSnakeObject(json2, UserJson.class);
            System.out.println("下划线转驼峰：" + JSONObject.toJSONString(user1));
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> stringList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            stringList.add(String.valueOf(i));
        }
        stringList.stream().skip(2).collect(Collectors.toList());
        // 不改变原对象
        System.out.println(stringList);

        // Stream 使用接口
        /**
         * 1. filter
         * 返回Predicate定义过滤的元素
         */
        // 过滤偶数
        stringList = stringList.stream().filter(e -> Integer.valueOf(e) % 2 == 0).collect(Collectors.toList());
        System.out.println(stringList); // [0, 2, 4, 6, 8]

        /**
         * 2.map
         * 定义函数返回新的类型的流
         */
        List<Long> longList = stringList.stream().map(Long::valueOf).collect(Collectors.toList());
        System.out.println(longList);// [0, 2, 4, 6, 8]

        /**
         * 3.mapToInt
         * 转换成Int类型的流, 可以做一些运算操作，其他double与float类似
         */
        // 求和
        Integer count = stringList.stream().mapToInt(e -> Integer.valueOf(e)).sum();
        System.out.println(count);

        /**
         * 4.flapMap
         *  二维数组，每个数组调用Function
         */
        List<List<String>> stringListList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<String> list = Lists.newArrayList();
            stringListList.add(list);
            for (int j = 0; j < 10; j++) {
                list.add(String.valueOf(i * j));
            }
        }
        List<Integer> integerListList = stringListList.stream().flatMap(e -> e.stream().map(Integer::valueOf)).collect(Collectors.toList());
        System.out.println(integerListList);

        /**
         * 5.Distinct排除重复
         */
        List<Integer> distinctList = integerListList.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctList); // 去除重复的

        /**
         * sort按指定方式排序
         */
        distinctList.sort((e1, e2) -> e1 < e2 ? 1 : -1);
        System.out.println(distinctList); // 递减排列

        /**
         * peek 可以使用它去处理每个元素
         * 想比较map没返回值
         */
        Stream.of("a", "b", "c").peek(e -> System.out.println(e)).collect(Collectors.toList());

        /**
         * limit 只取前指定个数
         */
        List<Integer> limitList = Stream.of(1, 2, 3).limit(1).collect(Collectors.toList());
        System.out.println(limitList); // 2,3

        /**
         * skip 指定个数，返回
         */
        List<String> skipList = Stream.of("a", "b", "c").skip(1).collect(Collectors.toList());
        System.out.println(skipList); // b,c

        Set<String> list=new HashSet<>();
        list.add("");
        list.add("MDSA090388123");
        System.out.println(list+"==== 是否包含null对象："+list.contains(null)+"----是否包含空字符串："+list.contains(""));

        //1.fastjson  List转JSONArray
        List<T> list1 = new ArrayList<T>();
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(list1));

        //2.fastjson  JSONArray转List
        JSONArray array1 = new JSONArray();
        List<UserJson> list2 = JSONObject.parseArray(array1.toJSONString(), UserJson.class);
        for (UserJson userJson : list2) {
            System.out.println(userJson.getUserName());
        }
        //3.fastjson  字符串转List
        String str1 = "[{\n" +
                "    \"user_id\": 9839306,\n" +
                "    \"user_name\": \"师思\",\n" +
                "    \"id\": 736\n" +
                "}]";
        List<UserJson> list12 = JSONObject.parseArray(str1,UserJson.class);
        for (UserJson userJson : list12) {
            System.out.println(userJson.getUserName());
        }

    }

}
