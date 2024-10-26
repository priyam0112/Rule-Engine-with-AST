package com.ruleengine.Rule.Engine.dto;

import java.util.Map;

public class EvaluateRuleRequest {
    private String ruleString;
    private Map<String, Object> userData;

    // Getters and Setters
    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public Map<String, Object> getUserData() {
        return userData;
    }

    public void setUserData(Map<String, Object> userData) {
        this.userData = userData;
    }
}