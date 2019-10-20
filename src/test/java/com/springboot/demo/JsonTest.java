package com.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.demo.entity.RootEntity;
import com.springboot.demo.entity.UserEntity;
import com.springboot.demo.util.JsonUtils;
import com.springboot.demo.vo.UserJson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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


        UserJson user = new UserJson("李兴", "8923914");
        try {
            String json1 = JsonUtils.toUnderlineJSONString(user);
            System.out.println(json1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String json2 = "{\"user_name\":\"张三\",\"order_no\":\"1111111\"}";
        try {
            UserJson user1 = JsonUtils.toSnakeObject(json2, UserJson.class);
            System.out.println(JSONObject.toJSONString(user1));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
