package com.ruleengine.Rule.Engine.service;

import com.ruleengine.Rule.Engine.model.Rule;
import com.ruleengine.Rule.Engine.repository.RuleRepository;
import com.ruleengine.Rule.Engine.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public Rule createRule(String ruleString) {
        System.out.println("Parsing rule to AST: " + ruleString);
        Node ast = parseRuleToAST(ruleString);
        Rule rule = new Rule(ruleString, ast);
        return ruleRepository.save(rule);
    }

    private Node parseRuleToAST(String ruleString) {
        // Basic parsing logic for combined rules (supports AND, OR operators)
        String trimmedRule = ruleString.trim();
        
        // Split rule into left and right parts at the first occurrence of AND/OR
        String[] tokens = trimmedRule.split("\\s+(AND|OR)\\s+", 2);
        if (tokens.length > 1) {
            String operator = trimmedRule.contains("AND") ? "AND" : "OR";
            Node rootNode = new Node("operator", operator);
            
            // Recursive parsing for each side of the operator
            rootNode.setLeft(parseRuleToAST(tokens[0]));
            rootNode.setRight(parseRuleToAST(tokens[1]));

            System.out.println("Parsed AST root node: " + rootNode);
            return rootNode;
        } else {
            // Single operand condition
            Node operandNode = new Node("operand", trimmedRule);
            System.out.println("Parsed AST operand node: " + operandNode);
            return operandNode;
        }
    }

    public boolean evaluateRule(String ruleString, Map<String, Object> userData) {
        System.out.println("Evaluating rule: " + ruleString + " with user data: " + userData);
        Node ast = parseRuleToAST(ruleString);
        return evaluateAST(ast, userData);
    }

    public boolean evaluateAST(Node node, Map<String, Object> userData) {
        if (node == null) {
            throw new IllegalArgumentException("Node is null in AST evaluation");
        }
        
        System.out.println("Evaluating node: " + node.getValue() + " (" + node.getType() + ")");
        
        if (node.getType().equals("operand")) {
            return evaluateOperand(node, userData);
        } else if (node.getType().equals("operator")) {
            boolean leftResult = evaluateAST(node.getLeft(), userData);
            boolean rightResult = evaluateAST(node.getRight(), userData);
            
            System.out.println("Operator: " + node.getValue() + " | Left result: " + leftResult + ", Right result: " + rightResult);
            
            switch (node.getValue()) {
                case "AND":
                    return leftResult && rightResult;
                case "OR":
                    return leftResult || rightResult;
                default:
                    throw new UnsupportedOperationException("Unsupported operator: " + node.getValue());
            }
        }
        throw new IllegalArgumentException("Invalid node type: " + node.getType());
    }

    public boolean evaluateOperand(Node node, Map<String, Object> userData) {
        Pattern pattern = Pattern.compile("(.+)\\s*(>|<|=)\\s*(.+)");
        Matcher matcher = pattern.matcher(node.getValue());
        
        if (!matcher.matches() || matcher.groupCount() != 3) {
            throw new IllegalArgumentException("Invalid operand format: " + node.getValue());
        }
    
        String field = matcher.group(1).trim().replaceAll("^\"|\"$", "");
        String operator = matcher.group(2).trim();
        String value = matcher.group(3).trim().replaceAll("^\"|\"$", "");
    
        System.out.println("Parsed operand - Field: " + field + ", Operator: " + operator + ", Value: " + value);
    
        Object userValue = userData.get(field);
    
        if (userValue == null) {
            throw new IllegalArgumentException("Field not found in user data: " + field);
        }
    
        switch (operator) {
            case ">":
                return (Integer) userValue > Integer.parseInt(value);
            case "<":
                return (Integer) userValue < Integer.parseInt(value);
            case "=":
                return userValue.toString().equals(value);
            default:
                throw new UnsupportedOperationException("Unsupported operator: " + operator);
        }
    }
    
}
