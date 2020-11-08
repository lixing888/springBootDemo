//package com.springboot.demo.third;
//
//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.sms.v20190711.SmsClient;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
//import com.tencentcloudapi.sms.v20190711.models.SendStatus;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.logging.Logger;
//
///**
// * @program: springBootDemo
// * @description: SpringBoot 开发案例之接入腾讯云短信
// * @author: lixing
// * @create: 2020-11-07 21:17
// * 短信发送
// * 参考：https://cloud.tencent.com/document/product/382/43194#example
// * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
// * 短信控制台：https://console.cloud.tencent.com/smsv2
// **/
//@Component
//@Configuration
//@Slf4j
//public class SendSmsUtils {
//
//
//    private SendSmsProperties sendSms;
//
//    public SendSmsUtils(SendSmsProperties sendSms) {
//        this.sendSms = sendSms;
//    }
//
//    private static String code = "OK";
//
//    /**
//     * 发送
//     */
//    public void sand(Sms sms) {
//        log.info("发送短信:{}", Arrays.toString(sms.getMobile()));
//        try {
//            /**
//             * 实例化一个认证对象
//             */
//            Credential cred = new Credential(sendSms.getSecretId(), sendSms.getSecretKey());
//            /**
//             * 实例化一个客户端配置对象
//             */
//            ClientProfile clientProfile = new ClientProfile();
//            /**
//             * 实例化 SMS 的 client 对象
//             */
//            SmsClient client = new SmsClient(cred, "",clientProfile);
//            /**
//             * 实例化一个请求对象
//             */
//            SendSmsRequest req = new SendSmsRequest();
//            /**
//             * SDK appId 短信-应用管理-应用列表
//             */
//            req.setSmsSdkAppid(sendSms.getAppId());
//            /**
//             * 签名 短信-国内短信-签名管理，必须是审核通过的
//             */
//            req.setSign(sms.getSign());
//            /**
//             * 模板ID 短信-国内短信-正文模板管理，必须是审核通过的
//             */
//            req.setTemplateID(sms.getTemplateId());
//            /**
//             * 如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号
//             */
//            String[] phoneNumberSet = new String[sms.getMobile().length];
//            String[] array = sms.getMobile();
//            for(int i=0;i<phoneNumberSet.length;i++){
//                phoneNumberSet[i] = "+86"+array[i];
//            }
//            req.setPhoneNumberSet(phoneNumberSet);
//            /**
//             * 模板参数{}
//             */
//            String[] templateParams = sms.getParams();
//            req.setTemplateParamSet(templateParams);
//            /**
//             * 发送请求
//             */
//            SendSmsResponse res = client.SendSms(req);
//            /**
//             * 打印成功失败信息
//             */
//            SendStatus[] status = res.getSendStatusSet();
//            if(code.equals(status[0].getCode())){
//                log.info("短信:{}发送成功", Arrays.toString(sms.getMobile()));
//            }else{
//                log.info("短信:{}发送失败", SendSmsResponse.toJsonString(res));
//            }
//        } catch (TencentCloudSDKException e) {
//            log.error("短信:{}发送失败,{}",Arrays.toString(sms.getMobile()),e.getMessage());
//        }
//    }
//
//}
