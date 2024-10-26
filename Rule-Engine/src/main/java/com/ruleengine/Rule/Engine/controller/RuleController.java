package com.ruleengine.Rule.Engine.controller;

import com.ruleengine.Rule.Engine.dto.EvaluateRuleRequest;
import com.ruleengine.Rule.Engine.model.Rule;
import com.ruleengine.Rule.Engine.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping
    public Rule createRule(@RequestBody String ruleString) {
        return ruleService.createRule(ruleString);
    }

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }


    @PostMapping("/evaluate-rule")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody EvaluateRuleRequest request) {
        boolean result = ruleService.evaluateRule(request.getRuleString(), request.getUserData());
        return ResponseEntity.ok(result);
    }
}
