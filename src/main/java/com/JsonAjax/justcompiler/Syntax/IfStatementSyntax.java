package com.JsonAjax.justcompiler.Syntax;

import java.util.List;

public class IfStatementSyntax extends StatementSyntax{


    SyntaxToken ifKeyword;
    ExpressionSyntax condition;
    StatementSyntax thenStatment;
    ElseClauseSyntax elseClause;

    

    public IfStatementSyntax(SyntaxToken ifKeyword, ExpressionSyntax condition, StatementSyntax thenStatment,
            ElseClauseSyntax elseClause) {
        this.ifKeyword = ifKeyword;
        this.condition = condition;
        this.thenStatment = thenStatment;
        this.elseClause = elseClause;
    }

    

    public IfStatementSyntax(SyntaxToken ifKeyword, ExpressionSyntax condition, StatementSyntax thenStatment) {
        this.ifKeyword = ifKeyword;
        this.condition = condition;
        this.thenStatment = thenStatment;
    }

    

    public SyntaxToken getIfKeyword() {
        return ifKeyword;
    }



    public ExpressionSyntax getCondition() {
        return condition;
    }



    public StatementSyntax getThenStatment() {
        return thenStatment;
    }



    public ElseClauseSyntax getElseClause() {
        return elseClause;
    }



    @Override
    public SyntaxKind kind() {
        return SyntaxKind.ifStatement;
    }



    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(ifKeyword,condition,thenStatment,elseClause);
    }
    
}
