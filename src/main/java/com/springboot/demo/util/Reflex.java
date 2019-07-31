package com.springboot.demo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lixing
 * java反射机制
 */
public class Reflex {

    /**
     * class.getDeclaredFields();获取class对象的所有属性
     * class.getFields();获取class对象的public属性
     * class.getDeclaredMethods();获取class对象的所有声明方法
     * class.getMethods();获取class对象的所有方法
     * class.getSuperclass();获取class对象的父类
     * class.getInterfaces();获取class对象的所有接口
     * class.getConstructors();获取class对象public构造函数
     * class.getDeclaredConstructors();获取class对象的所有声明构造函数
     * class.getAnnotations();获取class对象的所有注解
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            //1.根据className得到class对象
            Class clazz = Class.forName("JcUser");

            //2.获取People类的所有方法信息
            System.out.println("------People类的所有方法--------");
            Method[] method = clazz.getDeclaredMethods();
            for(Method t:method) {
                System.out.println(t.toString());
            }

            //3.获取所有公有构造函数
            System.out.println("------People类的所有公有构造函数--------");
            Constructor[] constrArray = clazz.getConstructors();
            for(Constructor c:constrArray) {
                System.out.println(c);
            }

            //4.获取People类的所有构造函数
            System.out.println("------People类的所有构造函数--------");
            Constructor[] constructors = clazz.getDeclaredConstructors();
            for(Constructor c:constructors) {
                System.out.println(c.toString());
            }

            //5.获取People类的所有方法信息
            System.out.println("------People类的所有方法信息--------");
            Field[] fields = clazz.getDeclaredFields();
            for(Field f:fields) {
                System.out.println(f.toString());
            }

            //6.获取无参的构造函数
            Constructor constructor = clazz.getConstructor(null);
            System.out.println("-------无参的构造函数---------");
            System.out.println("constructor = " + constructor);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
