package com.JsonAjax.justcompiler;

import java.util.Map;

import com.JsonAjax.justcompiler.Binding.Binder;
import com.JsonAjax.justcompiler.Binding.BoundExpression;
import com.JsonAjax.justcompiler.Syntax.SyntaxTree;

public class Compilation {
    private SyntaxTree syntax;

    public Compilation(SyntaxTree syntax) {
        this.syntax = syntax;
    }

    public SyntaxTree getSyntax() {
        return syntax;
    }

    public EvaluationResult evaluate(Map<VariableSymbol,Object> variables){
        Binder binder = new Binder(variables);
        BoundExpression boundExpression;
        try {
            boundExpression = binder.bindExpression(syntax.getRoot().getExpression());
        } catch (Exception e) {
            e.printStackTrace();
            return new EvaluationResult(binder.getDiagnostics(),null);
        }
        
        syntax.getDiagnostics().addAll(binder.getDiagnostics());
        DiagnosticsBag diagnostics = syntax.getDiagnostics();

        if(!diagnostics.isEmpty())
            return new EvaluationResult(diagnostics,null);
        
        Evaluator evaluator = new Evaluator(boundExpression, variables);
        Object value = evaluator.evaluate();
        return new EvaluationResult(diagnostics, value);
    }
}
