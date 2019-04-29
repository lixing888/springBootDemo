package com.springboot.demo.util;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SignUtil {

    /**
     * 创建sign=md5(secret+timestamp+biz_params)
     *
     * @param secret
     * @param timestamp
     * @param bizParams
     * @return sign
     */
    public static String getSign(String secret, String timestamp, String bizParams) {
        String signgBeforeStr = secret + timestamp + bizParams;
        return MD5Util.md5Encoder(signgBeforeStr);
    }

    /**
     * timeStamp合法性验证
     * 因为正是服务器时间有误差，时间戳正负5分钟均合法。
     *
     * @param timeStamp
     * @return
     * @throws Exception
     */
    public static boolean timeStampCheck(String timeStamp) {
        //时间戳合法性验证
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 当前时间long
        long time = System.currentTimeMillis();
        // 系统时间前5分钟
        long time1 = time - 300000;
        // 系统时间后5分钟
        long time2 = time + 300000;
        try {
            long timeLong = simpleDateFormat.parse(timeStamp).getTime();
            if (timeLong > time1 && timeLong < time2) {
                //合法
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        String time = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String str = "20190416115559";
        String bizParams = "{\"process_instance_id\":\"\",\"task_instance_id\":\"979e20c5-7545-4932-8415-e87af155a341\"}";
        String sign = getSign("12345678", time, bizParams);
        System.out.println(sign);
    }
}
