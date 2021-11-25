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
        if(this.position >= text.length())
            return new SyntaxToken(SyntaxKind.endOfFile, position, "\0", null);
        
        if(Character.isDigit(current())){
            int start = this.position;
            while(Character.isDigit(current()))
                next();
            
            var text = this.text.substring(start, this.position);
            int val = Integer.parseInt(text);
            return new SyntaxToken(SyntaxKind.number, start, text, val);
        }
        
        if(Character.isWhitespace(current())){
            int start = this.position;
            while(Character.isWhitespace(current()))
                next();
            
            var text = this.text.substring(start, this.position);
            return new SyntaxToken(SyntaxKind.whiteSpace, start, text, null);
        }
        if(current() == '+')
            return new SyntaxToken(SyntaxKind.plus, this.position++, "+", null);
        if(current() == '-')
            return new SyntaxToken(SyntaxKind.minus, this.position++, "-", null);
        if(current() == '*')
            return new SyntaxToken(SyntaxKind.star, this.position++, "*", null);
        if(current() == '/')
            return new SyntaxToken(SyntaxKind.slash, this.position++, "/", null);
        if(current() == '(')
            return new SyntaxToken(SyntaxKind.leftParen, this.position++, "(", null);
        if(current() == ')')
            return new SyntaxToken(SyntaxKind.rightParen, this.position++, ")", null);
        
        return new SyntaxToken(SyntaxKind.badToken, 
                this.position++, 
                text.substring(this.position - 1, this.position), 
                null);
    }
    
}
