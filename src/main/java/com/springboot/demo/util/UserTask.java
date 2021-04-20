package com.springboot.demo.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 队列
 *
 * @author lixing
 */
public class UserTask {

    //队列大小
    private final int QUEUE_LENGTH = 10000 * 10;
    //基于内存的阻塞队列
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(QUEUE_LENGTH);
    //创建计划任务执行器
    private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

    /**
     * 构造函数，执行execute方法
     */
    public UserTask() {
        execute();
    }

    /**
     * 添加信息至队列中
     *
     * @param content
     */
    public void addQueue(String content) {
        queue.add(content);
    }

    /**
     * 初始化执行
     */
    public void execute() {
        //每一分钟执行一次
        es.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    //take()方法取出并删除队头的元素,当队列为空,则会一直等待直到队列有新元素可以取出,或者线程被中断抛出异常
                    String content = queue.take();
                    //处理队列中的信息。。。。。
                    System.out.println(content);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1, TimeUnit.MINUTES);
    }

}
