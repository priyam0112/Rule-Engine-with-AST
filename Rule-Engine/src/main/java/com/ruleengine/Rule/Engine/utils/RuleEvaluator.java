package com.ruleengine.Rule.Engine.utils;

import com.ruleengine.Rule.Engine.model.Node;
import org.json.JSONObject;

public class RuleEvaluator {

    /**
     * Evaluates the AST against the provided user data.
     *
     * @param ast   The root node of the AST representing the rule.
     * @param data  A JSONObject containing user attributes (e.g., age, department, salary, experience).
     * @return True if the user meets the rule conditions, false otherwise.
     */
    public boolean evaluateRule(Node ast, JSONObject data) {
        if (ast == null) {
            throw new IllegalArgumentException("AST cannot be null");
        }

        switch (ast.getType()) {
            case "operand":
                return evaluateOperand(ast, data);
            case "operator":
                return evaluateOperator(ast, data);
            default:
                throw new IllegalArgumentException("Unknown node type: " + ast.getType());
        }
    }

    private boolean evaluateOperand(Node operandNode, JSONObject data) {
        String[] parts = operandNode.getValue().split(" ");
        String attribute = parts[0]; // e.g., "age"
        String operator = parts[1];   // e.g., ">"
        String value = parts[2];       // e.g., "30"

        // Get the attribute value from the data
        Object attributeValue = data.get(attribute);
        if (attributeValue == null) {
            return false; // If the attribute is not present, treat as false
        }

        switch (operator) {
            case ">":
                return (int) attributeValue > Integer.parseInt(value);
            case "<":
                return (int) attributeValue < Integer.parseInt(value);
            case "=":
            case "==":
                return attributeValue.toString().equals(value.replace("'", ""));
            case ">=":
                return (int) attributeValue >= Integer.parseInt(value);
            case "<=":
                return (int) attributeValue <= Integer.parseInt(value);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private boolean evaluateOperator(Node operatorNode, JSONObject data) {
        boolean leftResult = evaluateRule(operatorNode.getLeft(), data);
        boolean rightResult = evaluateRule(operatorNode.getRight(), data);

        switch (operatorNode.getValue()) {
            case "AND":
                return leftResult && rightResult;
            case "OR":
                return leftResult || rightResult;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operatorNode.getValue());
        }
    }
}
