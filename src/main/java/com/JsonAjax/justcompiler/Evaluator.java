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

    
    private Map<String, Object> variables;
    private BoundExpression root;

    public Evaluator(BoundExpression root,Map<String, Object> variables) {
        this.root = root;
        this.variables = variables;
    }
    
    public Object evaluate(){
        return evaluateExpression(this.root);
    }

    private Object evaluateExpression(BoundExpression node) {
        
        if(node instanceof BoundLiteralExpression)
            return ((BoundLiteralExpression) node).getValue();
        
        if(node instanceof BoundVariableExpression)
            return variables.get(((BoundVariableExpression) node).getName());

        if(node instanceof BoundAssignmentExpression){
            Object value = evaluateExpression(((BoundAssignmentExpression)node).getExpression());
            variables.put(((BoundAssignmentExpression)node).getName(), value);
            return value;
        }

        if(node instanceof BoundUnaryExpression){
            Object operand = evaluateExpression(((BoundUnaryExpression)node).getOperand());

            BoundUnaryOperatorKind operator = ((BoundUnaryExpression)node).getOperator().getKind();
            
            switch(operator){
                case Identity:
                    return (int) operand;
                case Negation:
                    return - ((int) operand);
                case LogicalNegation:
                    return !((boolean) operand);
                default:
                    throw new AssertionError("Unexpected Unary operator " + operator);
            }
        }
        
        if(node instanceof BoundBinaryExpression){
            Object left =  evaluateExpression(((BoundBinaryExpression)node).getLeft());
            Object right = evaluateExpression(((BoundBinaryExpression)node).getRight());
            
            BoundBinaryOperatorKind operation = ((BoundBinaryExpression)node).getOperator().getKind();
            
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
        
        // if(node instanceof ParenthesizedExpressionSyntax){
        //     return evaluateExpression(((ParenthesizedExpressionSyntax) node).getExpression());
        // }
        
        throw new AssertionError("Unexpected Node " + node);

    }
}
