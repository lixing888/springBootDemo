package com.springboot.demo.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.zip.ZipOutputStream;

/**
 * 下载
 * @author lixing
 */
public class DownloadUtils {
    /**
     * 下载文件
     *
     * @param response
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */

//    @GetMapping("/download")
    public static void downloadLocal(String filePath, HttpServletResponse response) throws IOException {
//        String filePath = SysConfigCache.getString("file_url_z")+Path;
        File file = new File(filePath);
        if (!file.exists()) {
//            response.writeHead(200,{'Content-Type':'text/html'});//注意这里的html，如果为plain则不会显示html，会输出.html文件中的字符串
//            response.write(body);
//            response.end();
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("<div>文件路径不存在</div>");
            response.getWriter().println("<script>" +
                    "alert('文件路径不存在');" +
                    "location.reload()" +
                    "</script>");
            response.getWriter().close();
            return;

        }
        if (file.isDirectory()) {
            zipDownloadRelativePath(response, filePath);
        } else {
            downloadFile(response, filePath);
        }
    }

    /**sss
     * 下载文件
     *
     * @param response
     * @param filePath
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void downloadFile(HttpServletResponse response, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        // 下载本地文件
//        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
//        InputStream inStream = new FileInputStream(SysConfigCache.getString("file_url_z")+filePath);// 文件的存放路径
        // 文件的存放路径
        InputStream inStream = new FileInputStream(filePath);
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件夹
     *
     * @param response
     * @param filePath
     */
    public static void zipDownloadRelativePath(HttpServletResponse response, String filePath) throws UnsupportedEncodingException {
        String msg = "";//下载提示信息
//        String root = request.getSession().getServletContext().getRealPath("/convert");//文件路径
//        String root = "D:\\file\\武功秘籍";//文件路径
//        String root = "D:\\Downloads\\20200819190451\\九阴真经.pdf";//文件路径
//        String root = SysConfigCache.get("file_url_z")+filePath;
        String root = filePath;
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1) + ".zip";
        Vector<File> fileVector = new Vector<File>();//定义容器装载文件
        File file = new File(root);
        File[] subFile = file.listFiles();
        //判断文件性质并取文件
        for (int i = 0; i < subFile.length; i++) {
            if (!subFile[i].isDirectory()) {//不是文件夹并且不为空
                fileVector.add(subFile[i]);//装载文件
            } else {//是文件夹,再次递归遍历文件夹里面的文件
                File[] files = subFile[i].listFiles();
                Vector v = FileUtil.getPathAllFiles(subFile[i], new Vector<File>());
                fileVector.addAll(v);//把迭代的文件装到容器中
            }
        }
        //设置文件下载名称
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        String fileName = dateFormat.format(new Date())+".zip";
        //如果有中文需要转码
        response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
        //定义相关流
        //用于浏览器下载响应
        OutputStream out = null;
        //用于读压缩好的文件
        BufferedInputStream bis = null;//用缓存性能会好一些
        //用于压缩文件
        ZipOutputStream zos = null;
        //创建一个空的zip文件
//        String zipBasePath = request.getSession().getServletContext().getRealPath("/fileZip");
        //String zipBasePath = SysConfigCache.getString("file_url_z") + "zipFile";
        String zipBasePath = "D:\\data0\\uploads\\" + "zipFile";
        String zipFilePath = zipBasePath + File.separator + fileName.substring(fileName.lastIndexOf("/")+1);
        File zipFile = new File(zipFilePath);
        try {
            if (!zipFile.exists()) {//不存在创建一个新的
                zipFile.createNewFile();
            }
            out = response.getOutputStream();
            //创建文件输出流
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            //循环文件，一个一个按顺序压缩
            for (int i = 0; i < fileVector.size(); i++) {
                System.out.println(fileVector.get(i).getName() + "大小为：" + fileVector.get(i).length());
                FileUtil.zipFileFun(fileVector.get(i), root, zos);
            }
            //压缩完成以后必须要在此处执行关闭 否则下载文件会有问题
            if (zos != null) {
                zos.closeEntry();
                zos.close();
            }
            byte[] bt = new byte[10 * 1024];
            int size = 0;
            bis = new BufferedInputStream(new FileInputStream(zipFilePath), 10 * 1024);
            //没有读完
            while ((size = bis.read(bt, 0, 10 * 1024)) != -1) {
                out.write(bt, 0, size);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭相关流
            try {
                //避免出异常时无法关闭
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }

                if (bis != null) {
                    bis.close();
                }

                if (out != null) {
                    out.close();
                }
                if (zipFile.exists()) {
                    zipFile.delete();//删除
                }
            } catch (Exception e2) {
                System.out.println("流关闭出错！");
            }

        }
    }
}
