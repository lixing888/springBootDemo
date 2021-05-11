//package com.springboot.demo.util;
//
//
//import com.springboot.demo.service.serviceImpl.DroolsDmnServiceImpl;
//import com.springboot.demo.vo.RuleRequest;
//
//import org.kie.dmn.api.core.DMNDecisionResult;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * DroolsDmnServiceImpl Tester.
// *
// * @author <Authors name>
// * @version 1.0
// * @since <pre>���� 1, 2019</pre>
// */
//
//public class DroolsDmnServiceImplTest {
//
//    @Autowired
//    private static DroolsDmnServiceImpl droolsDmnServiceImpl;
//
//    /**
//     * Method: executeDmnDecision(String ruleName, Map<String, Object> decisionParams)
//     */
////    public static void main(String[] args) {
////        HashMap<String, Object> context = new HashMap<>();
////        context.put("area", 1);
////        //context.put("payment_code", "11111");
////        context.put("department_id", "288980000064974477");
////        List<DMNDecisionResult> apRule = droolsDmnServiceImpl.executeDmnDecision("APRule", context);
////        for (DMNDecisionResult dmnDecisionResult : apRule) {
////            System.out.println(dmnDecisionResult.getResult());
////        }
////    }
//
//    //288980000013704315	三级部门	姚晶	7789632,6755211
//    //288980000061468303	三级部门	姚晶	7789632,6755211
//    //288980000064975724	三级部门	郭芳芳	7789632,6755211
//    public static void main(String[] args) {
//        RuleRequest request = new RuleRequest();
//        request.setRuleCode("AP1Rule");
//        RuleRequest.RuleData request0 = new RuleRequest.RuleData(1, "288980000064975333", "");
//        RuleRequest.RuleData request1 = new RuleRequest.RuleData(1, "288980000000098145", "");
//        //RuleRequest.RuleData request2 = new RuleRequest.RuleData(1,"288980000064975724","");
//        request.setRuleData(Arrays.asList(request0, request1));
//        List<String> apRule = droolsDmnServiceImpl.executeApDecision(request);
//        for (Object dmnDecisionResult : apRule) {
//            System.out.println(dmnDecisionResult);
//        }
//    }
//
//}
