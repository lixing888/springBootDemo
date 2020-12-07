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

public class HttpClientTtsDemo {
    private static String url = "http://52.62.107.180:5066/tts";
    private static int syncid = 0;
    private static String sid = "";
    private static String text = "科大讯飞股份有限公司";

    public static void main(String[] args) throws Exception {
        System.out.println("Start main");
        SessionBegin();
        GetParam();
        SetParam();
        TextPut();
        GetAudio();
        SessionEnd();
        System.out.println("Finish main");
    }

    public static String sendJsonHttpPost(String url, String json) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseInfo = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-type", "application/json-rpc; charset=utf-8");
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
    public static void SessionBegin() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            JSONObject json_params = new JSONObject();
            json_params.put("appid", "test1234");
            json_params.put("aue", "raw");
            json_params.put("cmd", "ssb");
            json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
            json_params.put("private_key", "c20151fbc6d83d1eaf038ff47e6b39d1");
            json_params.put("svc", "tts");
            json_params.put("syncid", String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 1);

            String responseBody = sendJsonHttpPost(url, json_body.toJSONString());
            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("SessionBegin:" + JSONObject.toJSONString(json, true));

            sid = json.getJSONObject("result").getString("sid");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * GetParam
     */
    public static void GetParam() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            JSONObject json_params = new JSONObject();
            json_params.put("Tparams", "voicelist");
            json_params.put("appid", "test1234");
            json_params.put("cmd", "Tparam");
            json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
            json_params.put("flag", "get_param");
            json_params.put("sid", sid);
            json_params.put("svc", "tts");
            json_params.put("syncid", String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 1);

            System.out.println("toJSONString:" + JSONObject.toJSONString(json_body, true));
            String responseBody = sendJsonHttpPost(url, json_body.toJSONString());
            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("GetParam:" + JSONObject.toJSONString(json, true));

            sid = json.getJSONObject("result").getString("sid");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * SetParam
     */
    public static void SetParam() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            JSONObject json_params = new JSONObject();
            json_params.put("Tparams", "vid=60020");
            json_params.put("appid", "test1234");
            json_params.put("cmd", "Tparam");
            json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
            json_params.put("flag", "set_param");
            json_params.put("sid", sid);
            json_params.put("svc", "tts");
            json_params.put("syncid", String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 1);

            System.out.println("toJSONString:" + JSONObject.toJSONString(json_body, true));
            String responseBody = sendJsonHttpPost(url, json_body.toJSONString());
            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("GetParam:" + JSONObject.toJSONString(json, true));

            sid = json.getJSONObject("result").getString("sid");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * TextPut
     */
    public static void TextPut() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = text.getBytes("UTF-8");

            JSONObject json_params = new JSONObject();
            json_params.put("appid", "test1234");
            json_params.put("cmd", "txtw");
            json_params.put("data", encoder.encodeToString(textByte));
            json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
            json_params.put("sid", sid);
            json_params.put("svc", "tts");
            json_params.put("syncid", String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 2);

            String responseBody = sendJsonHttpPost(url, json_body.toJSONString());

            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");

            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("TextPut:" + JSONObject.toJSONString(json, true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * GetAudio
     */
    public static void GetAudio() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            String fileName = "D:/result.pcm";
            File target = new File(fileName);
            if (target.exists() && target.isFile()) {
                target.delete();
            }

            if (target.createNewFile()) {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(target));
                while (true) {
                    JSONObject json_params = new JSONObject();
                    json_params.put("appid", "test1234");
                    json_params.put("cmd", "grs");
                    json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
                    json_params.put("sid", sid);
                    json_params.put("svc", "tts");
                    json_params.put("syncid", String.valueOf(syncid));
                    syncid = syncid + 1;

                    JSONObject json_body = new JSONObject();
                    json_body.put("jsonrpc", "2.0");
                    json_body.put("method", "deal_request");
                    json_body.put("params", json_params);
                    json_body.put("id", 3);

                    String responseBody = sendJsonHttpPost(url, json_body.toJSONString());
                    String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");
                    JSONObject json = JSONObject.parseObject(StrResponseBody);
                    System.out.println("GetAudio:" + JSONObject.toJSONString(json, true));

                    dos.write(decoder.decode(json.getJSONObject("result").getString("data")));

                    if (0 == json.getJSONObject("result").getInteger("ttsStatus")) {
                        break;
                    }
                }
                dos.flush();
                dos.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * SessionEnd
     */
    public static void SessionEnd() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = text.getBytes("UTF-8");

            JSONObject json_params = new JSONObject();
            json_params.put("appid", "test1234");
            json_params.put("cmd", "sse");
            json_params.put("extend_params", "{\"params\":\"token=appid123,ability=ab_tts\"}");
            json_params.put("sid", sid);
            json_params.put("svc", "tts");
            json_params.put("syncid", String.valueOf(syncid));
            syncid = syncid + 1;

            JSONObject json_body = new JSONObject();
            json_body.put("jsonrpc", "2.0");
            json_body.put("method", "deal_request");
            json_body.put("params", json_params);
            json_body.put("id", 4);

            String responseBody = sendJsonHttpPost(url, json_body.toJSONString());

            String StrResponseBody = new String(decoder.decode(responseBody), "UTF-8");

            JSONObject json = JSONObject.parseObject(StrResponseBody);
            System.out.println("SessionEnd:" + JSONObject.toJSONString(json, true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
