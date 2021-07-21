package com.springboot.demo.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

/**
 * Copyright (C), 2018-2021
 * FileName: MyXMLReader2DOM
 * Author:   ZSB
 * Date:     2021/7/19 18:31
 * Description: DOM 实现方法
 */
public class MyXMLReader2DOM {
    public static void main(String arge[]) {

        long lasting = System.currentTimeMillis();
//创建DocumentBuilderFactory对象
        DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();
        try {
            //创建DocumentBuilder对象
            DocumentBuilder b = a.newDocumentBuilder();
            //通过DocumentBuilder对象的parse方法返回一个Document对象
            Document document = b.parse("D://demo.xml");
            //通过Document对象的getElementsByTagName()返根节点的一个list集合
            NodeList nl = document.getElementsByTagName("Row");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
