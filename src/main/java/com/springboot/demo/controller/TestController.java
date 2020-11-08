package com.springboot.demo.controller;

//import com.springboot.demo.third.SendSmsUtils;
import com.springboot.demo.third.Sms;
import com.springboot.demo.vo.Address;
import com.springboot.demo.vo.AddressCheckResult;
//import com.tencentcloudapi.iai.v20200303.models.Result;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lixing
 * drools测试
 */
@RequestMapping("/test")
@Controller
public class TestController {

//    @Autowired
//    private SendSmsUtils sendSmsUtils;
//    @Resource
//    private KieSession kieSession;
//
//    @ResponseBody
//    @RequestMapping("/address")
//    public void test(){
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        // kmodule.xml配置中ksession元素的name属性
//        KieSession kieSession = kc.newKieSession("ksession-rule");
//        Address address = new Address();
//        //address.setPostcode("99425");
//        AddressCheckResult result = new AddressCheckResult();
//        kieSession.insert(address);
//        kieSession.insert(result);
//        int ruleFiredCount = kieSession.fireAllRules();
//        System.out.println("触发了" + ruleFiredCount + "条规则");
////        if(result.isPostCodeResult()){
////            System.out.println("规则校验通过");
////       }
//
//    }

//    @RequestMapping("sms")
//    public void sms() {
//        Sms sms = new Sms();
//        sms.setMobile(new String[]{"13153037469"});
//        sms.setParams(new String[]{"李兴"});
//        sms.setSign("字节跳动");
//        sms.setTemplateId("844589");
//        sendSmsUtils.sand(sms);
//    }

}
