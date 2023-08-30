package com.JsonAjax.justcompiler;

public class EvaluationResult {
    private DiagnosticsBag diagnostics;
    private Object value;


    public EvaluationResult(DiagnosticsBag diagnostics, Object value) {
        this.diagnostics = diagnostics;
        this.value = value;
    }


    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }


    public Object getValue() {
        return value;
    }
    
}
