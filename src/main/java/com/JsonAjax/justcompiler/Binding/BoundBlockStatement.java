package com.JsonAjax.justcompiler.Binding;

import java.util.List;

public class BoundBlockStatement extends BoundStatement{

    private List<BoundStatement> statements;

    
    public BoundBlockStatement(List<BoundStatement> statements) {
        this.statements = statements;
    }

    

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.blockStatment;
    }



    public List<BoundStatement> getStatements() {
        return statements;
    }
    
}
