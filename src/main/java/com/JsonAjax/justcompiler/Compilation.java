package com.JsonAjax.justcompiler;

import java.util.Map;

import com.JsonAjax.justcompiler.Binding.Binder;
import com.JsonAjax.justcompiler.Binding.BoundExpression;
import com.JsonAjax.justcompiler.Binding.BoundGlobalScope;
import com.JsonAjax.justcompiler.Syntax.SyntaxToken;
import com.JsonAjax.justcompiler.Syntax.SyntaxTree;

public class Compilation {
    
    private SyntaxTree syntax;
    private Compilation previous;
    private BoundGlobalScope globalScope;

    public Compilation(SyntaxTree syntax) {
        this(null, syntax);
    }

    private Compilation(Compilation previous, SyntaxTree syntax){
        this.syntax = syntax;
        this.previous = previous;
    }

    public SyntaxTree getSyntax() {
        return syntax;
    }

    public Compilation continueWith(SyntaxTree syntax ){
        return new Compilation(this, syntax);
    }

    public EvaluationResult evaluate(Map<VariableSymbol,Object> variables){
        
        
        try {
            if (globalScope == null)
                if(previous == null)
                    globalScope = Binder.bindGlobalScope(null, syntax.getRoot());
                else
                    globalScope = Binder.bindGlobalScope(previous.globalScope, syntax.getRoot());
            syntax.getDiagnostics().addAll(globalScope.getDiagnostics());
        } catch (Exception e) {
            e.printStackTrace();
            return new EvaluationResult(syntax.getDiagnostics(),null);
        }
        
        

        DiagnosticsBag diagnostics = syntax.getDiagnostics();

        if(!diagnostics.isEmpty())
            return new EvaluationResult(diagnostics,null);
        
        Evaluator evaluator = new Evaluator(globalScope.getStatement(), variables);
        
        Object value;
        try{
            value = evaluator.evaluate();
        }catch (Exception e ){
            e.printStackTrace();
            value = 0;
        };
        return new EvaluationResult(diagnostics, value);
    }
}
