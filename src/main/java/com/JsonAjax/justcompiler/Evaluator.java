/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler;

import com.JsonAjax.justcompiler.Binding.BoundBinaryExpression;
import com.JsonAjax.justcompiler.Binding.BoundBinaryOperatorKind;
import com.JsonAjax.justcompiler.Binding.BoundExpression;
import com.JsonAjax.justcompiler.Binding.BoundLiteralExpression;
import com.JsonAjax.justcompiler.Binding.BoundUnaryExpression;
import com.JsonAjax.justcompiler.Binding.BoundUnaryOperatorKind;

/**
 *
 * @author ajax
 */
public class Evaluator {
    
    private BoundExpression root;

    public Evaluator(BoundExpression root) {
        this.root = root;
    }
    
    public Object evaluate(){
        return evaluateExpression(this.root);
    }

    private Object evaluateExpression(BoundExpression node) {
        
        if(node instanceof BoundLiteralExpression)
            return ((BoundLiteralExpression) node).getValue();

        if(node instanceof BoundUnaryExpression){
            int operand = (int) evaluateExpression(((BoundUnaryExpression)node).getOperand());

            BoundUnaryOperatorKind operator = ((BoundUnaryExpression)node).getOperatorKind();
            
            switch(operator){
                case Identity:
                    return operand;
                case Negation:
                    return -operand;
                default:
                    throw new AssertionError("Unexpected Unary operator " + operator);
            }
        }
        
        if(node instanceof BoundBinaryExpression){
            int left = (int) evaluateExpression(((BoundBinaryExpression)node).getLeft());
            int right = (int) evaluateExpression(((BoundBinaryExpression)node).getRight());
            
            BoundBinaryOperatorKind operation = ((BoundBinaryExpression)node).getOperatorKind();
            
            switch (operation) {
                case Addition:
                    return left + right;
                case Substraction:
                    return left - right;
                case Multiplication:
                    return left * right;
                case Division:   
                    return left / right;
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
