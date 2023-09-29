package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
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
    public void prettyPrint(String indentation, PrintStream printStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    public List<SyntaxNode> getChildren() {
       return List.of(expression);
    }



    public ExpressionSyntax getExpression() {
        return expression;
    }
    
}
