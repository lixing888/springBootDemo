package com.springboot.demo.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameDemo2 {
    static JTextField field1 = new JTextField(5);
    static JTextField field2 = new JTextField(5);
    static JTextField field3 = new JTextField("0", 5);

    public FrameDemo2() {
        JFrame f = new JFrame("加法器");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(220, 100);
        f.setLocation(500, 300);
        JLabel jb = new JLabel("+");
        f.setVisible(true);
        JPanel p1 = new JPanel();
        f.setContentPane(p1);
        p1.setLayout(new FlowLayout());

        p1.add(field1);
        p1.add(jb);
        p1.add(field2);
        p1.add(field3);
        JButton b1 = new JButton("计算");
        Color bg = new Color(255, 255, 255);
        b1.setBackground(bg);
        p1.add(b1);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)    //重写actionPerformed方法
            {
                String a, b;
                a = field1.getText(); //获取TextField1中的数据
                b = field2.getText();
                field3.setText(MD5Util.md5Encoder(a + b));

            }
        }
        );
    }

    public static void main(String args[]) {
        FrameDemo2 fd = new FrameDemo2();
    }
}

