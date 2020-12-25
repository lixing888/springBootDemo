package com.springboot.demo.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @program: springBootDemo
 * @description: 腾讯地图工具类
 * @author: lixing
 * @create: 2020-12-17 20:46
 **/
public class TencentMapUtil {
    /**
     * 通过腾讯地图将经纬度转成详细地址
     *
     * @param lat 纬度
     * @param lng 经度
     * @param key 开发密钥（Key） 需自行申请
     * @return
     */
    public static JSONObject getLatAndLng(String lat, String lng, String key) {
        try {
            String hsUrl = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + key + "&get_poi=1";

            URL url;

            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            // 提交模式
            con.setRequestMethod("GET");
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    // TODO Auto-generated method stub

                }
            };

            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });


            InputStream inStream = con.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            //网页的二进制数据
            byte[] b = outStream.toByteArray();
            outStream.close();
            inStream.close();
            String rtn = new String(b, "utf-8");
            if (StringUtils.isNotBlank(rtn)) {
                JSONObject object = JSONObject.fromObject(rtn);
                if (object != null) {
                    if (object.has("status") && object.getInt("status") == 0) {
                        JSONObject result = JSONObject.fromObject(object.get("result"));
                        if (result != null) {
                            JSONObject addressComponent = JSONObject.fromObject(result.get("address_component"));
                            if (addressComponent != null) {
                                return addressComponent;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 测试类
     * @param args
     * {"nation":"中国","province":"北京市","city":"北京市","district":"海淀区","street":"北四环西路","street_number":"北四环西路66号"}
     */
    public static void main(String[] args) {
        System.out.println(getLatAndLng("39.984154", "116.307490", "OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77"));
    }
}
