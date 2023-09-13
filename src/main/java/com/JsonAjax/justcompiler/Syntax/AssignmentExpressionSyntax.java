package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;


public class AssignmentExpressionSyntax extends ExpressionSyntax{
    SyntaxToken identifierToken;
    SyntaxToken equalsToken;
    ExpressionSyntax expression;

    
    public AssignmentExpressionSyntax(SyntaxToken identifierToken, SyntaxToken equalsToken ,ExpressionSyntax expression) {
        this.identifierToken = identifierToken;
        this.equalsToken = equalsToken;
        this.expression = expression;
    }


    public SyntaxToken getIdentifierToken() {
        return identifierToken;
    }


    public SyntaxToken getEqualsToken() {
        return equalsToken;
    }


    public ExpressionSyntax getExpression() {
        return expression;
    }


    @Override
    public SyntaxKind kind() {
        return SyntaxKind.assignmentExpression;
    }


    @Override
    public void prettyPrint(String indentation) {
        System.out.println( indentation+"AssignmentExpression");
        System.out.println(indentation+"├──"+ identifierToken.kind());
        System.out.println(indentation+"├──" + equalsToken.kind());
        System.out.print(indentation+"└──");
        expression.prettyPrint(indentation + "    " );   
    }


    @Override
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> list = new ArrayList<>();
        list.add(this.identifierToken);
        list.add(this.equalsToken);
        list.add(this.expression);
        return list;
    }

    
}
