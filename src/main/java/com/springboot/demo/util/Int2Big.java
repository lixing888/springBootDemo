package com.springboot.demo.util;

/**
 * @author lixing
 */
public class Int2Big {
    static String int2big(int src) {
        final String num[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        final String unit[] = {"圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
        String dst = "";
        int count = 0;
        while (src > 0) {
            dst = (num[src % 10] + unit[count]) + dst;
            src = src / 10;
            count++;
        }
        return dst.replaceAll("零[仟佰拾]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零圆", "圆");
    }

    public static void main(String[] args) {
        //-->壹亿贰仟叁佰万肆仟伍佰零陆圆
        System.out.println(int2big(123004506));
        //-->陆佰伍拾贰万叁仟肆佰陆拾肆圆
        System.out.println(int2big(210781));
        //-->壹拾亿零壹佰万零壹佰圆
        System.out.println(int2big(1001000100));
        //-->捌佰零捌圆
        System.out.println(int2big(808));
    }

}
