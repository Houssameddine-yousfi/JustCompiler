package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;

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
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> list = new ArrayList<>();
        list.add(this.identifierToken);
        return list;
    }

    
    
}
