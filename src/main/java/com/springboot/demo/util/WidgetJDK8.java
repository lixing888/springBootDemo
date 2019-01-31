package com.springboot.demo.util;

import java.util.ArrayList;
import java.util.List;

public class WidgetJDK8 {

    private final Color color;
    private final int weight;
    enum Color {RED, BLACK, BLUE}

    public WidgetJDK8(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Color getColor() {return color;}
    public int getWeight() {return weight;}

    public static void main(String[] args) {
        List<WidgetJDK8> widgets = new ArrayList<>();
        widgets.add(new WidgetJDK8(Color.RED, 7));
        widgets.add(new WidgetJDK8(Color.RED, 6));
        widgets.add(new WidgetJDK8(Color.BLACK, 3));
        widgets.add(new WidgetJDK8(Color.BLUE, 4));
        // stream() 获取当前的source, filter 和 mapToInt为intermediate操作, 进行数据筛选和转换,
        // 最后一个sum为terminal操作，对符合条件的全部widget做重量求和
        int sum = widgets.stream()
                .filter(w -> w.getColor() == Color.RED)
                .mapToInt(w -> w.getWeight())
                .sum();
        System.out.println(sum);// 13
    }

}
