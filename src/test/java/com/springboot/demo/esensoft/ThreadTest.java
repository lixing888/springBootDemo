package com.springboot.demo.esensoft;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.*;

public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConcurrentHashMap<Integer, String> dataMap = new ConcurrentHashMap<>(16);
        dataMap.put(3, "null,null");
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Void> future1 = es.submit(new ReplaceNullCallable(3, dataMap));
        Future<Void> future2 = es.submit(new ReplaceNullCallable(3, dataMap));
        future1.get();
        future2.get();
        System.out.println("最终结果：" + dataMap.get(3));

    }

    public static class ReplaceNullCallable implements Callable<Void> {
        private int key;
        private ConcurrentHashMap<Integer, String> dataMap;

        public ReplaceNullCallable(int key, ConcurrentHashMap<Integer, String> dataMap) {
            this.key = key;
            this.dataMap = dataMap;
        }

        @Override
        public Void call() {
            while (true) {
                String oldValue = dataMap.get(key);
                if (!oldValue.contains("null")) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String newValue = oldValue.replaceFirst("null", DateUtil.now());
                dataMap.replace(key, oldValue, newValue);
            }
            return null;
        }
    }
}
