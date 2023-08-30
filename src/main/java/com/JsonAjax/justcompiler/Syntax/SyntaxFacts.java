package com.JsonAjax.justcompiler.Syntax;

public class SyntaxFacts {
    
    protected static int GetBinaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case star:
            case slash:
                return 5;

            case plus:
            case minus:
                return 4;

            case equalsEquals:
            case bangEquals:
                return 3;

            case ampersandAmpersand:
                return 2;

            case pipePipe:
                return 1;

            default:
                return 0;
        }
    }

    protected static int GetUnaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case plus:
            case minus:
            case bang:
                return 6;

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
                return SyntaxKind.identifierToken;
        }

    }
}
