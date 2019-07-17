package com.springboot.demo.ListUtil;

public class Test {
    public static void main(String[] args) {
      /*  MyArrayList<String> list = new MyArrayList();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.remove("bbbb");
        list.show();
        System.out.println(list.size());*/
        MyLienkedList lienkedList = new MyLienkedList();
        lienkedList.add("aaaa");
        lienkedList.add("bbbb");
        lienkedList.add("cccc");
        lienkedList.add("dddd");
        lienkedList.show();
    }
}
