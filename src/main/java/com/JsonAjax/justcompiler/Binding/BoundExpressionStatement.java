package com.JsonAjax.justcompiler.Binding;

public class BoundExpressionStatement extends BoundStatement{

    private BoundExpression expression;

    

    public BoundExpressionStatement(BoundExpression expression) {
        this.expression = expression;
    }



    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.expressionStatement;
    }



    public BoundExpression getExpression() {
        return expression;
    }
    
}
