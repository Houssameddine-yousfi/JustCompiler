/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import com.JsonAjax.justcompiler.TextSpan;

/**
 *
 * @author hyousfi
 */
public class SyntaxToken {
    
    private SyntaxKind syntaxKind;
    private int position;
    private String text;
    private Object value;

    public SyntaxToken(SyntaxKind syntaxKind, int position, String text, Object value) {
        this.syntaxKind = syntaxKind;
        this.position = position;
        this.text = text;
        this.value = value;
    }

    public SyntaxKind kind() {
        return syntaxKind;
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public Object getValue() {
        return value;
    }

    public TextSpan getSpan(){
        return new TextSpan(position, this.text.length());
    }

    @Override
    public String toString() {
        return "[" + syntaxKind + "," + text + "]";
    }
    
    
    
    
}
