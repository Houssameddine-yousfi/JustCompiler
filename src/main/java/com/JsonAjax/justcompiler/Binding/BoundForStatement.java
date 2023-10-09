package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundForStatement extends BoundStatement{

    private VariableSymbol variable;
    private BoundExpression lowerBound;
    private BoundExpression upperBound;
    private BoundStatement body;
    
    
    
    public BoundForStatement(VariableSymbol variable, BoundExpression lowerBound, BoundExpression upperBound,
            BoundStatement body) {
        this.variable = variable;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.body = body;
    }

    


    public VariableSymbol getVariable() {
        return variable;
    }




    public BoundExpression getLowerBound() {
        return lowerBound;
    }




    public BoundExpression getUpperBound() {
        return upperBound;
    }




    public BoundStatement getBody() {
        return body;
    }




    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.forStatement;
    }
    
}
