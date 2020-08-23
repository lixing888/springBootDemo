package com.springboot.demo.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.*;

public class YasuoPicUtil {

    /**
     * 压缩上传图片
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        System.out.println("开始：" + new Date().toString());
        System.out.println(YasuoPicUtil.zipWidthHeightImageFile(new File("D:\\bizhi\\bjjd.jpg"), new File("C:\\Users\\Administrator\\Desktop\\DSC_0009New.JPG"), 600, 400, 3f));
        System.out.println("结束：" + new Date().toString());
    }


    /**
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件
     * @param newFile 新文件
     * @param width   宽度 //设置宽度时（高度传入0，等比例缩放）
     * @param height  高度 //设置高度时（宽度传入0，等比例缩放）
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    @SuppressWarnings("restriction")
    public static String zipImageFile(File oldFile, File newFile, int width, int height,
                                      float quality) {
        if (oldFile == null) {
            return null;
        }
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            //  System.out.println(w);
            int h = srcFile.getHeight(null);
            //  System.out.println(h);
            double bili;
            if (width > 0) {
                bili = width / (double) w;
                height = (int) (h * bili);
            } else {
                if (height > 0) {
                    bili = height / (double) h;
                    width = (int) (w * bili);
                }
            }
            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.'));  
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length());  

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

    /**
     * 按宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件全路径
     * @param newFile 新文件
     * @param width   宽度
     * @param height  高度
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipWidthHeightImageFile(File oldFile, File newFile, int width, int height,
                                                 float quality) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            //  System.out.println(w);
            int h = srcFile.getHeight(null);
            //  System.out.println(h);

            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));  
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());  

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
     * 按宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件全路径
     * @param newFile 新文件
     * @param width   宽度
     * @param height  高度
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipWidthHeightImageFile(File oldFile, File newFile, int size,
                                                 float quality) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            //  System.out.println(w);
            int h = srcFile.getHeight(null);
            //  System.out.println(h);
            int width = 0;
            int height = 0;
            if (w * h < size) {
                width = w;
                height = h;
            } else {
                int ration = 99;
                while ((w * ration / 100) * (h * ration / 100) > size) {
                    ration--;
                }

                width = w * ration / 100;
                height = h * ration / 100;
            }


            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));  
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());  

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }


}



