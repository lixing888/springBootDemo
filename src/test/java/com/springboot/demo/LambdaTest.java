package com.springboot.demo;

import com.springboot.demo.vo.User;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @program: springBootDemo
 * @description: jdk8 Lambda 表达式
 * @author: lixing
 * @create: 2020-12-16 14:56
 **/
public class LambdaTest {
    @Test
    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步执行方法
                System.out.println("线程运行...");
            }
        }).start();
    }

    //若把它改为用 lambda 表达式，则为,
    @Test
    public void test1() {
        // 一行搞定
        new Thread(() -> System.out.println("线程运行...")).start();
    }

    @Test
    public void test2() {
        //打印传入的 msg
        printMsg((s) -> System.out.println(s), "听朋友说「烟雨星空」公众号不仅文章好看，还免费送程序员福利，我心动了");
    }

    public void printMsg(Consumer<String> consumer, String msg) {
        //消费型，只有传入参数，没有返回值
        consumer.accept(msg);
    }

    @Test
    public void test3() {
        //返回一个 0~99 的随机数
        Integer content = getContent(() -> new Random().nextInt(100));
        System.out.println(content);
    }

    public Integer getContent(Supplier<Integer> supplier) {
        //供给型，传入参数为空，带返回值
        return supplier.get();
    }

    @Test
    public void test4() {
        //传入一个字符串，然后把它都转换成大写字母。
        System.out.println(transfer((str) -> str.toUpperCase(), "My wechat : mistyskys"));
    }

    public String transfer(Function<String, String> func, String str) {
        // 函数型，传入一个参数，对其进行处理之后，返回一个结果
        return func.apply(str);
    }

    @Test
    public void test5() {
        //定义一个list，用来做筛选
        ArrayList<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("jerry");
        list.add("tom");
        //筛选出集合中，字符串长度大于 3 的，并加入到结果集。
        //List<String> filterResult = filter((str) -> str.length() > 3, list);
        //System.out.println(filterResult.toString());
    }


    //对象 :: 实例方法
//类 :: 静态方法
//类 :: 实例方法
//类 :: new
    public static void main(String[] args) {
        //遍历数组里边的元素，并打印，用lambda表达式
        String[] arr = new String[]{"zhangsan", "lisi"};
        Arrays.asList(arr).forEach((s) -> System.out.println(s));

        // 注意：方法引用中的方法名不可带括号。
        Arrays.asList(arr).forEach(System.out::println);
        //函数式接口的抽象方法的参数列表和返回值类型，必须与方法引用对应的方法参数列表和返回值类型保持一致(情况3除外，比较特殊)。
        //======= 1.对象::实例方法 =========
        // lambda 表达式
        Consumer consumer1 = (s) -> System.out.println(s);
        consumer1.accept("hello world");
        //方法引用。Consumer的accept方法，和System.out的println方法结构一样，
        //都是传入一个参数，无返回值。故可以用方法引用。
        Consumer consumer2 = System.out::println;
        consumer2.accept("hello java");

        //======= 2.类::静态方法 =========
        Integer[] arr1 = new Integer[]{12, 20, 15};
        List<Integer> list = Arrays.asList(arr1);
        // lambda 表达式
        Comparator<Integer> com1 = (o1, o2) -> Integer.compare(o1, o2);
        Collections.sort(list, com1);
        //方法引用。Comparator的compare方法，和Integer的compare静态方法结构一样，
        //都是传入两个参数，返回一个int值，故可以用方法引用。
        Comparator<Integer> com2 = Integer::compare;
        Collections.sort(list, com2);

        //======= 3.类::实例方法 =========
        // lambda表达式
        Comparator<Integer> com3 = (o1, o2) -> o1.compareTo(o2);
        //方法引用。这种形式比较特殊，(o1, o2) -> o1.compareTo(o2) ，
        //当第一个参数o1为调用对象，且第二个参数o2为需要引用方法的参数时，才可用这种方式。
        Comparator<Integer> com4 = Integer::compareTo;

        //======= 4.类::new =========
        // lambda表达式
        Supplier<String> supplier1 = () -> new String();
        //方法引用。这个就比较简单了，就是类的构造器引用，一般用于创建对象。
        Supplier<String> supplier2 = String::new;
    }





}
