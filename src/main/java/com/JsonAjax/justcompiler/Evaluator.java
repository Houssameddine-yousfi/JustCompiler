/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler;

import java.util.Map;

import com.JsonAjax.justcompiler.Binding.BoundAssignmentExpression;
import com.JsonAjax.justcompiler.Binding.BoundBinaryExpression;
import com.JsonAjax.justcompiler.Binding.BoundBinaryOperatorKind;
import com.JsonAjax.justcompiler.Binding.BoundExpression;
import com.JsonAjax.justcompiler.Binding.BoundLiteralExpression;
import com.JsonAjax.justcompiler.Binding.BoundUnaryExpression;
import com.JsonAjax.justcompiler.Binding.BoundUnaryOperatorKind;
import com.JsonAjax.justcompiler.Binding.BoundVariableExpression;
/**
 *
 * @author ajax
 */
public class Evaluator {

    private Map<VariableSymbol, Object> variables;
    private BoundExpression root;

    public Evaluator(BoundExpression root, Map<VariableSymbol, Object> variables) {
        this.root = root;
        this.variables = variables;
    }

    public Object evaluate() {
        return evaluateExpression(this.root);
    } 

    private Object evaluateExpression(BoundExpression node) {
        switch (node.getKind()) {
            case LiteralExpression:
                return evaluateLiteralExpression(node);
            case VariableExpression:
                return evaluateVariableExpression(node);
            case AssignmentExpression:
                return evaluateAssignmentExpression(node);
            case UnaryExpression:
                return evaluateUnaryExpression(node);
            case BinaryExpression:
                return evaluateBinaryExpression(node);
            default:
                throw new AssertionError("Unexpected Node " + node);
        }
    }

    private Object evaluateBinaryExpression(BoundExpression node) throws AssertionError {
        Object left = evaluateExpression(((BoundBinaryExpression) node).getLeft());
        Object right = evaluateExpression(((BoundBinaryExpression) node).getRight());

        BoundBinaryOperatorKind operation = ((BoundBinaryExpression) node).getOperator().getKind();

        switch (operation) {
            case Addition:
                return (int) left + (int) right;
            case Substraction:
                return (int) left - (int) right;
            case Multiplication:
                return (int) left * (int) right;
            case Division:
                return (int) left / (int) right;
            case LogicalAnd:
                return (boolean) left && (boolean) right;
            case LogicalOr:
                return (boolean) left || (boolean) right;
            case Equals:
                return left.equals(right);
            case NotEquals:
                return !left.equals(right);
            default:
                throw new AssertionError("Unexpected Binary operator " + operation);
        }
    }

    private Object evaluateUnaryExpression(BoundExpression node) throws AssertionError {
        Object operand = evaluateExpression(((BoundUnaryExpression) node).getOperand());

        BoundUnaryOperatorKind operator = ((BoundUnaryExpression) node).getOperator().getKind();

        switch (operator) {
            case Identity:
                return (int) operand;
            case Negation:
                return -((int) operand);
            case LogicalNegation:
                return !((boolean) operand);
            default:
                throw new AssertionError("Unexpected Unary operator " + operator);
        }
    }

    private Object evaluateAssignmentExpression(BoundExpression node) {
        Object value = evaluateExpression(((BoundAssignmentExpression) node).getExpression());
        variables.put(((BoundAssignmentExpression) node).getVariable(), value);
        return value;
    }

    private Object evaluateVariableExpression(BoundExpression node) {
        return variables.get(((BoundVariableExpression) node).getVariable());
    }

    private Object evaluateLiteralExpression(BoundExpression node) {
        return ((BoundLiteralExpression) node).getValue();
    }
}
