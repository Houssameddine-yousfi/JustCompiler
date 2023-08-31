package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundVariableExpression extends BoundExpression{

    private VariableSymbol variable;


    public BoundVariableExpression(VariableSymbol variable) {
        this.variable=variable;
    }

    @Override
    public Class getType() {
        return variable.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.VariableExpression;
    }

    public String getName() {
        return variable.getName();
    }

    public VariableSymbol getVariable() {
        return variable;
    }

    
    
}
