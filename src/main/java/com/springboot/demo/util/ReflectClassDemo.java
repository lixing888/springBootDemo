package com.springboot.demo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectClassDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //使用 Class 类的 forName 静态方法
        Class c1 = Class.forName("com.springboot.demo.util.ReflectClassDemo");
        System.out.println(c1.getCanonicalName());
        Class c2 = Class.forName("[D");
        System.out.println(c2.getCanonicalName());
        Class c3 = Class.forName("[[Ljava.lang.String;");
        System.out.println(c3.getCanonicalName());
        //直接获取某一个对象的 class
        Boolean b;
        // Class c = b.getClass(); // 编译错误
        Class c11 = Boolean.class;
        System.out.println(c11.getCanonicalName());
        Class c21 = java.io.PrintStream.class;
        System.out.println(c21.getCanonicalName());
        Class c31 = int[][][].class;
        System.out.println(c31.getCanonicalName());
        /**
         * 通过反射来创建实例对象主要有两种方式：
         *
         * 用 Class 对象的 newInstance 方法。
         * 用 Constructor 对象的 newInstance 方法。
         */
        Class<?> c111 = StringBuilder.class;
        StringBuilder sb = (StringBuilder) c111.newInstance();
        sb.append("aaa");
        System.out.println(sb.toString());
        //获取String所对应的Class对象
        Class<?> c211 = String.class;
        //获取String类带一个String参数的构造器
        Constructor constructor = c211.getConstructor(String.class);
        //根据构造器创建实例
        String str2 = (String) constructor.newInstance("bbb");
        System.out.println(str2);

    }


}
