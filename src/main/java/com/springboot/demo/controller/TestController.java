package com.springboot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixing
 * drools测试
 */
@RequestMapping("/test")
@Controller
public class TestController {

//    public PlainResult result(){
//        PlainResult<Object> result = new PlainResult<>();
//        JSONObject jsonObject = JSON.parseObject("");
//        result.setData(jsonObject);
//        return result;
//    }


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

}
