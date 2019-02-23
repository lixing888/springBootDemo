package com.springboot.demo.util;

import com.springboot.demo.vo.Car;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Java8SteamUtil {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        // 顺序运行
        long count1 = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count1);
        // 串行
        long count2 = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count2);

        System.out.println("========================");

        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(filtered);
        // forEach迭代输出
        filtered.forEach(System.out::println);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(""));
        System.out.println("合并字符串: " + mergedString);

        System.out.println("========================");

        // map用于映射每个元素对应的结果
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        //以下代码片段使用 map 输出了元素对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        squaresList.forEach(System.out::println);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("个数："+stats.getCount());

        System.out.println("========================");

        // limit取指定数量的流，sorted排序
        Random random = new Random();
        random.ints().limit(5).sorted().forEach(System.out::println);


        int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);
        System.out.print(num);

        //stream 的并行流操作
        long reduce = LongStream.rangeClosed(0, 100L)
                .parallel()
                .reduce(0, Long::sum);//求和
        System.out.println("stream 的并行流求和:"+reduce);


        //Lambda 表达式 只需要给静态方法 Collections.sort 传入一个List对象以及一个比较器来按指定顺序排列。通常做法都是创建一个匿名的比较器对象然后将其传递给sort方法。
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (String a, String b) -> b.compareTo(a));

    }

    public interface Converter<T1, T2> {
        void convert(int i);
    }

}
