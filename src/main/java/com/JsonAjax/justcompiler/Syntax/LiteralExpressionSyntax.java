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

    SyntaxToken literalToken;

    public LiteralExpressionSyntax(SyntaxToken LiteralToken) {
        this.literalToken = LiteralToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.literalExpression;
    }

    public SyntaxToken getLiteralToken() {
        return literalToken;
    }

    @Override
    public void prettyPrint(String indentation) {
        System.out.println( literalToken.kind() + 
                " " + literalToken.getValue());
    }
    
    
}
