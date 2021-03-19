package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @program: springBootDemo
 * @description: 写文件工具类
 * @author: lixing
 * @create: 2021-01-12 21:22
 **/
@Slf4j
public class WriteFile {

    public static void xie(String filePath, String fileBase64) {
        // TODO Auto-generated method stub
        File file = new File(filePath);

        try {
            // 创建文件字节输出流对象，准备向d.txt文件中写出数据,true表示在原有的基础上增加内容
            FileOutputStream fout = new FileOutputStream(file, true);
            Scanner sc = new Scanner(System.in);
            String msg = fileBase64;
            /****************** (方法一)按字节数组写入 **********************/
            // byte[] bytes = msg.getBytes();//msg.getBytes()将字符串转为字节数组

            // fout.write(bytes);//使用字节数组输出到文件
            /****************** (方法一)逐字节写入 **********************/
            byte[] bytes = msg.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                // 逐字节写文件
                fout.write(bytes[i]);
            }
            fout.flush();// 强制刷新输出流
            fout.close();// 关闭输出流
            log.info("写入完成！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        xie("F:\\xie\\" + String.valueOf(System.currentTimeMillis() + "" + (Math.random() * 10)), "result.toString()");
    }
}
