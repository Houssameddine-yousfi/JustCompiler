package com.JsonAjax.justcompiler.Syntax;

import java.util.List;

public class ExpressionStatementSyntax extends StatementSyntax {


    ExpressionSyntax expression;

    public ExpressionStatementSyntax(ExpressionSyntax expression) {
        this.expression = expression;
    }

    

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.expressionStatment;
    }


    @Override
    public List<SyntaxNode> getChildren() {
       return List.of(expression);
    }



    public ExpressionSyntax getExpression() {
        return expression;
    }
    
}
