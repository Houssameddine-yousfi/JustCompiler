package com.JsonAjax.justcompiler.Binding;

public class BoundWhileStatement extends BoundStatement{

    private BoundExpression condition;
    private BoundStatement body;

    

    public BoundWhileStatement(BoundExpression condition, BoundStatement body) {
        this.condition = condition;
        this.body = body;
    }



    public BoundExpression getCondition() {
        return condition;
    }



    public void setCondition(BoundExpression condition) {
        this.condition = condition;
    }



    public BoundStatement getBody() {
        return body;
    }



    public void setBody(BoundStatement body) {
        this.body = body;
    }



    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.whileStatement;
    }
    
}
