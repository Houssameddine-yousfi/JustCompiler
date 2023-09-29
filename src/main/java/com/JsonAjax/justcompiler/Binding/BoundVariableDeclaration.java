package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundVariableDeclaration extends BoundStatement{

    VariableSymbol variable;
    BoundExpression initialiser;

    
    
    public VariableSymbol getVariable() {
        return variable;
    }



    public BoundExpression getInitialiser() {
        return initialiser;
    }



    public BoundVariableDeclaration(VariableSymbol variable, BoundExpression initialiser) {
        this.variable = variable;
        this.initialiser = initialiser;
    }



    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.BoundVariableDeclaration;
    }
    
}
