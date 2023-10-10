package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;

public class BlockStatmentSyntax extends StatementSyntax{
    
    private SyntaxToken openBraceToken;
    private List<StatementSyntax> statements;
    private SyntaxToken closeBraceToken;
    
    
    public BlockStatmentSyntax(SyntaxToken openBraceToken, List<StatementSyntax> statements,
            SyntaxToken closeBraceToken) {
        this.openBraceToken = openBraceToken;
        this.statements = statements;
        this.closeBraceToken = closeBraceToken;
    }


    public SyntaxToken getOpenBraceToken() {
        return openBraceToken;
    }


    public List<StatementSyntax> getStatements() {
        return statements;
    }


    public SyntaxToken getCloseBraceToken() {
        return closeBraceToken;
    }


    @Override
    public SyntaxKind kind() {
        return SyntaxKind.blockStatment;
    }





    @Override
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> children = new ArrayList<>();
        children.add( openBraceToken);
        children.addAll(statements);
        children.add( closeBraceToken);
        return children; 
    }

    

    
}
