package com.ruleengine.Rule.Engine.model;

public class Node {
    private String type; // "operator" or "operand"
    private String value; // Holds the operator or condition
    private Node left;   // Reference to left child
    private Node right;  // Reference to right child

    public Node(String type, String value) {
        this.type = type;
        this.value = value;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
