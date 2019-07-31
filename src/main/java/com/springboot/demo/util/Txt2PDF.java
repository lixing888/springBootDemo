package com.springboot.demo.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * txt转pdf文件
 */
public class Txt2PDF {
    private static Txt2PDF parser = null;

    private Txt2PDF() {
    }

    public static synchronized Txt2PDF getInstance() {
        if (parser == null) {
            parser = new Txt2PDF();
        }
        return parser;
    }

    public static void main(String[] args) throws Exception {
        java.io.File file = new java.io.File(Txt2PDF.class.getResource("/").getPath());
        String path = file.getParentFile().getPath();
        System.out.println("路径：" + path);
        path = java.net.URLDecoder.decode(path, "UTF-8");
        String text = path + "/mi.txt";
        String pdf = path + "/mi.txt.pdf";
        Txt2PDF p = Txt2PDF.getInstance();
        p.text2pdf(text, pdf);
    }

    public void text2pdf(String text, String pdf) throws DocumentException, IOException {
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        FileOutputStream out = new FileOutputStream(pdf);
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        Document doc = new Document(rect);
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        doc.open();
        Paragraph p = new Paragraph();
        p.setFont(FontChinese);
        BufferedReader read = new BufferedReader(new FileReader(text));
        String line = read.readLine();
        while (line != null) {
            System.out.println(line);
            p.add(line + "\n");
            line = read.readLine();
        }
        read.close();
        doc.add(p);
        doc.close();
    }
}
