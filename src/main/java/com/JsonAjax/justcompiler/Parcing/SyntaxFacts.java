package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.SyntaxKind;

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
}
