package com.JsonAjax.justcompiler;

public class VariableSymbol {
    private String name;
    private Class type;
    private boolean isReadOnly;


    public VariableSymbol(String name, boolean isReadOnly ,Class type) {
        this.name = name;
        this.type = type;
        this.isReadOnly = isReadOnly;
    }


    public String getName() {
        return name;
    }


    public Class getType() {
        return type;
    }


    public boolean isReadOnly() {
        return isReadOnly;
    }

    
    
}
