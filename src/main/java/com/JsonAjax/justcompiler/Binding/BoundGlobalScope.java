package com.JsonAjax.justcompiler.Binding;

import java.util.List;

import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundGlobalScope {
    
    private DiagnosticsBag diagnostics;
    private List<VariableSymbol> variables;
    private BoundStatement statement;
    private BoundGlobalScope previous;
    
    
    public BoundGlobalScope(BoundGlobalScope previous, DiagnosticsBag diagnostics, List<VariableSymbol> variables, BoundStatement statement) {
        this.diagnostics = diagnostics;
        this.variables = variables;
        this.statement = statement;
        this.previous = previous;
    }


    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }


    public List<VariableSymbol> getVariables() {
        return variables;
    }


    public BoundStatement getStatement() {
        return statement;
    }


    public BoundGlobalScope getPrevious() {
        return previous;
    }
    
    

    
}
