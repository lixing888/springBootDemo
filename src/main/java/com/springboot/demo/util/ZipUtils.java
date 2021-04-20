package com.springboot.demo.util;

import org.apache.commons.compress.compressors.gzip.GzipUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lixing
 * @date 2020-01-07
 * <p>
 * 文件流转压缩包工具类
 */
public class ZipUtils {
    /**
     * 缓存区大小
     */
    private static final int BUFFER_SIZE = 2 * 1024;


    private static void saveAsFileWriter(String filePath, String content) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param file     文本文件
     * @param encoding 编码类型
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            //将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        //返回转换结果
        if (writer != null) {
            return writer.toString();

        } else {
            return null;
        }
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 压缩核心方法
     */
    private static void compress(ZipOutputStream zos, String path, String name, String data) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        zos.putNextEntry(new ZipEntry(path + name));
        int len;
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        while ((len = in.read(buf)) != -1) {
            zos.write(buf, 0, len);
        }
        zos.closeEntry();
        in.close();
    }


    /**
     * 文本直接转zip压缩成文件
     *
     * @param list -> map -> path 路径; name 文件名; data 具体文本内容;
     * @param out  传入输出流
     * @throws RuntimeException 抛出异常
     */
    public static void toZip(List<Map<String, String>> list, OutputStream out) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (Map<String, String> map : list) {
                String path = map.get("path");
                String name = map.get("name");
                String data = map.get("data");
                compress(zos, path, name, data);
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        OutputStream outputStream = new FileOutputStream(new File("D:/test.zip"));
        Map<String, String> m1 = new HashMap() {{
            put("path", "/f1/f2/f3/");
            put("name", "1.txt");
            put("data", "abcdefg");
        }};
        Map<String, String> m2 = new HashMap() {{
            put("path", "/f1/f2/f3/f4/");
            put("name", "2.txt");
            put("data", "abcdefg");
        }};
        Map<String, String> m3 = new HashMap() {{
            put("path", "");
            put("name", "3.txt");
            put("data", "abcdefg");
        }};

        list.add(m1);
        list.add(m2);
        list.add(m3);
        toZip(list, outputStream);
        if (outputStream != null) {
            outputStream.close();
        }
        String filePath = "D:/file/increment/lixing85210279.txt";
        saveAsFileWriter(filePath, "lixing85210279,知足常乐");
        String result = file2String(new File("D:/file/increment/50AE16084A0D4219956E069E4900E896.txt"), "UTF-8");
        System.out.println("file2String：" + result);
    }
}
