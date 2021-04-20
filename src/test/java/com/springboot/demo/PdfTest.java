package com.springboot.demo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName TestPdf
 * @Description 生成hello world
 * @Author 戴某某
 * @Date 2019/11/19 20:29
 * @Version 1.0
 **/
@Slf4j
public class PdfTest {

    private static final String FILE_NAME = "F:\\pdf\\helloPdf.pdf";

    public static void main(String[] args) throws IOException {
        PdfTest pdf = new PdfTest();
        pdf.createPdf(FILE_NAME);
        log.info("打印完成");
    }

    /**
     * 生成一个hello world
     */
    public void createPdf(String filename)throws IOException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            document.add(new Paragraph("Hello World!"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

