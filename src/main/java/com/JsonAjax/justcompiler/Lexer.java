/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

import java.util.ArrayList;

/**
 *
 * @author hyousfi
 */
public class Lexer {
    
    private String text;
    private int position;
    private ArrayList<String> diagnostics = new ArrayList<>();
    
    
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
            
            String text = this.text.substring(start, this.position);
            int val = 0;
            try{
                val = Integer.parseInt(text);
            }catch(NumberFormatException e){
                this.diagnostics.add("The number " +text + " isn't a valid Int32." );
            }
            return new SyntaxToken(SyntaxKind.number, start, text, val);
        }
        
        if(Character.isWhitespace(current())){
            int start = this.position;
            while(Character.isWhitespace(current()))
                next();
            
            String text = this.text.substring(start, this.position);
            return new SyntaxToken(SyntaxKind.whiteSpace, start, text, null);
        }

        switch(current()){
            case '+':
                return new SyntaxToken(SyntaxKind.plus, this.position++, "+", null);
            case '-':
                return new SyntaxToken(SyntaxKind.minus, this.position++, "-", null);
            case '*':
                return new SyntaxToken(SyntaxKind.star, this.position++, "*", null);
            case '/':
                return new SyntaxToken(SyntaxKind.slash, this.position++, "/", null);
            case '(':
                return new SyntaxToken(SyntaxKind.leftParen, this.position++, "(", null);
            case ')':
                return new SyntaxToken(SyntaxKind.rightParen, this.position++, ")", null);
        }
        
    
        this.diagnostics.add("Error: bad charachter input '" + current() + "'");
        return new SyntaxToken(SyntaxKind.badToken, 
                this.position++, 
                text.substring(this.position - 1, this.position), 
                null);
    }

    public ArrayList<String> getDiagnostics() {
        return diagnostics;
    }
    
    
}
