package com.JsonAjax.justcompiler.Binding;

public class BoundLiteralExpression extends BoundExpression{

    private Object value;

    public BoundLiteralExpression(Object value){
        this.value = value;
    }

    @Override
    public Class getType() {
        return value.getClass();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.LiteralExpression;
    }



    public Object getValue() {
        return value;
    }
    
}
