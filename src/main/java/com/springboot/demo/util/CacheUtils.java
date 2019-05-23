package com.springboot.demo.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.thread.ThreadUtil;

/**
 * @author lixing
 */
public class CacheUtils {

    public static void main(String[] args) {
        //新建FIFOCache
        Cache<String, String> fifoCache = CacheUtil.newFIFOCache(3);
        //加入元素，每个元素可以设置其过期时长，DateUnit.SECOND.getMillis()代表每秒对应的毫秒数，在此为3秒
        fifoCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);

        //由于缓存容量只有3，当加入第四个元素的时候，根据FIFO规则，最先放入的对象将被移除
        fifoCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

        //value1为null
        String value1 = fifoCache.get("key1");
        System.out.println("返回结果:"+value1);

        //============
        // LFU(least frequently used) 最少使用率策略。
        // 根据使用次数来判定对象是否被持续缓存（使用率是通过访问次数计算），
        // 当缓存满时清理过期对象，清理后依旧满的情况下清除最少访问（访问计数最小）的对象并将其他对象的访问数减去这个最小访问数，
        // 以便新对象进入后可以公平计数。========
        Cache<String, String> lfuCache = CacheUtil.newLFUCache(3);
        //通过实例化对象创建
        //LFUCache<String, String> lfuCache = new LFUCache<String, String>(3);

        lfuCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        //使用次数+1
        lfuCache.get("key1");
        lfuCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        //使用次数+1
        lfuCache.get("key2");
        lfuCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        lfuCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

        //由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（3被移除）
        //null
        String value2 = lfuCache.get("key2");
        //null
        String value3 = lfuCache.get("key3");
        System.out.println("value2返回结果:"+value2);
        System.out.println("value3返回结果:"+value3);

        //================LRU (least recently used)最近最久未使用缓存。
        // 根据使用时间来判定对象是否被持续缓存，当对象被访问时放入缓存，
        // 当缓存满了，最久未被使用的对象将被移除。此缓存基于LinkedHashMap，
        // 因此当被缓存的对象每被访问一次，这个对象的key就到链表头部。
        // 这个算法简单并且非常快，他比FIFO有一个显著优势是经常使用的对象不太可能被移除缓存。
        // 缺点是当缓存满时，不能被很快的访问。===============
        Cache<String, String> lruCache = CacheUtil.newLRUCache(3);
        //通过实例化对象创建
        //LRUCache<String, String> lruCache = new LRUCache<String, String>(3);
        lruCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        //使用时间推近
        lruCache.get("key1");
        lruCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);

        //由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（2被移除）
        //null
        String value4 = lruCache.get("key");
        System.out.println("value4返回结果:"+value4);
        //==============定时缓存，对被缓存的对象定义一个过期时间，
        // 当对象超过过期时间会被清理。此缓存没有容量限制，
        // 对象只有在过期后才会被移除。===========
        //创建缓存，默认4毫秒过期
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
        //实例化创建
        //TimedCache<String, String> timedCache = new TimedCache<String, String>(4);
        timedCache.put("key1", "value1", 1);
        //默认过期(4毫秒)
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 5);
        //默认过期(4毫秒)
        timedCache.put("key3", "value3");

        //启动定时任务，每5毫秒秒检查一次过期
        timedCache.schedulePrune(5);

        //等待5毫秒
        ThreadUtil.sleep(5);

        //5毫秒后由于value2设置了5毫秒过期，因此只有value2被保留下来
        //null
        String value11 = timedCache.get("key1");
        //value2
        String value22 = timedCache.get("key2");

        //5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value33 = timedCache.get("key3");
        System.out.println("value11返回结果:"+value11);
        System.out.println("value22返回结果:"+value22);
        System.out.println("value33返回结果:"+value33);


        //取消定时清理
        timedCache.cancelPruneSchedule();

        //============================================



    }
}
