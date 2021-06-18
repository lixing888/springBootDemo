package com.springboot.demo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * Copyright (C), 2018-2021
 * FileName: DocumentExample
 * Author:   李兴
 * Date:     2021/6/17 15:15
 * Description: 设置完属性的段落
 */

public class DocumentExample {
    public static void main(String[] args) {
        //创建文本
        Document document = new Document();
        try {
            //写入文本到文件中
            PdfWriter.getInstance(document, new FileOutputStream("Paragraph.pdf"));
            //打开文本
            document.open();
            //定义段落
            Paragraph paragraph = new Paragraph();
            //设置段落前后间距
            paragraph.setSpacingAfter(25);
            paragraph.setSpacingBefore(25);
            //设置段段落居中
            paragraph.setAlignment(Element.ALIGN_CENTER);
            //设置缩进
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);

            //插入十条文本块到段落中
            int i = 0;
            for (i = 0; i < 10; i++) {
                Chunk chunk = new Chunk("This is a sentence which is long " + i + ". ");
                paragraph.add(chunk);
            }
            //添加段落
            document.add(paragraph);
            //关闭文本
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
