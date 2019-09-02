package com.springboot.demo.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Streamutil8 {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("aaa", "aab", "aba", "aaa", "aab", "aba", "aac", "ddd", "bbb", "ccc", "a2a", "d2d", "b2b", "c2c", "a3a", "d3d", "b3b", "c3c");
        //filter：通过设置条件来过滤元素。
        //以上代码使用filter方法过滤出只包含”a”的元素，然后通过forEach将满足条件的元素遍历出来。输出如下：
        list.stream()
                .filter((s) -> s.contains("a"))
                .forEach(s -> System.out.println(s));

        //map：就是将对应的元素使用给定方法进行转换。
        //在filter的基础上，给每个元素后面添加字符串”—rmb”，输出如下：
        list.stream()
                .filter((s) -> s.contains("a"))
                .map((s) -> s + "---rmb")
                .forEach(s -> System.out.println(s));
        //distinct：将集合中的元素去重。
        list.stream()
                .distinct()
                .forEach(s -> System.out.println(s));

        //sorted：将集合中的元素排序。
        list.stream()
                //.sorted()
                .sorted((s1, s2) -> s2.compareTo(s1))
                .forEach(s -> System.out.println(s));

        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        integerList.stream()
                .sorted()//排序
                .forEach(s -> System.out.println(s));

        //min:获取集合中最小值。
        Integer min = integerList.stream()
                .filter(a -> a > 1)
                .min((Integer a, Integer b) -> a.compareTo(b))
                .get();
        System.out.println("min:获取集合中最小值。" + min);
        //max：获取集合中最大值。
        Integer max = integerList.stream()
                .filter(a -> a > 1)
                .max((Integer a, Integer b) -> a.compareTo(b))
                .get();
        System.out.println("min:获取集合中最大值。" + max);

        //求和  此处需要两个参数，第一个参数为起始值，第二个参数为引用的方法。从起始值开始，每个元素执行一次引用的方法（方法引用的中的两个参数：第一个参数为上个元素执行方法引用的结果，第二个参数为当前元素）。
        int integer = integerList.stream().reduce(5, (a, b) -> a + b);
        System.out.println("求和：" + integer);

        //合并
        Stream.concat(integerList.stream(), list.stream())
                .forEach(s -> System.out.println(s));
        //parallelStream具有平行处理能力，处理的过程会分而治之，也就是将一个大任务切分成多个小任务，这表示每个任务都是一个操作，因此像以下的程式片段：
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream().sorted()
                .forEach(System.out::println);


    }
}
