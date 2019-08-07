package com.springboot.demo;

/**
 * @author lixing
 * java list 交集 并集 差集 去重复并集
 */

import com.springboot.demo.entity.Person;
import com.springboot.demo.vo.Student;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ListUtil {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        List<String> list2 = new ArrayList();
        list2.add("1111");
        list2.add("2222");
        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---得到交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);
        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);
        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);
        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out::println);
        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List1---");
        list1.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEach(System.out::println);         // 一般有filter 操作时，不用并行流parallelStream ,如果用的话可能会导致线程安全问题     }

        List list11 = new ArrayList();
        list11.add("1111");
        list11.add("2222");
        list11.add("3333");

        List list12 = new ArrayList();
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
        set1.add("d");

        set2.add("c");
        set2.add("d");
        set2.add("e");

        //交集
        boolean result = set1.retainAll(set2);

        System.out.println("交集是 " + set1);


        //差集
        //list1.removeAll(list2);
        //无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);

        Iterator<String> it = list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        String deptId = "11";
        List<String> grdDeptNo = new ArrayList<>();
        List<String> deptLevelNoList = Arrays.asList("0@11", "0@11@2@3@4@5", "0@11@2@3@7@8", "0@11@2@3@7@8@10@13", "0@11@2@3@8@9");
        List<String> childIds = Arrays.asList("2", "3", "7");
        for (String deptLevelNo : deptLevelNoList) {
            String deptLevel = deptLevelNo.substring(deptLevelNo.lastIndexOf(deptId) + deptId.length());
            System.out.println("下属部门编码:" + deptLevel);

            if (!deptLevel.isEmpty()) {
                String[] split = deptLevel.split("@");
                for (int i = 0; i < split.length; i++) {
                    String deptNo = split[i];
                    if (!deptNo.isEmpty()) {
                        grdDeptNo.add(split[i]);
                    }
                }
            }

        }

        Set<String> grpIds = new HashSet<>(grdDeptNo);
        System.out.println("子部门个数：" + grpIds.size());
        for (String grpId : grpIds) {
            System.out.println("子部门:" + grpId);
        }
        grpIds.removeAll(childIds);
        //孙子辈的部门id
        System.out.println("孙子辈的部门个数：" + grpIds.size());
        for (String grpId : grpIds) {
            System.out.println("管辖部门：" + grpId);
        }
        //System.out.println("-----------------------------------\n");
        //printStr(list1);

        if (Objects.equals("2", "2")) {
            System.out.println("Objects判断两个对象是否相等。");
        }

        String str = DecimalFormat.getNumberInstance().format(1245600000);
        String currecy = NumberFormat.getCurrencyInstance().format(1245600000);
        System.out.println("转换成Currency格式：" + currecy);
        System.out.println("转换成带千分位的格式：" + str);

        BigDecimal a = new BigDecimal("233333333333333333333333333");
        //DecimalFormat df = new DecimalFormat(",###,##0");
        //没有小数
        DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留2位小数
        String format = df.format(a);
        System.out.println(format);


        List<Student> list = new ArrayList<>();

        list.stream().map(e -> {
            Person person = new Person();
            person.setAge(Integer.valueOf(e.getAge()));
            person.setName(e.getName());
            return person;
        }).collect(Collectors.toList());


    }

    public static void printStr(List list1) {
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }

    }
}
