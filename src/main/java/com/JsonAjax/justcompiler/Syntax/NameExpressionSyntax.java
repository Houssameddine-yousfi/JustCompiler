package com.JsonAjax.justcompiler.Syntax;

public class NameExpressionSyntax extends ExpressionSyntax {

    SyntaxToken identifierToken;

    
    public NameExpressionSyntax(SyntaxToken identifierToken) {
        this.identifierToken = identifierToken;
    }


    public SyntaxToken getIdentifierToken() {
        return identifierToken;
    }


    @Override
    public SyntaxKind kind() {
        return SyntaxKind.nameExpression;
    }


    @Override
    public void prettyPrint(String indentation) {
        System.out.println( identifierToken.kind() + 
                " " + identifierToken.getValue());
    }

    
    
}
