//package com.springboot.demo;
//
//
//import com.springboot.demo.service.serviceImpl.DroolsDmnServiceImpl;
//import com.springboot.demo.vo.RuleRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.kie.dmn.api.core.DMNDecisionResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * DroolsDmnServiceImpl Tester.
// *
// * @author <Authors name>
// * @version 1.0
// * @since <pre>���� 1, 2019</pre>
// */
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//public class DroolsDmnServiceImplTest extends BaseTestController {
//
//    @Autowired
//    private DroolsDmnServiceImpl droolsDmnServiceImpl;
//
//    /**
//     * Method: executeDmnDecision(String ruleName, Map<String, Object> decisionParams)
//     */
//    @Test
//    public void testExecuteDmnDecision() throws Exception {
//        HashMap<String, Object> context = new HashMap<>();
//        context.put("area", 1);
//        //context.put("payment_code", "11111");
//        context.put("department_id", "288980000064974477");
//        List<DMNDecisionResult> apRule = droolsDmnServiceImpl.executeDmnDecision("APRule", context);
//        for (DMNDecisionResult dmnDecisionResult : apRule) {
//            System.out.println(dmnDecisionResult.getResult());
//        }
//    }
//
//    //288980000013704315	三级部门	姚晶	7789632,6755211
//    //        288980000061468303	三级部门	姚晶	7789632,6755211
//    //        288980000064975724	三级部门	郭芳芳	7789632,6755211
//
//    @Test
//    public void testExecuteapDecision() throws Exception {
//        RuleRequest request = new RuleRequest();
//        request.setRuleCode("AP1Rule");
//        RuleRequest.RuleData request0 = new RuleRequest.RuleData(1,"288980000064975333","");
//        RuleRequest.RuleData request1 = new RuleRequest.RuleData(1,"288980000000098145","");
//        //RuleRequest.RuleData request2 = new RuleRequest.RuleData(1,"288980000064975724","");
//        request.setRuleData(Arrays.asList(request0,request1));
//        List<String> apRule = droolsDmnServiceImpl.executeApDecision(request);
//        for (Object dmnDecisionResult : apRule) {
//            System.out.println(dmnDecisionResult);
//        }
//    }
//
//}
