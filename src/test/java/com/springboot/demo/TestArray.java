package com.springboot.demo;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

//检查数组是否包含某个值的方法
public class TestArray {
    //使用List
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    //使用Set
    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    //使用循环判断
    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    //查找有序数组中是否包含某个值的用法
    public static boolean useArraysBinarySearch(String[] arr, String targetValue) {
        int a = Arrays.binarySearch(arr, targetValue);
        if (a > 0)
            return true;
        else
            return false;
    }

    //使用ArrayUtils
    public static boolean useArrayUtils(String[] arr, String targetValue) {
        return ArrayUtils.contains(arr, targetValue);
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"CD", "BC", "EF", "DE", "AB", "JK"};
        //use list
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useList(arr, "A");
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("useList:" + duration / 1000000);
        //use set
        long startTime2 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useSet(arr, "A");
        }
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("useSet:" + duration2 / 1000000);
        //use loop
        long startTime3 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useLoop(arr, "A");
        }
        long endTime3 = System.nanoTime();
        long duration3 = endTime3 - startTime3;
        System.out.println("useLoop:" + duration3 / 1000000);
        //use Arrays.binarySearch()
        long startTime4 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useArraysBinarySearch(arr, "A");
        }
        long endTime4 = System.nanoTime();
        long duration4 = endTime4 - startTime4;
        System.out.println("useArraysBinarySearch:" + duration4 / 1000000);


        String[] str = {"FA_COST_NEW", "FA_COST_K_PROJECT", "FA_COST_NEW", "FA_COST_K_PROJECT", "project_modify_process", "project_application_process"};
        String s = "FA_COST_NEW";
        Set<String> set1 = Arrays.asList(str).stream().collect(Collectors.toSet());
        System.out.println("set无序非重复:" + set1);
        //先将数组转换为List
        System.out.println(s + " 是否包含在" + Arrays.asList(str) + " 中:" + Arrays.asList(str).contains(s));
        Set<String> set = new HashSet<>(Arrays.asList(str));
        System.out.println(set.contains(s));

        String[] str1 = {"张三", "李四", "王五", "宋六", "赵七", "朱八", "何九", "田十"};
        List<String> list = Arrays.asList(str1);//将数组转换为list集合
        List<String> arrayList = new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法

        arrayList.remove("张三");
        System.out.println("ArrayList是否包含 张三:" + arrayList.contains("张三"));
        for (String s1 : arrayList) {
            System.out.println("人名：" + s1);
        }

    }
}
/*
 * 显然，使用一个简单的循环方法比使用任何集合都更加高效。许多开发人员为了方便，都使用第一种方法，但是他的效率也相对较低。因为将数组压入Collection类型中，首先要将数组元素遍历一遍，然后再使用集合类做其他操作。
 */
