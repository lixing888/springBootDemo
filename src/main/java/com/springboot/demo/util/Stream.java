package com.springboot.demo.util;

import com.springboot.demo.entity.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: springBootDemo
 * @description: jdk8 Stream流
 * @author: lixing
 * @create: 2020-12-16 14:05
 **/
public class Stream {
    public static void main(String[] args) {
        //定义一个100元素的集合，包含A-Z
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf((char) ('A' + Math.random() * ('Z' - 'A' + 1))));
        }
        System.out.println(list);
        //统计集合重复元素出现次数，并且去重返回hashmap
        Map<String, Long> map = list.stream().
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(map);
        //由于hashmap无序，所以在排序放入LinkedHashMap里(key升序)
        Map<String, Long> sortMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                forEachOrdered(e -> sortMap.put(e.getKey(), e.getValue()));
        System.out.println(sortMap);
        //获取排序后map的key集合
        List<String> keys = new LinkedList<>();
        sortMap.entrySet().stream().forEachOrdered(e -> keys.add(e.getKey()));
        System.out.println(keys);
        //获取排序后map的value集合
        List<Long> values = new LinkedList<>();
        sortMap.entrySet().stream().forEachOrdered(e -> values.add(e.getValue()));
        System.out.println(values);

        List<Student> students = new ArrayList<>();

        students.add(new Student(1, "张三", 90));
        students.add(new Student(1, "张三", 83));
        students.add(new Student(1, "张三", 79));
        students.add(new Student(2, "李四", 60));
        students.add(new Student(3, "王五", 30));
        students.add(new Student(3, "王五", 70));
        students.add(new Student(3, "王五", 75));
        students.add(new Student(4, "赵六", 60));
        students.add(new Student(4, "赵六", 85));
        students.add(new Student(9527, "吴起", 90));
        students.add(new Student(9527, "吴起", 75));
        students.add(new Student(9527, "吴起", 98));
        students.add(new Student(9527, "吴起", 62));

        Map<Integer, IntSummaryStatistics> collect = students.stream().collect(Collectors.groupingBy(Student::getId, Collectors.summarizingInt(Student::getScore)));
        System.out.println("根据学号统计：" + collect);

        Map<Integer, Integer> collect1 = students.stream().collect(Collectors.groupingBy(Student::getId, Collectors.summingInt(Student::getScore)));
        System.out.println("根据学号求和：" + collect1 + " == 9527的总分：" + collect1.get(9527));
    }
}
