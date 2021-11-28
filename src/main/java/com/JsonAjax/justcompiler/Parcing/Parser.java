/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.Lexer;
import com.JsonAjax.justcompiler.SyntaxKind;
import com.JsonAjax.justcompiler.SyntaxToken;
import java.util.ArrayList;

/**
 *
 * @author ajax
 */
public class Parser {
    
    ArrayList<SyntaxToken> tokens = new ArrayList<>();
    private int position = 0;

    public Parser(String text) {
        Lexer lexer = new Lexer(text);
        SyntaxToken token = null;
        do{
            token = lexer.nextToken();
            
            if(token.kind() != SyntaxKind.whiteSpace && 
                    token.kind() != SyntaxKind.badToken)
                tokens.add(token);
            
        }while(token.kind() != SyntaxKind.endOfFile );
    }
    
    private SyntaxToken peek(int offset){
        int index = position + offset;
        if(index >= tokens.size())
            return tokens.get(tokens.size() - 1);
        
        return tokens.get(index);
    }
    
    private SyntaxToken current(){
        return peek(0);
    }
    
    private SyntaxToken nextToken(){
        SyntaxToken current = this.current();
        position ++;
        return current;
    } 
    
    private SyntaxToken match(SyntaxKind kind){
        if(current().kind() == kind)
            return nextToken();
        
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }
    
    public ExpressionSyntax parse(){
        ExpressionSyntax left = parsePrimayExpression();
        
        while (current().kind() == SyntaxKind.plus ||
                current().kind() == SyntaxKind.minus ){
            
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parsePrimayExpression();
            left = new BinaryExpressionSyntax(left, operatorToken, right);
            
        }
        
        return left;
    }
    
    private ExpressionSyntax parsePrimayExpression(){
        SyntaxToken numberToken = match(SyntaxKind.number);
        return new NumberExpressionSyntax(numberToken);
    }
    
}
