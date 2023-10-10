/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ajax
 */
public class ParenthesizedExpressionSyntax extends ExpressionSyntax{

    private SyntaxToken leftParenthesisToken;
    private ExpressionSyntax expression;
    private SyntaxToken rightParenthesisToken;

    public ParenthesizedExpressionSyntax(SyntaxToken leftParenthesisToken, ExpressionSyntax expression, SyntaxToken rightParenthesisToken) {
        this.leftParenthesisToken = leftParenthesisToken;
        this.expression = expression;
        this.rightParenthesisToken = rightParenthesisToken;
    }
    
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.parenthesizedExpression;
    }


    public SyntaxToken getLeftParenthesisToken() {
        return leftParenthesisToken;
    }

    public ExpressionSyntax getExpression() {
        return expression;
    }

    public SyntaxToken getRightParenthesisToken() {
        return rightParenthesisToken;
    }


    @Override
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> list = new ArrayList<>();
        list.add(this.leftParenthesisToken);
        list.add(this.expression);
        list.add(this.rightParenthesisToken);
        return list;
    }
    
    
}
