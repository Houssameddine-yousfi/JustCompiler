package com.JsonAjax.justcompiler.Binding;

import java.util.List;

import com.JsonAjax.justcompiler.Diagnostic;
import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundGlobalScope {
    
    private DiagnosticsBag diagnostics;
    private List<VariableSymbol> variables;
    private BoundExpression expression;
    private BoundGlobalScope previous;
    
    
    public BoundGlobalScope(BoundGlobalScope previous, DiagnosticsBag diagnostics, List<VariableSymbol> variables, BoundExpression expression) {
        this.diagnostics = diagnostics;
        this.variables = variables;
        this.expression = expression;
        this.previous = previous;
    }


    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }


    public List<VariableSymbol> getVariables() {
        return variables;
    }


    public BoundExpression getExpression() {
        return expression;
    }


    public BoundGlobalScope getPrevious() {
        return previous;
    }
    
    

    
}
