/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.SyntaxKind;
import com.JsonAjax.justcompiler.SyntaxToken;

/**
 *
 * @author ajax
 */
public class NumberExpressionSyntax extends ExpressionSyntax {

    SyntaxToken numberToken;

    public NumberExpressionSyntax(SyntaxToken numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.numberExpression;
    }

    public SyntaxToken getNumberToken() {
        return numberToken;
    }

    @Override
    public void prettyPrint(String indentation) {
        System.out.println( numberToken.kind() + 
                " " + numberToken.getValue());
    }
    
    
}
