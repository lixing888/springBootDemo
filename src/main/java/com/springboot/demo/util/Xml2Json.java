package com.springboot.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;

/**
 * @program: springBootDemo
 * @description: xml转json
 * @author: lixing
 * @create: 2020-12-07 15:54
 **/
public class Xml2Json {
    public static void main(String[] args) throws Exception {
//        String xmlStr = readFile("D:/demo.xml");
//        readStringXml(xmlStr);
//        Document doc = DocumentHelper.parseText(xmlStr);
//        JSONObject json = new JSONObject();
//        dom4j2Json(doc.getRootElement(), json);
//        System.out.println("xml2Json:" + json.toJSONString());

        String xmlStr = readFile("D:/demo.xml");
        Document document = DocumentHelper.parseText(xmlStr);
//        JSONObject json = new JSONObject();
//        dom4j2Json(doc.getRootElement(), json);
//        System.out.println("xml2Json:" + json.toJSONString());
        // 创建xml解析对象
        SAXReader reader = new SAXReader();
        // 得到xml的根节点(message)
        Element root = document.getRootElement();
        //定义子循环体的变量
        Element ticket = null;

        Iterator iterator = null;
        for (iterator = root.element("YKFP").elementIterator(); iterator.hasNext(); ) {
            ticket = (Element) iterator.next();
            System.out.print("原发票号码" + ticket.attributeValue("原发票号码") + "  ");
            System.out.print("价税合计" + ticket.attributeValue("价税合计") + "  ");
            System.out.print("客户识别号" + ticket.attributeValue("客户识别号"));
            System.out.print("发票状态" + ticket.attributeValue("发票状态") + "  ");
            System.out.print("票信息表编号" + ticket.attributeValue("票信息表编号") + "  ");
            System.out.print("作废人" + ticket.attributeValue("作废人"));

            System.out.print("发票代码" + ticket.attributeValue("发票代码") + "  ");
            System.out.print("合计金额" + ticket.attributeValue("合计金额") + "  ");
            System.out.print("税额" + ticket.attributeValue("税额"));
            System.out.print("清单标识" + ticket.attributeValue("清单标识") + "  ");
            System.out.print("开票日期" + ticket.attributeValue("开票日期") + "  ");
            System.out.print("开票人" + ticket.attributeValue("开票人"));

            System.out.print("作废日期" + ticket.attributeValue("作废日期") + "  ");
            System.out.print("原发票代码" + ticket.attributeValue("原发票代码") + "  ");
            System.out.print("上传状态" + ticket.attributeValue("上传状态"));
            System.out.print("发票号码" + ticket.attributeValue("发票号码") + "  ");
            System.out.print("发票类型" + ticket.attributeValue("发票类型") + "  ");
            System.out.print("开票人" + ticket.attributeValue("开票人"));
            System.out.println("客户名称" + ticket.attributeValue("客户名称"));

        }
    }

    public static String readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
        //fc向buffer中读入数据
        fc.read(bb);
        bb.flip();
        String str = new String(bb.array(), "GB2312");
        fc.close();
        fis.close();
        return str;

    }

    /**
     * xml转json
     *
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     *
     * @param element
     * @param json
     */
    public static void dom4j2Json(Element element, JSONObject json) {
        //如果是属性
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute) o;
            if (!isEmpty(attr.getValue())) {
                json.put("@" + attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        //如果没有子元素,只有一个值
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {
            json.put(element.getName(), element.getText());
        }
        //有子元素
        for (Element e : chdEl) {
            //子元素也有子元素
            if (!e.elements().isEmpty()) {
                JSONObject chdjson = new JSONObject();
                dom4j2Json(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    //如果此元素已存在,则转为jsonArray
                    if (o instanceof JSONObject) {
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    if (!chdjson.isEmpty()) {
                        json.put(e.getName(), chdjson);
                    }
                }


            } else {//子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {

        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static void readStringXml(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            Iterator iter = rootElt.elementIterator("YKFP"); // 获取根节点下的子节点head
            // 遍历head节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                String title = recordEle.elementTextTrim("Row"); // 拿到head节点下的子节点title值
                System.out.println("客户识别号:" + title);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
