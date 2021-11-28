/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.SyntaxKind;
import com.JsonAjax.justcompiler.SyntaxToken;

/**
 *
 * @author ajax
 */
public class BinaryExpressionSyntax extends ExpressionSyntax{

    private ExpressionSyntax left;
    private ExpressionSyntax right;
    private SyntaxToken operatorToken;

    public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operatorToken, ExpressionSyntax right) {
        this.left = left;
        this.right = right;
        this.operatorToken = operatorToken;
    }
    
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.binaryExpression;
    }
    
    @Override
    public void prettyPrint(String indentation){
        System.out.println(indentation  + "BinaryExpression");
        
        indentation = indentation.concat("    ");
        left.prettyPrint(indentation);
        System.out.println(indentation + operatorToken.kind());
        right.prettyPrint(indentation);   
    }

}
