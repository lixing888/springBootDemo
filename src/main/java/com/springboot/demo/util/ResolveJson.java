package com.springboot.demo.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResolveJson {

    public static void main(String[] args) {
        String json = "{\"data\":{\"items\":[{\"itemstring\":\"手机\",\"itemcoord\":{\"x\":0,\"y\":100,\"width\":40,\"height\":20},}],\"session_id\":\"9012321\",},\"code\":0,\"message\":\"OK\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray items = jsonObject.getJSONObject("data").getJSONArray("items");
        JSONObject row = null;
        for (int i = 0; i < items.size(); i++) {
            row = items.getJSONObject(i);
            System.out.println("itemstring ：" + row.get("itemstring"));
            JSONObject itemcoord = row.getJSONObject("itemcoord");
            System.out.println("x：" + itemcoord.get("x"));
            System.out.println("y：" + itemcoord.get("y"));
            System.out.println("width：" + itemcoord.get("width"));
            System.out.println("height：" + itemcoord.get("height"));
        }
        System.out.println("session_id：" + data.get("session_id"));
        System.out.println("code：" + jsonObject.get("code"));
        System.out.println("code：" + jsonObject.get("message"));
    }

}
