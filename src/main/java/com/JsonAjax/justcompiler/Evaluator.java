/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler;

import com.JsonAjax.justcompiler.Syntax.BinaryExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.LiteralExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ParenthesizedExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.SyntaxKind;
import com.JsonAjax.justcompiler.Syntax.UnaryExpressionSyntax;

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
        
        if(node instanceof LiteralExpressionSyntax)
            return (int) ((LiteralExpressionSyntax) node).getNumberToken().getValue();

        if(node instanceof UnaryExpressionSyntax){
            int operand = evaluateExpression(((UnaryExpressionSyntax)node).getOperand());

            SyntaxKind operator = ((UnaryExpressionSyntax)node).getOperatorToken().kind();
            
            if(operator == SyntaxKind.plus)
                return operand;
            else if(operator == SyntaxKind.minus)
                return -operand;
            else 
                throw new AssertionError("Unexpected Unary operator " + operator);
        }
        
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
