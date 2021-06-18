package com.springboot.demo.util;
import java.awt.Color;

import com.itextpdf.text.*;

/**
 * Copyright (C), 2018-2021
 * FileName: DocStyleUtils
 * Author:   ZSB
 * Date:     2021/6/17 15:22
 * Description: java使用itext导出word文档常用方法
 */
public class DocStyleUtils {
    public static Font setFontStyle(String family , float size , int style){
        return setFontStyle(family, BaseColor.BLACK, size, style);
    }


    public static Font setFontStyle(String family , BaseColor color , float size , int style){
        Font font = new Font();
        font.setFamily(family);
        font.setColor(color);
        font.setSize(size);
        font.setStyle(style);
        return font;
    }


    private static Phrase setPhraseStyle(String content , String appendStr){
        Chunk chunk = new Chunk(content);
        //填充的背景颜色为浅灰色
        chunk.setBackground(BaseColor.LIGHT_GRAY);
        Phrase phrase = new Phrase(chunk);
        phrase.add(appendStr);
        return phrase;
    }


    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , String appendStr){
        Paragraph par = setParagraphStyle(content, font, 0f, 12f);
        Phrase phrase = new Phrase();
        phrase.add(par);
        phrase.add(appendStr);
        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setFirstLineIndent(firstLineIndent);
        //设置对齐方式为两端对齐
        paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
        return paragraph;
    }


    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , String appendStr){
        Phrase phrase = setPhraseStyle(content , appendStr);
        Paragraph par = new Paragraph(phrase);
        par.setFont(font);
        par.setFirstLineIndent(firstLineIndent);
        par.setLeading(leading);
        //设置对齐方式为两端对齐
        par.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
        return par;
    }


    public static Paragraph setParagraphStyle(String content , Font font , float leading , int alignment){
        return setParagraphStyle(content, font, 0f, leading, 0f, alignment);
    }


    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading){
        return setParagraphStyle(content, font, firstLineIndent, leading, 0.6f, Paragraph.ALIGN_JUSTIFIED_ALL);
    }


    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , float indentationRight , int alignment){
        Paragraph par = new Paragraph(content, font);
        par.setFirstLineIndent(firstLineIndent);
        par.setLeading(leading);
        par.setIndentationRight(indentationRight);
        par.setAlignment(alignment);
        return par;
    }

}
