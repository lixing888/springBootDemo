package com.springboot.demo.util;

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
import java.util.ArrayList;
import java.util.Base64;

public class HttpClientIatDemo {
    private static String url = "http://52.62.107.180:5066/iat";
    private static String fileName="E:\\IflytekWork\\source\\webapiDemo\\iflytek-16k.wav";
    private static int syncid = 0;
    private static String sid;
    private static String allResult;
    private static ArrayList<byte[]> buffers = new ArrayList<byte[]>();

    public static void main(String[] args) throws Exception {
        System.out.println("Start main");
        SessionBegin();
        AudioWrite();
        GetResult();
        SessionEnd();
        System.out.println("Finish main");
    }

    public static void readFile(String path){
        File file = new File(path);
        int length = 0;
        try {
            InputStream stream = new FileInputStream(file);
            for(;;)
            {
                //byte[] bts = new byte[128*1024];
                byte[] bts = new byte[2560];
                length = stream.read(bts);
                if (length <= 0)
                    break;
                //System.out.println(length);
                buffers.add(bts);
            }

            if (buffers.size() == 1)
            {
                byte[] bts = new byte[1];
                buffers.add(bts);
            }

            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * SessionBegin
     */
    public static void SessionBegin(){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            JSONObject json_params = new JSONObject();
            json_params.put("appid","iat12345");
            json_params.put("aue","raw");
            json_params.put("auf","audio/L16;rate=16000");
            json_params.put("cmd","ssb");
            json_params.put("extend_params","{\"params\":\"token=iat12345678,ability=ab_asr\"}");
            json_params.put("svc","iat");
            json_params.put("syncid",String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 1);

            String responseBody = sendJsonHttpPost(url,json_body.toJSONString());
            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("SessionBegin:" + JSONObject.toJSONString(json, true));

            sid = json.getJSONObject("result").getString("sid");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * AudioWrite
     */
    public static void AudioWrite(){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();

            // 音频路径，此时使用相对路径，可根据实际情况自己修改
            readFile(fileName);
            for(int i=0; i< buffers.size(); i++){
                byte[] wave = buffers.get(i);
                // 音频缓存区位置信息
                int audioStatus;
                if(i == 0){
                    // 第一段音频
                    audioStatus = 1;
                } else if(i == buffers.size()-1){
                    // 最后一段音频
                    audioStatus = 4;
                } else {
                    // 中间音频
                    audioStatus = 2;
                }

                JSONObject json_params = new JSONObject();
                json_params.put("appid","iat12345");
                json_params.put("audioStatus",String.valueOf(audioStatus));
                json_params.put("cmd","auw");
                json_params.put("data",encoder.encodeToString(wave));
                json_params.put("extend_params","{\"params\":\"token=iat12345678,ability=ab_asr\"}");
                json_params.put("sid",sid);
                json_params.put("svc","iat");
                json_params.put("syncid",String.valueOf(syncid));
                syncid = syncid + 1;

                JSONObject json_body = new JSONObject();
                json_body.put("jsonrpc", "2.0");
                json_body.put("method", "deal_request");
                json_body.put("params", json_params);
                json_body.put("id", 2);

                System.out.println("json_body:" + JSONObject.toJSONString(json_body, true));
                String responseBody = sendJsonHttpPost(url,json_body.toJSONString());
                String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
                JSONObject json = JSONObject.parseObject(StrResponseBody);
                System.out.println("AudioWrite:" + JSONObject.toJSONString(json, true));

                // 判断引擎返回pgs是否为1，为1表示有识别结果可获取
                if(json.getJSONObject("result").getInteger("pgs") == 1)
                {
                    allResult = "" + json.getJSONObject("result").getString("result");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * GetResult
     */
    public static void GetResult(){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();

            while(true) {
                JSONObject json_params = new JSONObject();
                json_params.put("appid","iat12345");
                json_params.put("cmd","grs");
                json_params.put("extend_params","{\"params\":\"token=iat12345678,ability=ab_asr\"}");
                json_params.put("sid",sid);
                json_params.put("svc","iat");
                json_params.put("syncid",String.valueOf(syncid));
                syncid = syncid + 1;

                JSONObject json_body = new JSONObject();
                json_body.put("jsonrpc", "2.0");
                json_body.put("method", "deal_request");
                json_body.put("params", json_params);
                json_body.put("id", 3);

                String responseBody = sendJsonHttpPost(url,json_body.toJSONString());
                String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
                JSONObject json = JSONObject.parseObject(StrResponseBody);
                System.out.println("GetResult:" + JSONObject.toJSONString(json, true));

                // 判断引擎返回pgs是否为1，为1表示有识别结果可获取
                if(json.getJSONObject("result").getInteger("pgs") == 1)
                {
                    allResult = "" + json.getJSONObject("result").getString("result");
                }

                if (5 == json.getJSONObject("result").getInteger("recStatus"))
                {
                    break;
                }
            }

            //打印完整结果
            System.out.println("allResult:" + allResult);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * SessionEnd
     */
    public static void SessionEnd(){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();

            JSONObject json_params = new JSONObject();
            json_params.put("appid","iat12345");
            json_params.put("cmd","sse");
            json_params.put("extend_params","{\"params\":\"token=iat12345678,ability=ab_asr\"}");
            json_params.put("sid",sid);
            json_params.put("svc","iat");
            json_params.put("syncid",String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 4);

            String responseBody = sendJsonHttpPost(url,json_body.toJSONString());

            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");

            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("SessionEnd:" + JSONObject.toJSONString(json, true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
