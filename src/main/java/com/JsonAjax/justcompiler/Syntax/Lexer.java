/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;

import com.JsonAjax.justcompiler.Diagnostic;
import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.TextSpan;

/**
 *
 * @author hyousfi
 */
public class Lexer {
    
    private String text;
    private int position;
    private DiagnosticsBag diagnostics = new DiagnosticsBag();
    
    
    private char current(){
        return peek(0);
    }

    private char lookahead(){
        return peek(1);
    }

    private char peek(int offset){
        int index = this.position + offset;
        if(index >= text.length())
            return '\0';
        
        return text.charAt(index);
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
        
        var start = this.position;

        if(Character.isDigit(current())){
            
            while(Character.isDigit(current()))
                next();
            
            String text = this.text.substring(start, this.position);
            int val = 0;
            try{
                val = Integer.parseInt(text);
            }catch(NumberFormatException e){
                this.diagnostics.reportInvalidNumber(new TextSpan(start, this.position-start),text,Integer.class );
            }
            return new SyntaxToken(SyntaxKind.number, start, text, val);
        }
        
        if(Character.isWhitespace(current())){
            
            while(Character.isWhitespace(current()))
                next();
            
            String text = this.text.substring(start, this.position);
            return new SyntaxToken(SyntaxKind.whiteSpace, start, text, null);
        }

        if(Character.isLetter(current())){
            
            while(Character.isLetter(current()))
                next();
            
            String text = this.text.substring(start, this.position);
            SyntaxKind kind =  SyntaxFacts.getKeywordKind(text);
            return new SyntaxToken(kind, start, text, null);
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
            
            case '!':
                if(lookahead() == '='){
                    this.position+=2;
                    return new SyntaxToken(SyntaxKind.bangEquals, start, "!=", null);
                }
                else
                    return new SyntaxToken(SyntaxKind.bang, this.position++, "!", null);
            case '&':
                if(lookahead() == '&'){
                    this.position+=2;
                    return new SyntaxToken(SyntaxKind.ampersandAmpersand, start, "&&", null);
                }
                break;
            case '|':
                if(lookahead() == '|'){
                    this.position+=2;
                    return new SyntaxToken(SyntaxKind.pipePipe, start, "||", null);
                }
                break;
            case '=':
                if(lookahead() == '='){
                    this.position+=2;
                    return new SyntaxToken(SyntaxKind.equalsEquals, start, "==", null);
                }
                break;
        }
        
    
        this.diagnostics.reportBadCharacter(position,current());
        return new SyntaxToken(SyntaxKind.badToken, 
                this.position++, 
                text.substring(this.position - 1, this.position), 
                null);
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }
    
    
}
