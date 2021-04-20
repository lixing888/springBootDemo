package com.springboot.demo.esensoft;

import java.util.concurrent.ConcurrentHashMap;

public class MyThread extends Thread {
    private Message message;

    public MyThread(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start");
        int i = 0;
        while (i++ < 5) {
            message.addMessage();
            message.removeMessage();
            message.updateMessage();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " end");
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> messageMap = new ConcurrentHashMap<String, String>();
        Message message = new Message(messageMap);
        Thread t1 = new MyThread(message);
        t1.start();
        message.iteratorMessage();
        //Thread t2 = new MyThread(message);
    }

}
