/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

/**
 *
 * @author hyousfi
 */
public class Lexer {
    
    private String text;
    private int position;
    
    
    private char current(){
        if(this.position >= text.length())
            return '\0';
        
        return text.charAt(position);
    }
    
    private void next(){
        position++;
    }

    public Lexer(String text) {
        this.text = text;
    }
    
    public SyntaxToken nextToken(){
        
    }
    
}
