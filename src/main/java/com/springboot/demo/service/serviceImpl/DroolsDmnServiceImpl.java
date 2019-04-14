package com.springboot.demo.service.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.springboot.demo.service.AmsService;
import com.springboot.demo.service.DroolsDmnService;
import com.springboot.demo.vo.RuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kie.dmn.api.core.*;
import org.kie.dmn.api.core.ast.InputDataNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DroolsDmnServiceImpl implements DroolsDmnService {

    @Autowired
    private DMNRuntime dmnRuntime;
    @Autowired
    private AmsService amsService;

    @Override
    public List<DMNDecisionResult> executeDmnDecision(String ruleName, Map<String, Object> decisionParams) {

        DMNModel model = getDmnModel(ruleName);
        DMNContext context = dmnRuntime.newContext();
        Set<InputDataNode> inputs = model.getInputs();
        //如果参数没传，也需要set 进constext
        for (InputDataNode input : inputs) {
            String inputName = input.getName();
            if (decisionParams.containsKey(inputName)) {
                context.set(inputName, decisionParams.get(inputName));
            } else {
                context.set(inputName, "");
            }
        }
        DMNResult dmnResult = dmnRuntime.evaluateByName(model, context, ruleName);
        return dmnResult.getDecisionResults();
    }

    @Override
    //不确定规则的返回结果，返回object
    public List<String> executeApDecision(RuleRequest ruleRequest) {
        List<RuleRequest.RuleData> ruleData = ruleRequest.getRuleData();
        String ruleCode = ruleRequest.getRuleCode();
        if (CollUtil.isEmpty(ruleData)) {
            throw new RuntimeException("规则参数不能为空");
        }

        if (ruleData.size() == 1) {
            return singleApRule(ruleCode,ruleData.get(0));
        }
        return muliApRule(ruleCode,ruleData);
    }

    private List<String> muliApRule(String ruleCode,List<RuleRequest.RuleData> ruleData) {
        List<List<String>> preResult = new ArrayList<>(ruleData.size());
        //分别求结果
        for (RuleRequest.RuleData request : ruleData) {
            List<String> objects = singleApRule(ruleCode,request);
            //如果一个为空，没必要取交集，直接执行other规则
            if (CollUtil.isEmpty(objects)) {
                return executeOtherRule(ruleCode,ruleData);
            }
            preResult.add(objects);
        }
        //如果有交集，返回交集
        if (CollUtil.isNotEmpty(preResult)) {
            Collection<String> intersection = CollUtil.intersection(preResult.get(0), preResult.get(1));
            //交集为空，执行other
            if (CollUtil.isEmpty(intersection)) {
                return executeOtherRule(ruleCode,ruleData);
            }
            //遍历求交集
            for (int i = 2; i < preResult.size(); i++) {
                if (CollUtil.isEmpty(intersection)) {
                    return executeOtherRule(ruleCode,ruleData);
                }
                intersection = CollUtil.intersection(intersection, preResult.get(i));
            }
            //交集不为空，返回交集
            if (CollUtil.isNotEmpty(intersection)) {
                return new ArrayList<>(intersection);
            }
        }
        return executeOtherRule(ruleCode,ruleData);
    }

    /**
     * 获得other规则执行结果
     * @param ruleData
     * @return
     */
    private List<String> executeOtherRule(String ruleCode,List<RuleRequest.RuleData> ruleData) {
        Map<Integer, List<RuleRequest.RuleData>> collect = ruleData.stream().collect(Collectors.groupingBy(req -> req.getArea()));
        //如果area是一个，则返回当前area的other规则结果
        if (collect.size() == 1) {
            return singleApRule(ruleCode,new RuleRequest.RuleData(collect.keySet().toArray(new Integer[]{})[0]));
        }else{
            //如果area是多，则返回所有area的合集
            List<String> result = new ArrayList<>();
            for (Integer area : collect.keySet()) {
                List<String> list = singleApRule(ruleCode,new RuleRequest.RuleData(area));
                result.addAll(list);
            }
            return result;
        }
    }

    private List<String> singleApRule(String ruleCode,RuleRequest.RuleData ruleData) {

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(ruleData));
        List<DMNDecisionResult> dmnDecisionResults = executeDmnDecision(ruleCode, jsonObject);
        if (CollUtil.isNotEmpty(dmnDecisionResults)) {
            String[] split = ((String) dmnDecisionResults.get(0).getResult()).split(",");
            return Arrays.asList(split);
        }
        //查上级部门进行判断
        String departmentPath = amsService.listDepartmentPath(ruleData.getDepartmentId());
        if (StringUtils.isEmpty(departmentPath)) {
            throw new RuntimeException("部门全路径查询出错");
        }
        String[] depts = departmentPath.split("@");
        for (int i = depts.length; i < 0; i--) {
            ruleData.setDepartmentId(depts[i]);
            jsonObject = JSON.parseObject(JSON.toJSONString(ruleData));
            dmnDecisionResults = executeDmnDecision(ruleCode, jsonObject);
            if (CollUtil.isNotEmpty(dmnDecisionResults)) {
                String[] split = ((String) dmnDecisionResults.get(0).getResult()).split(",");
                return Arrays.asList(split);
            }
        }
        return Collections.emptyList();
    }


    private DMNModel getDmnModel(String ruleName) {
        List<DMNModel> models = dmnRuntime.getModels();
        if (CollUtil.isEmpty(models)) {
            throw new RuntimeException("未找到规则文件，请联系管理员");
        }
        return models.stream()
                .filter(mod -> Objects.equals(mod.getName(), ruleName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到规则文件，请联系管理员"));
    }

}



