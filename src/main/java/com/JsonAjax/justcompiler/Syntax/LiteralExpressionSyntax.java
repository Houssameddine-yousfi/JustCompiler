/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ajax
 */
public class LiteralExpressionSyntax extends ExpressionSyntax {

    SyntaxToken literalToken;
    Object value;

    public LiteralExpressionSyntax(SyntaxToken LiteralToken, Object value) {
        this.literalToken = LiteralToken;
        this.value = value;
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

    public Object getValue() {
        return value;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> list = new ArrayList<>();
        list.add(this.literalToken);
        return list;
    }
    
}
