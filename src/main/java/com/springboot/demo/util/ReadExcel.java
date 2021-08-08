package com.springboot.demo.util;


import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Copyright (C), 2018-2021
 * FileName: ReadExcel
 * Author:   ZSB
 * Date:     2021/8/2 15:12
 * Description: 读取execl文件
 */
public class ReadExcel {
    public static void main(String[] args) {
        try {
            //选取指定的excel
            Workbook workbook = Workbook.getWorkbook(new File("D:\\test.xlsx"));
            //选取制定的sheet
            Sheet sheet = workbook.getSheet(0);
            //选取指定的cell
            //遍历循环得到所要的cell值
            for (int j = 0; j < sheet.getRows(); j++) {
                for (int i = 0; i < sheet.getColumns(); i++) {
                    Cell cell = sheet.getCell(i, j);
                    //获取该cell的值
                    String var1 = cell.getContents();
                    //打印输出该值
                    System.out.println(var1);
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
