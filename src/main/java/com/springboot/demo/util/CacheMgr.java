package com.springboot.demo.util;

import java.util.*;

/**
 * 缓存管理类
 *
 * @author Administrator
 */
public class CacheMgr {

    private static Map cacheMap = new HashMap();
    private static Map cacheConfMap = new HashMap();

    private static CacheMgr cm = null;

    // 构造方法
    private CacheMgr() {
    }

    public static CacheMgr getInstance() {
        if (cm == null) {
            cm = new CacheMgr();
            Thread t = new ClearCache();
            t.start();
        }
        return cm;
    }

    public static void main(String[] args) {
        //this.addCache("","",)
        System.out.println("进入加载缓存addData()………………。");
        CacheMgr cm = CacheMgr.getInstance();
        CacheConfModel cModel = new CacheConfModel();
        Date d = new Date();
        cModel.setBeginTime(d.getTime());
        cModel.setDurableTime(60);
        cModel.setForever(true);
        //在缓存加值
        cm.addCache("li", "lixing", cModel);
        //获取缓存
        //获取缓存个数
        int num = cm.getSize();
        Object ob = cm.getValue("li");
        System.out.println("缓存个数为：" + num);
        System.out.println("缓存数值：value====" + ob);
    }

    /**
     * 增加缓存
     *
     * @param key
     * @param value
     * @param ccm   缓存对象
     * @return
     */
    public boolean addCache(Object key, Object value, CacheConfModel ccm) {
        System.out.println("开始增加缓存－－－－－－－－－－－－－");
        boolean flag = false;
        try {
            cacheMap.put(key, value);
            cacheConfMap.put(key, ccm);
            System.out.println("增加缓存结束－－－－－－－－－－－－－");
            System.out.println("now addcache==" + cacheMap.size());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 获取缓存实体
     */
    public Object getValue(String key) {
        Object ob = cacheMap.get(key);
        if (ob != null) {
            return ob;
        } else {
            return null;
        }
    }

    /**
     * 获取缓存数据的数量
     *
     * @return
     */
    public int getSize() {
        return cacheMap.size();
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean removeCache(Object key) {
        boolean flag = false;
        try {
            cacheMap.remove(key);
            cacheConfMap.remove(key);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 清除缓存的类 继承Thread线程类
     */
    private static class ClearCache extends Thread {
        @Override
        public void run() {
            while (true) {
                Set tempSet = new HashSet();
                Set set = cacheConfMap.keySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    CacheConfModel ccm = (CacheConfModel) cacheConfMap.get(key);
                    // 比较是否需要清除
                    if (!ccm.isForever()) {
                        if ((System.currentTimeMillis() - ccm.getBeginTime()) >= ccm
                                .getDurableTime() * 60 * 1000) {
                            // 可以清除，先记录下来
                            tempSet.add(key);
                        }
                    }
                }
                // 真正清除
                Iterator tempIt = tempSet.iterator();
                while (tempIt.hasNext()) {
                    Object key = tempIt.next();
                    cacheMap.remove(key);
                    cacheConfMap.remove(key);

                }
                System.out.println("now thread================>"
                        + cacheMap.size());
                // 休息
                try {
                    Thread.sleep(60 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
