package com.springboot.demo.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Copyright (C), 2018-2021
 * FileName: LRUCache0
 * Author:   ZSB
 * Date:     2021/6/3 16:41
 * Description: LRU 缓存机制 使用LinkedHashMap
 */
public class LRUCache0 {
    int capacity;
    Map<Integer, Integer> map;

    public LRUCache0(int capacity) {
        this.capacity = capacity;
        map = new LinkedHashMap<>();
    }

    public int get(int key) {
        //如果没有找到
        if (!map.containsKey(key)) {
            return -1;
        }
        //找到了就刷新数据
        Integer value = map.remove(key);
        map.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            map.remove(key);
            map.put(key, value);
            return;
        }
        map.put(key, value);
        //超出capacity，删除最久没用的即第一个,或者可以复写removeEldestEntry方法
        if (map.size() > capacity) {
            map.remove(map.entrySet().iterator().next().getKey());
        }
    }

    public static void main(String[] args) {
        LRUCache0 lruCache = new LRUCache0(10);
        for (int i = 0; i < 10; i++) {
            lruCache.map.put(i, i);
            System.out.println(lruCache.map.size());
        }
        System.out.println(lruCache.map);
        lruCache.put(10, 200);
        System.out.println(lruCache.map);
    }
}
