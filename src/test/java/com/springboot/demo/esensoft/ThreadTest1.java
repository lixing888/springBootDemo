package com.springboot.demo.esensoft;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 使用Condition实现等待
 * */
class MyCondition implements Runnable {
    private Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void run() {
        try {
            //上锁
            lock.lock();
            System.out.println("当前线程名:" + Thread.currentThread().getName() + " 开始等待时间：" + DateUtil.now());
            //线程等待
            condition.await();
            System.out.println("我陷入了等待...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
            System.out.println("锁释放了!");
        }
    }
}

public class ThreadTest1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCondition myCondition = new MyCondition();
        Thread thread1 = new Thread(myCondition, "线程1");
        thread1.start();

    }

}
