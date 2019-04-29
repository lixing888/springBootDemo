package com.springboot.demo.service;

import com.springboot.demo.vo.RuleRequest;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lixing
 */
@Service
@Component
public interface DroolsDmnService {

    /**
     * 执行DMN规则
     *
     * @param ruleName
     * @param decisionParams
     * @return
     */
    List<DMNDecisionResult> executeDmnDecision(String ruleName, Map<String, Object> decisionParams);

    /**
     * ap1 & ap2 规则
     *
     * @param ruleRequest
     * @return 不确定规则的返回结果，返回object
     */
    List<String> executeApDecision(RuleRequest ruleRequest);
}

