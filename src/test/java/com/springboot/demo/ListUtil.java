package com.springboot.demo;

/**
 * @author lixing
 * java list 交集 并集 差集 去重复并集
 */
import java.util.*;

import static java.util.stream.Collectors.toList;

public class ListUtil {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");
        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---得到交集 intersection---");
        intersection.parallelStream().forEach(System.out :: println);
        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out :: println);
        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out :: println);
        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out :: println);
        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());        System.out.println("---得到去重并集 listAllDistinct---");        listAllDistinct.parallelStream().forEach(System.out :: println);         System.out.println("---原来的List1---");        list1.parallelStream().forEach(System.out :: println);        System.out.println("---原来的List2---");        list2.parallelStream().forEach(System.out :: println);         // 一般有filter 操作时，不用并行流parallelStream ,如果用的话可能会导致线程安全问题     }

        List list11 =new ArrayList();
        list11.add("1111");
        list11.add("2222");
        list11.add("3333");

        List list12 =new ArrayList();
        list12.add("3333");
        list12.add("4444");
        list12.add("5555");

        //并集
        //list1.addAll(list2);
        //交集
        //list1.retainAll(list2);
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        boolean c = set1.add("c");

        set2.add("c");
        set2.add("d");
        set2.add("e");

        //交集
        set1.retainAll(set2);

        System.out.println("交集是 "+set1);
    

        //差集
        //list1.removeAll(list2);
        //无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);

        Iterator<String> it=list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        //System.out.println("-----------------------------------\n");
        //printStr(list1);

    }

    public static void printStr(List list1){
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
    }
}
