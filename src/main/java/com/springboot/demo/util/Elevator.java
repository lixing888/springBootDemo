package com.springboot.demo.util;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 模拟电梯
 *
 * @author lixing
 */
public class Elevator {
    public static final Integer height = 26;
    public static final Integer low = 0;
    //电梯开启状态.需用volatile保持可见性
    public static volatile boolean isUse;
    //当前楼层
    public static Integer current = 0;
    //临时楼层去重复开门用
    public static Integer tempCurrent;

    //上队列
    public static PriorityBlockingQueue<Order> up = new PriorityBlockingQueue<Order>(height * 3, new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.getHeight() - o2.getHeight();
        }
    });

    //下队列
    public static PriorityBlockingQueue<Order> down = new PriorityBlockingQueue<Order>(height * 3, new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
            return o2.getHeight() - o1.getHeight();
        }
    });

    private Elevator() {
    }

    public static Elevator getInstance() {
        return Instance.elevator;
    }

    /**
     * 电梯开闸放电使其运行
     */
    public void startRun() {
        this.isUse = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    excutor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 电梯关闭
     */
    public void stopRun() {
        this.isUse = false;
    }

    /**
     * 电梯私有运转方法
     */
    private void excutor() throws Exception {
        boolean isUp = false;
        while (isUse) {
            if (up.isEmpty() && down.isEmpty()) {
                Thread.sleep(1000);//这里先手动阻塞，阻塞队列的作用没体现出来。不知道两个阻塞队列如何实现一个非空则唤醒。
            }
            if ((current.intValue() == low.intValue()) || !up.isEmpty()) { //默认先上
                isUp = true;
            }
            if ((current.intValue() == height.intValue()) || (up.isEmpty() && !down.isEmpty())) {
                isUp = false;
            }

            if (isUp && !up.isEmpty()) {
                open(up, isUp, down);
                Thread.sleep(500);
            } else {
                isUp = false;
            }

            if (!isUp && !down.isEmpty()) {
                open(down, isUp, up);
                Thread.sleep(500);
            } else {
                isUp = true;
            }
        }
    }

    /**
     * 电梯开门
     */
    private void open(Queue<Order> queue0, Boolean isUp, Queue<Order> queue) {
        Order order = queue0.poll();
        if (order == null) {
            return;
        }
        if (order.getToUp() == null || order.getToUp() == isUp) { //允许开门的条件
            current = order.getHeight();
            if (tempCurrent != current) {
                tempCurrent = current;
                System.out.println("电梯正往" + (isUp ? "上" : "下") + "走，到" + current + "层了！");
            }
            open(queue0, isUp, queue);
            return;
        }
        //不允许开门则将其添加到另一个队列中
        add(queue, order);
    }

    /**
     * 往队列中添加指令
     */
    private void add(Queue<Order> queue, Order order) {
        Object[] o = queue.toArray();
        for (Object object : o) {//判断队列中是否有相同的指令，有则不添加
            Order oo = (Order) object;
            if (oo.getToUp() == order.getToUp() && oo.getHeight() == order.getHeight()) {
                return;
            }
        }
        queue.add(order);
    }

    /**
     * 人类按电梯
     */
    public void toGo(Integer height, Boolean isUp) {
        if (current > height) { //往下的队列中添加
            add(down, new Order(height, isUp));
        } else { //往上的队列中添加
            add(up, new Order(height, isUp));
        }
    }

    static final class Instance {
        static Elevator elevator = new Elevator();
    }

//=================神奇分割线：指令类================

    /**
     * 电梯运行的指令
     */
    class Order {
        private Integer height; //从几层叫的电梯
        private Boolean isToUp; //叫电梯的时候按的上还是下按钮

        public Order(Integer height, Boolean isToUp) {
            this.height = height;
            this.isToUp = isToUp;
        }

        public Integer getHeight() {
            return height;
        }

        public Boolean getToUp() {
            return isToUp;
        }
    }

}

/**
 * 神奇分割线 : 测试类
 */
class People {
    public static void main(String[] args) throws Exception {
        Elevator elevator = Elevator.getInstance();

        elevator.startRun();
        elevator.toGo(3, true);
        elevator.toGo(1, null);

        elevator.toGo(4, false);
        elevator.toGo(5, true);

        elevator.toGo(7, false);
        elevator.toGo(6, true);
        elevator.toGo(8, false);

        elevator.toGo(10, false);
        elevator.toGo(9, true);

        elevator.toGo(10, true);
        elevator.toGo(10, null);

//        Thread.sleep(3000); //3秒后断电。
//        elevator.stopRun();

        elevator.toGo(26, null);
    }
}
