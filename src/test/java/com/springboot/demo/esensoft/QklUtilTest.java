package com.springboot.demo.esensoft;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Copyright (C), 2018-2021
 * FileName: QklUtilTest
 * Author:   李兴
 * Date:     2021/7/2 11:33
 * Description: 区块链调用demo
 */
public class QklUtilTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://119.3.180.191:8081/bank11_t_m-1.0/sendExcel");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            HashMap<String, String> map1 = new HashMap<String, String>();
            String jsonString = fileToBase64("D:\\2021-07-14 17-24-02f1ac74a4f0186b9cc7d159faf02b45380acb5caeda8e0eebaf285877267db24d.xls");
            String txid = "4fc5dd56594f9a3e683aaf0a9b99ab6ca84623c860b7e87ff6a7b4c58ec9ded5";
            String body = "excelString=" + jsonString + "&txid=" + txid;
            System.out.println(body);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            connection.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("存入成功");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
    }

    /**
     * @param path 文件全路径(加文件名)
     * @return String
     * @description 将文件转base64字符串
     * @date 2019年11月24日
     * @author rmk
     */
    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = new String(Base64.encodeBase64(bytes), "UTF-8");
            System.out.println("将文件[" + path + "]转base64字符串:" + base64);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }
}
