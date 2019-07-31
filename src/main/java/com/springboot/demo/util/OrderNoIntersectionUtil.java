package com.springboot.demo.util;

import cn.hutool.core.collection.CollUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈orderno 求交集〉
 *
 * @author lixing
 * @create 2019/6/20
 * @since 1.0.0
 */
public class OrderNoIntersectionUtil {
    private OrderNoIntersectionUtil() {

    }


    public static Collection<Integer> intersectionOrderNos(List<List<Integer>> ruleDataOrderNos) {
        Collection<Integer> orderNos;
        int ruleDataAndDraftOrderNosSize = ruleDataOrderNos.size();
        if (ruleDataAndDraftOrderNosSize == 0) {
            //orderno 为空
            orderNos = Collections.emptyList();
        } else if (ruleDataAndDraftOrderNosSize == 1) {
            orderNos = ruleDataOrderNos.get(0);
        } else {
            orderNos = CollUtil.intersection(ruleDataOrderNos.get(0), ruleDataOrderNos.get(1));
            for (int i = 2; i < ruleDataAndDraftOrderNosSize; i++) {
                orderNos = CollUtil.intersection(orderNos, ruleDataOrderNos.get(i));
            }
        }
        return orderNos;
    }


    public static String getChinaTime() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
