package com.JsonAjax.justcompiler.Syntax;

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
    public List<SyntaxNode> getChildren() {
        return List.of(elseKeyword,elseStatement);
    }
    
}
