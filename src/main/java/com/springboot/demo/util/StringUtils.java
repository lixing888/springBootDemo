package com.springboot.demo.util;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringUtils {

    private static Pattern pattern1 = Pattern.compile("([A-Za-z\\d]+)(_)?");

    private static Pattern pattern2 = Pattern.compile("[A-Z]([a-z\\d]+)?");

    /**
     * 下划线转驼峰法(默认小驼峰)
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean... smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();

        Matcher matcher = pattern1.matcher(line);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();

        Matcher matcher = pattern2.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            //sb.append(word.toUpperCase());//大写
            sb.append(word.toLowerCase());//小写
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "are_you_dou_bi_yellowcong";
        //下划线转驼峰（大驼峰）
        //AreYouDouBiYellowcong
        String camel = underline2Camel(line, false);
        System.out.println(camel);

        //下划线转驼峰（小驼峰）
        //areYouDouBiYellowcong
        camel = underline2Camel(line);
        System.out.println(camel);

        //驼峰转下划线
        //ARE_YOU_DOU_BI_YELLOWCONG
        System.out.println(camel2Underline(camel));

        //下划线转驼峰
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "department_id"));


        String orderColumn = "orderColumn";
        //输入是LOWER_CAMEL，输出是LOWER_UNDERSCORE
        orderColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderColumn);
        //order_column
        System.out.println(orderColumn);
        orderColumn = "orderColumn";
        //输入是LOWER_CAMEL，输出是UPPER_CAMEL
        orderColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, orderColumn);
        System.out.println(orderColumn);//OrderColumn
        orderColumn = "order_column";//输入是LOWER_UNDERSCORE，输出是LOWER_CAMEL
        orderColumn = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, orderColumn);
        System.out.println(orderColumn);//orderColumn


        System.out.println("与jar包同级：" + GetServerRealPathUnit.getPath(""));

        String left = "左值";
        String symbolName = "符号名称";
        String right = "右值";
        System.out.println("字符串拼接；" + String.format("%s %s %s", left, symbolName, right));

        //String转Boolean
        String a = "true";
        System.out.println("String转Boolean："+Boolean.parseBoolean(a));;
        Boolean.getBoolean(a);
        //Boolean转String
        boolean b = true;
        String s = String.valueOf(b);
        System.out.println("Boolean转String："+s);

        String str = null;
        str = String.format("Hi,%s", "王力");
        System.out.println(str);
        str = String.format("Hi,%s:%s.%s", "王南", "王力", "王张");
        System.out.println(str);
        System.out.printf("字母a的大写是：%c %n", 'A');
        System.out.printf("3>7的结果是：%b %n", 3 > 7);
        System.out.printf("100的一半是：%d %n", 100 / 2);
        System.out.printf("100的16进制数是：%x %n", 100);
        System.out.printf("100的8进制数是：%o %n", 100);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50 * 0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50 * 0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50 * 0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50 * 0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');
        // 浮点数的打印
        System.out.println(new BigDecimal("10000000000").toString());
        // 普通的数字字符串
        System.out.println(new BigDecimal("100.000").toString());
        // 去除末尾多余的0
        System.out.println(new BigDecimal("100.000").stripTrailingZeros().toString());
        // 避免输出科学计数法
        System.out.println(new BigDecimal("100.990").stripTrailingZeros().toPlainString());

    }

    /**
     * 输入类型验证
     *
     * @param valueType
     * @param value
     */
    private void addConstantCheck(String valueType, String value) {
        boolean boo;
        switch (valueType) {
            case "NUMBER":
                boo = NumberUtil.isNumber(value);
                break;
            case "BOOLEAN":
                boo = BooleanUtil.toBoolean(value);
                break;
            default:
                return;
        }
        if (!boo) {
            throw new RuleEngineException("值与类型不匹配");
        }
    }
}

