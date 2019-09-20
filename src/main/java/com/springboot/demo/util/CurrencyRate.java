package com.springboot.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//注意：使用的是com.alibaba.fastjson.JSONArray ，不是net.sf.json.JSONObjec

/**
 * @author lixing
 * https://www.juhe.cn/account/certify
 */
public class CurrencyRate {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    //配置您申请的APPKEY
    public static final String APPKEY = "**********************";
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    static Double[] eRate = new Double[10];

    public static void main(String[] args) {
        String result1 = null;
        String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";
        //请求接口地址
        Map params = new HashMap();
        //请求参数
        params.put("key", APPKEY);
        //APP Key
        params.put("type", "");
        //两种格式(0或者1,默认为0)
        try {
            result1 = net(url, params, "GET");
            //将字符串转化成JSON对象
            JSONObject object = JSONObject.parseObject(result1);
            //转化成JSON数组
            JSONArray resultList = object.getJSONArray("result");
            //取出JSON数组中的值
            for (int i = 0; i < resultList.size(); i++) {
                // mBuyPri 是现钞买入价
                // USD美元
                JSONObject json = (JSONObject) resultList.get(i);
                String USD = json.getString("data1");
                System.out.println(USD);
                JSONObject rJson1 = (JSONObject) json.get("data1");
                eRate[1] = rJson1.getDouble("mBuyPri");
                System.out.println(eRate[1]);
                //EUP欧元
                String EUP = json.getString("data2");
                System.out.println(EUP);
                JSONObject rJson2 = (JSONObject) json.get("data2");
                eRate[2] = rJson2.getDouble("mBuyPri");
                System.out.println(eRate[2]);
                // HKD港币
                String HKD = json.getString("data3");
                System.out.println(HKD);
                JSONObject rJson3 = (JSONObject) json.get("data3");
                eRate[3] = rJson3.getDouble("mBuyPri");
                System.out.println(eRate[3]);
                //JPY日元
                String JPY = json.getString("data4");
                System.out.println(JPY);
                JSONObject rJson4 = (JSONObject) json.get("data4");
                eRate[4] = rJson4.getDouble("mBuyPri");
                System.out.println(eRate[4]);
                // GBP英镑
                String GBP = json.getString("data5");
                System.out.println(GBP);
                JSONObject rJson5 = (JSONObject) json.get("data5");
                eRate[5] = rJson5.getDouble("mBuyPri");
                System.out.println(eRate[5]);
                // THB泰国铢
                String THB = json.getString("data8");
                System.out.println(THB);
                JSONObject rJson6 = (JSONObject) json.get("data8");
                eRate[6] = rJson6.getDouble("mBuyPri");
                System.out.println(eRate[6]);
                // MOP澳门元
                String MOP = json.getString("data18");
                System.out.println(MOP);
                JSONObject rJson7 = (JSONObject) json.get("data18");
                eRate[7] = rJson7.getDouble("mBuyPri");
                System.out.println(eRate[7]);
                //KRW韩国元
                String KRW = json.getString("data21");
                System.out.println(KRW);
                JSONObject rJson8 = (JSONObject) json.get("data21");
                eRate[8] = rJson8.getDouble("mBuyPri");
                System.out.println(eRate[8]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * @param strUrl 请求地址         * @param params 请求参数         * @param method 请求方法         * @return  网络请求字符串         * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlencode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
