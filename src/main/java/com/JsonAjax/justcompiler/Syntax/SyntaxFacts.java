package com.JsonAjax.justcompiler.Syntax;

public class SyntaxFacts {
    
    protected static int GetBinaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case star:
            case slash:
                return 2;

            case plus:
            case minus:
                return 1;

            default:
                return 0;
        }
    }

    protected static int GetUnaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case plus:
            case minus:
                return 3;

            default:
                return 0;
        }
    }

    public static SyntaxKind getKeywordKind(String text) {
        
        switch (text){
            case "true":
                return SyntaxKind.trueKeyword;
            case "false":
                return SyntaxKind.falseKeyword;
            default:
                return SyntaxKind.IdentifierToken;
        }

    }
}
