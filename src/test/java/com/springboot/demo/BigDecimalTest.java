package com.springboot.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author lixing
 */
public class BigDecimalTest {
    public static void main(String[] args) {

        BigDecimal num1 = new BigDecimal(0.005);
        BigDecimal num2 = new BigDecimal(1000000);
        BigDecimal num3 = new BigDecimal(-1000000);
        //尽量用字符串的形式初始化
        BigDecimal num12 = new BigDecimal("0.005");
        BigDecimal num22 = new BigDecimal("1000000");
        BigDecimal num32 = new BigDecimal("-1000000");

        //加法
        BigDecimal result1 = num1.add(num2);
        BigDecimal result12 = num12.add(num22);
        //减法
        BigDecimal result2 = num1.subtract(num2);
        BigDecimal result22 = num12.subtract(num22);
        //乘法
        BigDecimal result3 = num1.multiply(num2);
        BigDecimal result32 = num12.multiply(num22);
        //绝对值
        BigDecimal result4 = num3.abs();
        BigDecimal result42 = num32.abs();
        //除法
        BigDecimal result5 = num2.divide(num1, 20, BigDecimal.ROUND_HALF_UP);
        BigDecimal result52 = num22.divide(num12, 20, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat("###,##0");
        System.out.println("加法用value结果：" + result1);
        System.out.println("加法用string结果：" + result12);

        System.out.println("减法value结果：" + result2);
        System.out.println("减法用string结果：" + result22);

        System.out.println("乘法用value结果：" + df.format(result3));
        System.out.println("乘法用string结果：" + result32);

        System.out.println("绝对值用value结果：" + result4);
        System.out.println("绝对值用string结果：" + result42);

        System.out.println("除法用value结果：" + result5);
        System.out.println("除法用string结果：" + result52);

    }
}
