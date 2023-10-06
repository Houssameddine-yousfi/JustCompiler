package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

public class ElseClauseSyntax extends SyntaxNode {

    SyntaxToken elseKeyword;
    StatementSyntax elseStatement; 

    

    public ElseClauseSyntax(SyntaxToken elseKeyword, StatementSyntax elseStatement) {
        this.elseKeyword = elseKeyword;
        this.elseStatement = elseStatement;
    }

    

    public SyntaxToken getElseKeyword() {
        return elseKeyword;
    }



    public StatementSyntax getElseStatement() {
        return elseStatement;
    }



    @Override
    public SyntaxKind kind() {
        return SyntaxKind.elseClause;
    }

    @Override
    public void prettyPrint(String indentation, PrintStream printStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(elseKeyword,elseStatement);
    }
    
}
