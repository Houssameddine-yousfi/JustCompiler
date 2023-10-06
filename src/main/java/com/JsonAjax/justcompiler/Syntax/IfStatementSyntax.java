package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.ArrayList;
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
    public void prettyPrint(String indentation, PrintStream printStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(ifKeyword,condition,thenStatment,elseClause);
    }
    
}
