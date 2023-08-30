package com.JsonAjax.justcompiler.Binding;

public class BoundVariableExpression extends BoundExpression{

    private String name;
    private Class type;


    public BoundVariableExpression(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Class getType() {
        return type;
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.VariableExpression;
    }

    public String getName() {
        return name;
    }
    
}
