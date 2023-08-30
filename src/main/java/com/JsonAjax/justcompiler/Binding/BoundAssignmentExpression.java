package com.JsonAjax.justcompiler.Binding;

public class BoundAssignmentExpression extends BoundExpression {

    private String name;
    private BoundExpression expression;

    public BoundAssignmentExpression(String name, BoundExpression boundExpression) {
        this.name = name;
        this.expression = boundExpression;
    }

    @Override
    public Class getType() {
        return expression.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.AssignmentExpression;
    }

    public String getName() {
        return name;
    }

    public BoundExpression getExpression() {
        return expression;
    }
    
}
