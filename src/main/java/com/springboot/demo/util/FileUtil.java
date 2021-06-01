package com.springboot.demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileUtil {
    /**
     * 把某个文件路径下面的文件包含文件夹压缩到一个文件下
     *
     * @param file
     * @param rootPath        相对地址
     * @param zipoutputStream
     */
    public static void zipFileFun(File file, String rootPath, ZipOutputStream zipoutputStream) {
        //文件存在才合法
        if (file.exists()) {
            if (file.isFile()) {
                //定义相关操作流
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    //设置文件夹
                    String relativeFilePath = file.getPath().replace(rootPath + File.separator, "");
                    //创建输入流读取文件
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis, 10 * 1024);
                    //将文件装入zip中，开始打包
                    ZipEntry zipEntry;
                    if (!relativeFilePath.contains("\\")) {
                        zipEntry = new ZipEntry(file.getName());//此处值不能重复，要唯一，否则同名文件会报错
                    } else {
                        zipEntry = new ZipEntry(relativeFilePath);//此处值不能重复，要唯一，否则同名文件会报错
                    }
                    zipoutputStream.putNextEntry(zipEntry);
                    //开始写文件
                    byte[] b = new byte[10 * 1024];
                    int size = 0;
                    while ((size = bis.read(b, 0, 10 * 1024)) != -1) {//没有读完
                        zipoutputStream.write(b, 0, size);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //读完以后关闭相关流操作
                        if (bis != null) {
                            bis.close();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (Exception e2) {
                        System.out.println("流关闭错误！");
                    }
                }
            }
//            else{//如果是文件夹
//                try {
//                    File [] files = file.listFiles();//获取文件夹下的所有文件
//                    for(File f : files){
//                        zipFileFun(f,rootPath, zipoutputStream);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
        }
    }

    /*
     * 获取某个文件夹下的所有文件
     */
    public static Vector<File> getPathAllFiles(File file, Vector<File> vector) {
        if (file.isFile()) {//如果是文件，直接装载
            System.out.println("在迭代函数中文件" + file.getName() + "大小为：" + file.length());
            vector.add(file);
        } else {//文件夹
            File[] files = file.listFiles();
            for (File f : files) {//递归
                if (f.isDirectory()) {
                    getPathAllFiles(f, vector);
                } else {
                    System.out.println("在迭代函数中文件" + f.getName() + "大小为：" + f.length());
                    vector.add(f);
                }
            }
        }
        return vector;
    }

    /**
     * 压缩文件到指定文件夹
     *
     * @param sourceFilePath 源地址
     * @param destinFilePath 目的地址
     */
    public static String zipFiles(String sourceFilePath, String destinFilePath) {
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = dateFormat.format(new Date()) + ".zip";
        String zipFilePath = destinFilePath + File.separator + fileName;
        if (sourceFile.exists() == false) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath);
                if (zipFile.exists()) {
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    } else {
                        //读取sourceFile下的所有文件
                        Vector<File> vector = FileUtil.getPathAllFiles(sourceFile, new Vector<File>());
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];

                        for (int i = 0; i < vector.size(); i++) {
                            // 创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(vector.get(i).getName());//文件不可重名，否则会报错
                            zos.putNextEntry(zipEntry);
                            // 读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(vector.get(i));
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // 关闭流
                try {
                    if (null != bis)
                        bis.close();
                    if (null != zos)
                        zos.closeEntry();
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return fileName;
    }

}
