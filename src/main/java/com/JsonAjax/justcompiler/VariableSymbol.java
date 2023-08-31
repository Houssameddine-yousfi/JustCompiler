package com.JsonAjax.justcompiler;

public class VariableSymbol {
    private String name;
    private Class type;


    public VariableSymbol(String name, Class type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }


    public Class getType() {
        return type;
    }

    
    
}
