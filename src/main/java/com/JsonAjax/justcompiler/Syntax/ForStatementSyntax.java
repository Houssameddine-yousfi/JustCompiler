package com.JsonAjax.justcompiler.Syntax;

import java.util.List;

public class ForStatementSyntax extends StatementSyntax{
    
    private SyntaxToken forkeyword;  
    private SyntaxToken identifier;
    private SyntaxToken equalsToken;
    private ExpressionSyntax lowerbound;
    private SyntaxToken toKeyword;
    private ExpressionSyntax upperbound;
    private StatementSyntax body;

    

    public ForStatementSyntax(SyntaxToken whilekeyword, SyntaxToken identifier, SyntaxToken equalsToken,
            ExpressionSyntax lowerbound,SyntaxToken toKeyword, ExpressionSyntax upperbound,StatementSyntax body) {
        this.forkeyword = whilekeyword;
        this.identifier = identifier;
        this.equalsToken = equalsToken;
        this.lowerbound = lowerbound;
        this.toKeyword = toKeyword;
        this.upperbound = upperbound;
        this.body = body;
    }
    
    public SyntaxToken getForkeyword() {
        return forkeyword;
    }

    public SyntaxToken getIdentifier() {
        return identifier;
    }

    public SyntaxToken getEqualsToken() {
        return equalsToken;
    }

    public ExpressionSyntax getLowerbound() {
        return lowerbound;
    }

    public ExpressionSyntax getUpperbound() {
        return upperbound;
    }

    public StatementSyntax getBody(){
        return body;
    }

    public SyntaxToken getToKeyword(){
        return toKeyword;
    }
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.forStatement;
    }


    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(forkeyword,identifier,equalsToken,lowerbound,toKeyword,upperbound,body);
    }

    
     
}
