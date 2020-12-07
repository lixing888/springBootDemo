package com.springboot.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;

public class HttpClientItrDemo {
    private static String url = "http://52.62.107.180:5066/itr";
    private static final int syncid = 0;
    private static String sid = "";
    //private static String text = "我是一个程序员,我是讯飞公司员工.";
    //private static String text = "I am a programmer, and I am an employee of iFLYTEK.";
    private static String text = "I guss now you can test the accuracy in each step .";
    public static void main(String[] args) throws Exception {
        System.out.println("Start main");
        Translate();
        System.out.println("Finish main");
    }

    public static String sendJsonHttpPost(String url, String json) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseInfo = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-type","application/json-rpc; charset=utf-8");
            httpPost.setHeader("Accept", "application/json-rpc");
            httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));

            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                if (null != entity) {
                    responseInfo = EntityUtils.toString(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseInfo;
    }

    /**
     * Translate
     */
    public static void Translate(){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = text.getBytes("UTF-8");

            JSONObject json_params = new JSONObject();
            json_params.put("appid","test1234");
            json_params.put("auth_need","false");
            json_params.put("svc","itr");
            json_params.put("txt",encoder.encodeToString(textByte));
            json_params.put("type","encn");

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 1);

            System.out.println("json_body:" + JSONObject.toJSONString(json_body, true));
            String responseBody = sendJsonHttpPost(url,json_body.toJSONString());

            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");

            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("Translate:" + JSONObject.toJSONString(json, true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
