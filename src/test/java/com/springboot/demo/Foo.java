package com.springboot.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: springBootDemo
 * @description: jdk8 stream特性
 * @author: lixing
 * @create: 2020-12-11 18:07
 **/
public class Foo {
    private String name;
    private String type;
    private Double typeValue;
    private Integer count;

    public Foo(String name, String type, Double typeValue, Integer count) {
        this.name = name;
        this.type = type;
        this.typeValue = typeValue;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(Double typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", typeValue=" + typeValue +
                ", count=" + count +
                '}';
    }

    public static void main(String[] args) {
        List<Foo> fooList = new ArrayList<Foo>();
        fooList.add(new Foo("A", "san", 1.0, 2));
        fooList.add(new Foo("A", "nas", 13.0, 1));
        fooList.add(new Foo("B", "san", 112.0, 3));
        fooList.add(new Foo("C", "san", 43.0, 5));
        fooList.add(new Foo("B", "nas", 77.0, 7));
        fooList.add(new Foo("D", "nas", 12.0, 2));
        fooList.add(new Foo("F", "nas", 31.0, 3));
        List<List<Foo>> groupList = new ArrayList<>();
        fooList.stream()
                .collect(Collectors.groupingBy(Foo::getName, Collectors.toList()))
                .forEach((name, fooListByName) -> {
                    groupList.add(fooListByName);
                });

        System.out.println(JSON.toJSONString(groupList));
    }
}
