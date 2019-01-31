package com.springboot.demo.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ListSum {
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(createMap("小溪塔", "A", 9.0,5.0));
        list.add(createMap("小溪塔", "B", 7.0,4.0));
        list.add(createMap("小溪塔", "C", 4.574,2.52));
        list.add(createMap("小溪塔", "D", 8.435,3.53));
        list.add(createMap("太平溪", "A", 7.246,4));
        list.add(createMap("太平溪", "B", 5,3));
        list.add(createMap("雾渡河镇", "A", 7.0,2.5));
        list.add(createMap("雾渡河镇", "B", 5.0,3.5));
        List<Map<String, Object>> xlist=groupCount(list);
        for (Map<String, Object> map : xlist) {
            System.out.println("==="+map);
        }


    }
    public static List<Map<String, Object>> groupCount(List<Map<String, Object>> list){
        List<Map<String, Object>> xList =new ArrayList<Map<String,Object>>();
        if(list!=null&&list.size()>0){
            double zhj1=0,gdzhj1=0;
            xList.add(list.get(0));
            for(int i=1;i<list.size();i++){
                int size=xList.size()-1;
                Map<String,Object> map=list.get(i);
                Map<String,Object> map1=xList.get(size);
                String xz = object2String(map.get("乡镇"));
                String xz1 = object2String(map1.get("乡镇"));
                double zj = object2Double(map.get("总计"));
                double gdzj = object2Double(map.get("耕地"));
                zhj1=add(zj,zhj1);gdzhj1=add(gdzj,gdzhj1);
                if(xz.equals(xz1)){
                    double zj1 = object2Double(map1.get("总合计"));
                    double gdzj1 = object2Double(map1.get("耕地"));
                    double zhj=add(zj,zj1);
                    double gdzhj=add(gdzj,gdzj1);
                    Map<String, Object> map2=new HashMap<String, Object>();
                    map2.put("乡镇总合计", xz+"总合计");
                    map2.put("村", null);
                    map2.put("总计",zhj);
                    map2.put("耕地",gdzhj);
                    xList.add(map);
                    xList.add(map2);
                }else{
                    xList.add(map);
                }
            }
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("所有总计","所有总计");
            map.put("总计", zhj1);
            map.put("耕地", gdzhj1);
            xList.add(map);
        }

        return xList;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String object2String(Object obj){
        return obj == null ? "" : obj.toString();
    }
    public static double object2Double(Object obj){
        String value = object2String(obj);
        return "".equals(value) ? 0 : Double.valueOf(value);
    }
    public static Map<String, Object> createMap(String town,String village,double zjmj,double gdmj){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("乡镇", town);
        map.put("村", village);
        map.put("总计",zjmj);
        map.put("耕地", gdmj);
        return map;
    }
}
