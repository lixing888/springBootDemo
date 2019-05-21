package com.springboot.demo;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.*;

import java.util.List;

public class DroolsDmnTest {
    public static void main(String[] args) throws Exception {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
//
        String ruleName = "AP1Rule";
        DMNRuntime runtime = KieRuntimeFactory.of(container.getKieBase()).get(DMNRuntime.class);
//        List<DMNModel> models = runtime.getModels();
//        DMNModel model = models.stream().filter(mo -> Objects.equals(ruleName, mo.getName())).findFirst().get();
//        DMNDTExpressionEvaluator evaluator = (DMNDTExpressionEvaluator) ((DecisionNodeImpl) model.getDecisions().toArray()[0]).getEvaluator();
//
//        Field dt = evaluator.getClass().getDeclaredField("dt");
//        dt.setAccessible(true);
//        Object o = dt.get(evaluator);
//        DecisionTableImpl decisionTable = ((DTInvokerFunction) o).getDecisionTable();
//
//        Field decisionRules = decisionTable.getClass().getDeclaredField("decisionRules");
//        decisionRules.setAccessible(true);
//        Object input = decisionRules.get(decisionTable);
//
//        System.out.println(input);
//        UnaryTest unaryTest = ((DTDecisionRule) ((ArrayList) input).get(0)).getInputEntry().get(1);
//        Field arg$1 = unaryTest.getClass().getDeclaredField("arg$1");
//        arg$1.setAccessible(true);
//        Object o1 = arg$1.get(unaryTest);
//        System.out.println(o1);


        String nameSpace = "https://github.com/kiegroup/drools/kie-dmn/_46a7b80c-b32e-41c5-a457-0fc42fc627c2";
        String name = "AP1Rule";
        DMNModel model = runtime.getModel(nameSpace, name);
        DMNContext context = runtime.newContext();
        context.set("area", 1);
        context.set("payment_code", "");
        context.set("department_id", "288980000064974477");

        DMNResult dmnResult = runtime.evaluateByName(model, context, name);
        List<DMNDecisionResult> results = dmnResult.getDecisionResults();
        for (DMNDecisionResult result : results) {
            System.out.println(result.getResult());
        }
    }
}
