/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.SyntaxKind;

/**
 *
 * @author ajax
 */
public class Evaluator {
    
    private ExpressionSyntax root;

    public Evaluator(ExpressionSyntax root) {
        this.root = root;
    }
    
    public int evaluate(){
        return evaluateExpression(this.root);
    }

    private int evaluateExpression(ExpressionSyntax node) {
        
        if(node instanceof NumberExpressionSyntax)
            return (int) ((NumberExpressionSyntax) node).getNumberToken().getValue();
        
        if(node instanceof BinaryExpressionSyntax){
            int left = evaluateExpression(((BinaryExpressionSyntax)node).getLeft());
            int right = evaluateExpression(((BinaryExpressionSyntax)node).getRight());
            
            SyntaxKind operation = ((BinaryExpressionSyntax)node).getOperatorToken().kind();
            
            if(operation == SyntaxKind.plus)
                return left + right;
            else if(operation == SyntaxKind.minus)
                return left - right;
            else if(operation == SyntaxKind.star)
                return left * right;
            else if(operation == SyntaxKind.slash)
                return left / right;
            else 
                throw new AssertionError("Unexpected Binary operator " + operation);
        }
        
        if(node instanceof ParenthesizedExpressionSyntax){
            return evaluateExpression(((ParenthesizedExpressionSyntax) node).getExpression());
        }
        
        throw new AssertionError("Unexpected Node " + node);

    }
}
