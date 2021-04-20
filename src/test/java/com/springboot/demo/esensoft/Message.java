package com.springboot.demo.esensoft;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Message {
    private ConcurrentHashMap<String, String> messageMap;

    public Message(ConcurrentHashMap messageMap) {
        messageMap.put("1", "1");
        messageMap.put("2", "2");
        messageMap.put("3", "3");
        this.messageMap = messageMap;
    }

    public void addMessage() {
        messageMap.put("4", "4");
        System.out.println("add message");
    }

    public void removeMessage() {
        messageMap.remove("1");
        System.out.println("remove message");
    }

    public void updateMessage() {
        messageMap.put("2", "22");
        System.out.println("update message");
    }

    public void iteratorMessage() {
        Iterator<Map.Entry<String, String>> iterator = messageMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("遍历key为" + entry.getKey() + "value为" + entry.getValue());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
