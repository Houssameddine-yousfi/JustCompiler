package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

public class WhileStatementSyntax extends StatementSyntax{

    private SyntaxToken whileKeyword;
    private ExpressionSyntax condition;
    private StatementSyntax body;

    public WhileStatementSyntax(SyntaxToken whileKeyword, ExpressionSyntax condition, StatementSyntax body) {
        this.whileKeyword = whileKeyword;
        this.condition = condition;
        this.body = body;
    }

    

    public SyntaxToken getWhileKeyword() {
        return whileKeyword;
    }



    public ExpressionSyntax getCondition() {
        return condition;
    }



    public StatementSyntax getBody() {
        return body;
    }



    @Override
    public SyntaxKind kind() {
        return SyntaxKind.whileStatement;
    }

    @Override
    public void prettyPrint(String indentation, PrintStream printStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(whileKeyword,condition,body);
    }

}
