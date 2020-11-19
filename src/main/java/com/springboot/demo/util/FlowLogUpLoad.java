package com.springboot.demo.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @program: springBootDemo
 * @description: 复制某一文件夹下的所有文件到另一个文件夹下
 * @author: lixing
 * @create: 2020-11-19 19:55
 **/
public class FlowLogUpLoad {

    public static void copyDir(String sourcePath, String newPath) throws IOException {
        //获取文件夹File对象
        File file = new File(sourcePath);
        //获取文件夹下所有内容的名称
        String[] filePath = file.list();
        //判断要目标文件夹是否存在不存在则创建
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        //循环遍历
        for (int i = 0; i < filePath.length; i++) {
            //判断是不是文件夹，是的话执行递归。file.separator 分隔符，如“/”
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }
            //判断是不是文件，是的话旧的文件拷至新的文件夹下
            if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        //获取旧的文件File对象
        File oldFile = new File(oldPath);
        //获取新的文件File对象并生成文件
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[2097152];
        int readByte = 0;
        //读取旧文件的流写入新文件里
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            //解决文件目录有中文时的解压错误!!!
            zipFile = new ZipFile(srcFile, Charset.forName("gbk"));
            //zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //启用输入
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入源目录：");
//        String sourcePath = sc.nextLine();
//        System.out.println("请输入新目录：");
//        String path = sc.nextLine();

        //解压zip到制定文件夹
        String zipPath = "D:/home/before/bizhi.zip";
        String destDirPath = "D:/home/before/";
        String sourcePath = "D:/home/before/bizhi";
        String path = "F:/home/after/bizhi";
        unZip(new File(zipPath), destDirPath);
        copyDir(sourcePath, path);
    }

}
