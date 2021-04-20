package com.springboot.demo.util;

import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.StringJoiner;
import java.util.UUID;
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

        String str = "";
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int num = rand.nextInt(3);
            switch (num) {
                case 0:
                    //生成随机小写字母
                    char c1 = (char) (rand.nextInt(26) + 'a');
                    str += c1;
                    break;
                case 1:
                    //生成随机大写字母
                    char c2 = (char) (rand.nextInt(26) + 'A');
                    str += c2;
                    break;
                case 2:
                    //生成随机数字
                    str += rand.nextInt(10);
            }
        }
        System.out.println("生成的5个随机验证码是:" + str);

        String charRandom = "";
        for (int i = 0; i < 5; i++) {
            int c1 = (int) (Math.random() * 26 + 97);
            charRandom += (char) c1;
        }

        String ziMuRandom = "";
        Random rand1 = new Random();
        for (int i = 0; i < 5; i++) {
            char c1 = (char) (rand1.nextInt(26) + 'a');
            ziMuRandom += c1;
        }
        System.out.println("5位随机字母：" + ziMuRandom);
        String nbrRandom = "";
        Random randNum = new Random();
        for (int i = 0; i < 5; i++) {
            nbrRandom += randNum.nextInt(10);
        }

        System.out.println("5位随机数字：" + nbrRandom);
        String userName = charRandom + "_" + ziMuRandom + "_" + nbrRandom;
        System.out.println("随机生成的用户名：" + userName);
        String bmlUserName = "epri_lixingaaa";
        System.out.println("====:" + bmlUserName.substring(0, bmlUserName.length() - 3));

        StringJoiner sj = new StringJoiner("");
        sj.add("Hello");
        sj.add("World");
        //当我们使用StringJoiner(CharSequence delimiter)初始化一个StringJoiner的时候，这个delimiter其实是分隔符，并不是字符串的初始值。
        System.out.println("sj:" + sj);
        StringJoiner sj1 = new StringJoiner(",", "[", "]");
        sj1.add("Hello");
        sj1.add("World");
        //StringJoiner(CharSequence delimiter,CharSequence prefix,CharSequence suffix)的第二个和第三个参数分别是拼接后的字符串的前缀和后缀。
        System.out.println("sj1:" + sj1);

        for (int i = 0; i < 101; i++) {
            String rond = UUID.randomUUID().toString().replace("-", "");
            String sql = "insert into yshd_out_zb_zl (ID_, DJXH, NSRMC, NSRSBH, SQYH, SQXXLY, TJSQSJ, SFZSQYXQ, SQYXQQSRQ, SQYXQZZRQ, BZ, DELETE_FLAG, SJTBKSSJ, SJTBJSSJ, TXID, SJJSKSSJ, SJJSJSSJ)\n" +
                    "values (" + "'" + rond + "'" + ", 1.01111010100002E19, '北京如月媛管理咨询有限公司', '91110112MA00B1JQ3P', '00002', '北京市电子税务局', '202011231740', null, null, null, null, '1', to_date('24-11-2020 07:41:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-11-2020 07:41:37', 'dd-mm-yyyy hh24:mi:ss'), 'ac6301e90055cebb4cd5110bf6cd183cfc3ee21f17f64cdd9f000b2478d9ad19', null, null);\n";
            System.out.println(sql);
        }

    }
}

