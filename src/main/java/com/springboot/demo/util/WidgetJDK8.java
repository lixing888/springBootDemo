package com.springboot.demo.util;


import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WidgetJDK8 {

    private final Color color;
    private final int weight;
    enum Color {RED, BLACK, BLUE}

    public WidgetJDK8(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Color getColor() {return color;}
    public int getWeight() {return weight;}

    public static void main(String[] args) {
        List<WidgetJDK8> widgets = new ArrayList<>();
        widgets.add(new WidgetJDK8(Color.RED, 7));
        widgets.add(new WidgetJDK8(Color.RED, 6));
        widgets.add(new WidgetJDK8(Color.BLACK, 3));
        widgets.add(new WidgetJDK8(Color.BLUE, 4));
        // stream() 获取当前的source, filter 和 mapToInt为intermediate操作, 进行数据筛选和转换,
        // 最后一个sum为terminal操作，对符合条件的全部widget做重量求和
        int sum = widgets.stream()
                .filter(w -> w.getColor() == Color.RED)
                .mapToInt(w -> w.getWeight())
                .sum();
        System.out.println(sum);// 13

        Map<Integer, String> HOSTING = new HashMap<>();
        HOSTING.put(1, "linode.com");
        HOSTING.put(2, "www.baidu.com");
        HOSTING.put(3, "digitalocean.com");
        HOSTING.put(4, "aws.amazon.com");


        //Map -> Stream -> Filter -> Map
        Map<Integer, String> collect = HOSTING.entrySet().stream()
                .filter(map -> map.getKey() == 2)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        System.out.println(collect);


        //String转JSON
        JSONObject json = JSONObject.fromObject("{\"data\": {\"pages\": [ {\"name\": \"李兴\"},{\"name\": \"李明\"}],\"total_count\": 2 },\"errcode\": 0}");
        System.out.println("String转JSON"+json);
        System.out.println("解析:"+json.get("data"));
        //JsonParser parse =new JsonParser();  //创建json解析器
//        ArrayList<Users> users = new ArrayList<Users>();
//        JSONArray jsonArray = JSONArray.fromObject(json);
//        for (int i = 0; i < jsonArray.size(); i++) {
//            Users user = new Users();
//            user.setName(jsonArray.getJSONObject(i).getString("pages"));
//
//            users.add(user);
//        }
//        for (Users user : users) {
//            System.out.println(user.toString());
//        }
    }

}
