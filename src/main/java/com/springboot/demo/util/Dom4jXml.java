package com.springboot.demo.util;

import java.io.*;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Dom4jXml {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        //createXml();
        createApplicationConfigXML();
        // 执行dom4j生成xml方法
        createDom4j(new File("F:\\dom4j.xml"));
        System.out.println("运行时间：" + (System.currentTimeMillis() - start));
    }


    /**
     * 生成xml方法
     */
    public static void createXml() {
        try {
            // 1、创建document对象
            Document document = DocumentHelper.createDocument();
            // 2、创建根节点rss
            Element rss = document.addElement("rss");
            // 3、向rss节点添加version属性
            rss.addAttribute("version", "2.0");
            // 4、生成子节点及子节点内容
            Element channel = rss.addElement("channel");
            Element title = channel.addElement("title");
            title.setText("国内最新新闻");
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");
            // 6、生成xml文件
            File file = new File("rss.xml");
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成rss.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成rss.xml失败");
        }
    }

    /**
     *
     */
    public static void createApplicationConfigXML() {
        //建立document对象
        try {
            Document document = DocumentHelper.createDocument();
            //添加文档根
            Element root = document.addElement("root");
            //加入一行注释
            root.addComment("这一行是注释");
            //创建一个root的子节点
            //添加root的子节点
            Element request = root.addElement("website");
            request.addAttribute("databases", "testXml");
            request.addAttribute("table", "website");
            request.addAttribute("time", "2015");

            Element pro = request.addElement("property");
            pro.addAttribute("id", "用户的id");
            pro.addAttribute("name", "用户的名称");
            pro.addAttribute("pwd", "用户的密码");
            pro.addText("此项在中间显示备注信息");

            //输出全部原始数据，在编译器中显示
            OutputFormat format = OutputFormat.createPrettyPrint();
            //根据需要设置编码
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(format);
            document.normalize();
            writer.write(document);
            writer.close();
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            XMLWriter writer2 = new XMLWriter(new FileWriter(new File(
                    "F://test.xml")), format);
            //输出到文件
            writer2.write(document);
            writer2.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 生成xml文件
     *
     * @param file
     */
    public static void createDom4j(File file) {
        try {
            // 创建一个Document实例
            Document doc = DocumentHelper.createDocument();

            // 添加根节点
            Element root = doc.addElement("root");

            // 在根节点下添加第一个子节点
            Element oneChildElement = root.addElement("person").addAttribute("attr", "root noe");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person one child one");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person one child two");
            Element oneGrandSonElement = oneChildElement.addElement("personGrand").addAttribute("attr", "root noe");
            oneGrandSonElement.addElement("personGrandSon1")
                    .addAttribute("attr", "child one")
                    .addText("person one personGrand one");
            oneGrandSonElement.addElement("personGrandSon2")
                    .addAttribute("attr", "personGrand two")
                    .addText("person one personGrand two");


            // 在根节点下添加第一个子节点
            Element twoChildElement = root.addElement("person").addAttribute("attr", "root two");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person two child one");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person two child two");

            // xml格式化样式
            // OutputFormat format = OutputFormat.createPrettyPrint(); // 默认样式

            // 自定义xml样式
            OutputFormat format = new OutputFormat();
            // 行缩进
            format.setIndentSize(2);
            // 一个结点为一行
            format.setNewlines(true);
            // 去重空格
            format.setTrimText(true);
            format.setPadText(true);
            // 放置xml文件中第二行为空白行
            format.setNewLineAfterDeclaration(false);

            // 输出xml文件
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(doc);
            System.out.println("dom4j CreateDom4j success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
