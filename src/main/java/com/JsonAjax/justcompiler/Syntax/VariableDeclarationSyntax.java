package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

public class VariableDeclarationSyntax extends StatementSyntax{

    SyntaxToken keyword;
    SyntaxToken identifier;
    SyntaxToken equalsToken;
    ExpressionSyntax initializer;
    

    public SyntaxToken getKeyword() {
        return keyword;
    }

    public SyntaxToken getIdentifier() {
        return identifier;
    }

    public SyntaxToken getEqualsToken() {
        return equalsToken;
    }

    public ExpressionSyntax getInitializer() {
        return initializer;
    }

    public VariableDeclarationSyntax(SyntaxToken keyword, SyntaxToken identifier, SyntaxToken equalsToken,
            ExpressionSyntax initializer) {
        this.keyword = keyword;
        this.identifier = identifier;
        this.equalsToken = equalsToken;
        this.initializer = initializer;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.variableDeclaration;
    }

    @Override
    public void prettyPrint(String indentation, PrintStream printStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    public List<SyntaxNode> getChildren() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChildren'");
    }


    
}
