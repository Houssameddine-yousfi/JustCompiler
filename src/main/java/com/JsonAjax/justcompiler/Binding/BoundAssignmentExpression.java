package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundAssignmentExpression extends BoundExpression {

    private VariableSymbol variable;
    private BoundExpression expression;

    public BoundAssignmentExpression(VariableSymbol variable, BoundExpression boundExpression) {
        this.variable = variable;
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
        return variable.getName();
    }

    public BoundExpression getExpression() {
        return expression;
    }

    public VariableSymbol getVariable() {
        return variable;
    }
    
}
