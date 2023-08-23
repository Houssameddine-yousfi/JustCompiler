/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

/**
 *
 * @author ajax
 */
public class LiteralExpressionSyntax extends ExpressionSyntax {

    SyntaxToken numberToken;

    public LiteralExpressionSyntax(SyntaxToken LiteralToken) {
        this.numberToken = LiteralToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.numberExpression;
    }

    public SyntaxToken getNumberToken() {
        return numberToken;
    }

    @Override
    public void prettyPrint(String indentation) {
        System.out.println( numberToken.kind() + 
                " " + numberToken.getValue());
    }
    
    
}
