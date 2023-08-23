/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler.Syntax;

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

    @Override
    public void prettyPrint(String indentation) {
        System.out.println( "ParenthesizedExpression");
        
        System.out.print(indentation+"└──");
        expression.prettyPrint(indentation + "    ");
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
    
    
}
