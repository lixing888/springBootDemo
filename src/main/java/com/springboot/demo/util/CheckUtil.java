package com.springboot.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xy
 * @Title: Check
 * @date 2019/9/24 18:05
 * @ProjectName qkltest-tax190910
 * @Description: TODO
 */
public class CheckUtil {
    private static final Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    private static final Pattern p1 = Pattern.compile("\t|\r|\n");

    public static String checkout(String KEY) {
        // TODO Auto-generated method stub
        int KEYlength = KEY.length();
        if (KEYlength > 16) {
            KEY = KEY.substring(0, 16);
        }

        if (KEYlength < 16) {
            for (int i = 0; i < 16 - KEYlength; i++) {
                KEY += "F";
            }
        }
        return KEY;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     *
     * @param str
     * @return
     */
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str != null) {
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     *
     * @param str
     * @return
     */
    public static String replaceNStr(String str) {
        String repl = "";
        if (str != null) {
            Matcher m = p1.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }
}
