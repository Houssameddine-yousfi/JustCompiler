package com.JsonAjax.justcompiler.Binding;

public class BoundIfStatment extends BoundStatement{

    private BoundExpression condition;
    private BoundStatement thanStatement;
    private BoundStatement elseStatement;
    
    
    public BoundIfStatment(BoundExpression condition, BoundStatement thanStatement, BoundStatement elseStatement) {
        this.condition = condition;
        this.thanStatement = thanStatement;
        this.elseStatement = elseStatement;
    }

    public BoundExpression getCondition() {
        return condition;
    }

    public BoundStatement getThanStatement() {
        return thanStatement;
    }

    public BoundStatement getElseStatement() {
        return elseStatement;
    }
    
    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.ifStatement;
    }

}
