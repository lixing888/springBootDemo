package com.springboot.demo.util;

import com.springboot.demo.vo.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lixng
 */
public class SteamUtil {

    public static void main(String[] args) {
        //============Java 7的排序，取值实现=================
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 10, Transaction.Type.GEOCERY));
        transactions.add(new Transaction(3, 30, Transaction.Type.GEOCERY));
        transactions.add(new Transaction(6, 60, Transaction.Type.GEOCERY));
        transactions.add(new Transaction(5, 50, Transaction.Type.GEOCERY));
        transactions.add(new Transaction(2, 20, Transaction.Type.A));
        transactions.add(new Transaction(4, 40, Transaction.Type.C));

        // JDK 7 发现type为grocery的所有交易
        List<Transaction> groceryTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType() == Transaction.Type.GEOCERY) {
                groceryTransactions.add(t);
            }
        }
        // 集合排序 交易值降序排序
        Collections.sort(groceryTransactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        // 交易ID 获取
        List<Integer> transactionIds = new ArrayList<>();
        for (Transaction t : groceryTransactions) {
            transactionIds.add(t.getId());
        }
        //[6, 5, 3, 1]
        System.out.println(transactionIds);
        //List转Map
        Map<Integer, Transaction> transactionMap = transactions.stream().collect(Collectors.toMap(Transaction::getId, Function.identity()));
        for (int i = 1; i < transactionIds.size(); i++) {
            int id = i;
            System.out.println(id + "map根据Key取value:" + transactionMap.get(id).getValue());
        }
        //============Java 8的排序，取值实现=================
        // JDK 8 如果发现type为grocery的所有交易, 然后返回以交易值降序排序的交易ID集合
        List<Integer> transactionsIds =
                transactions.parallelStream().filter(t -> t.getType() == Transaction.Type.GEOCERY)
                        .sorted(Comparator.comparing(Transaction::getValue).reversed())
                        .map(Transaction::getId)
                        .collect(Collectors.toList());
        //[6, 5, 3, 1]
        System.out.println(transactionsIds);

        //时间比较
        String beginTime = new String("2020-10-15 10:22:22");
        String endTime = new String("2020-10-15 10:32:22");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date sd1 = df.parse(beginTime);
            Date sd2 = df.parse(endTime);
            System.out.println("sd1在sd2之前:" + sd1.before(sd2));
            System.out.println("sd1在sd2之后:" + sd1.after(sd2));

            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MINUTE,-10);
            Date validDate=calendar.getTime();
            System.out.println("十分钟前的时间："+validDate);
            System.out.println("validDate在sd2之qian:" + validDate.before(sd2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //方法2
        Integer i = beginTime.compareTo(endTime);
        System.out.println("beginTime.compareTo(endTime):" + i);



    }
}
