package com.JsonAjax.justcompiler.Syntax;

import java.util.List;

public class CompilationUnitSyntax extends SyntaxNode {

    private StatementSyntax statement;
    private SyntaxToken endOfFileToken;

    

    public CompilationUnitSyntax(StatementSyntax statement, SyntaxToken endOfFileToken) {
        this.statement = statement;
        this.endOfFileToken = endOfFileToken;
    }

    public StatementSyntax getStatement() {
        return statement;
    }

    public SyntaxToken getEndOfFileToken() {
        return endOfFileToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.compilationUnit;
    }



    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(statement);
    }
    
}
