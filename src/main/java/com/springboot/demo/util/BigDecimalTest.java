package com.springboot.demo.util;

import com.springboot.demo.vo.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalTest {

    public static void main(String[] args) {
        // 准备数据
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User(i,new BigDecimal(i+"."+i));
            userList.add(user);
        }
        // for version
        BigDecimal result1 = BigDecimal.ZERO;
        for (User user : userList) {
            result1 = result1.add(user.getMoney());
        }
        System.out.println("result1 = "+result1);
        // java 8 stream version
        BigDecimal result2 = userList.stream()
                // 将user对象的mongey取出来map为Bigdecimal
                .map(User::getMoney)
                // 使用reduce聚合函数,实现累加器
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        System.out.println("使用reduce聚合函数,实现累加器 = "+result2);
    }

}
