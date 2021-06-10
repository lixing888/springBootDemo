package com.springboot.demo.util.CreateExcelUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C), 2018-2021
 * FileName: CreateExcelUtil
 * Author:   李兴
 * Date:     2021/6/8 17:18
 * Description: 将List集合对象生成Excel数据表格
 */
public class CreateExcelUtil {

    /**
     * 将list集合转成Excel文件
     *
     * @param list     对象集合
     * @param path     输出路径
     * @param fileName 文件名称
     * @return 返回文件路径
     **/
    public static String createExcel(List<? extends Object> list, String path, String fileName) {
        String result = "";
        if (list.size() == 0 || list == null) {
            result = "没有对象信息";
        } else {
            Object o = list.get(0);
            Class<? extends Object> clazz = o.getClass();
            String className = clazz.getSimpleName();
            Field[] fields = clazz.getDeclaredFields();    //这里通过反射获取字段数组
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

//            String fileName = FORMATTER.format(new Date());
//            String fileName ="2019-19-14";
            String name = fileName.concat(".xls");
            WritableWorkbook book = null;
            File file = null;
            try {
                file = new File(path.concat(File.separator).concat(name));
                book = Workbook.createWorkbook(file);  //创建xls文件
                WritableSheet sheet = book.createSheet(className, 0);
                int i = 0;  //列
                int j = 0;  //行
                for (Field f : fields) {
                    j = 0;
                    Label label = new Label(i, j, f.getName());   //这里把字段名称写入excel第一行中
                    sheet.addCell(label);
                    j = 1;
                    for (Object obj : list) {
                        Object temp = getFieldValueByName(f.getName(), obj);
                        String strTemp = "";
                        if (temp != null) {
                            strTemp = temp.toString();
                        }
                        sheet.addCell(new Label(i, j, strTemp));  //把每个对象此字段的属性写入这一列excel中
                        j++;
                    }
                    i++;
                }
                book.write();
                result = file.getPath();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                result = "SystemException";
                e.printStackTrace();
            } finally {
                fileName = null;
                name = null;
                folder = null;
                file = null;
                if (book != null) {
                    try {
                        book.close();
                    } catch (WriteException e) {
                        // TODO Auto-generated catch block
                        result = "WriteException";
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        result = "IOException";
                        e.printStackTrace();
                    }
                }
            }

        }

        return result;   //最后输出文件路径
    }

    /**
     * 获取属性值
     *
     * @param fieldName 字段名称
     * @param o         对象
     * @return Object
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);    //获取方法名
            Method method = o.getClass().getMethod(getter, new Class[]{});  //获取方法对象
            Object value = method.invoke(o, new Object[]{});    //用invoke调用此对象的get字段方法
            return value;  //返回值
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        List<Taxi> taxis = new LinkedList<Taxi>();
        for (int i = 0; i < 1000; i++) {
            Taxi taxi = new Taxi();
            taxi.setId(i);
            taxi.setWaittime((int) (1 + Math.random() * (100 - 1 + 1)));
            taxi.setEarntime((int) (1 + Math.random() * (100 - 1 + 1)));
            int hour = (int) (1 + Math.random() * (6 - 1 + 1)) + 8;
            int min = (int) (1 + Math.random() * (59 - 1 + 1));
            taxi.setStarttime(hour * 60 + min);
            String starttime = hour + ":" + min;
            taxi.setStime(starttime);
            taxis.add(taxi);
        }
        System.out.println("下载路径:" + CreateExcelUtil.createExcel(taxis, "D:\\", "出租车按照进入机场时间排序前数据"));

    }
}
