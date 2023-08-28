/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;

import com.JsonAjax.justcompiler.Diagnostic;
import com.JsonAjax.justcompiler.DiagnosticsBag;

/**
 *
 * @author ajax
 */
public class Parser {
    
    private List<SyntaxToken> tokens = new ArrayList<>();
    private DiagnosticsBag diagnostics = new DiagnosticsBag();
    
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
        
        this.diagnostics.addAll(lexer.getDiagnostics());
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

    private ExpressionSyntax parseExpression(int parentPrecedence){
        ExpressionSyntax left; 
        int unaryOperatorPrecedence = SyntaxFacts.GetUnaryOperatorPrecedence(current().kind());

        if(unaryOperatorPrecedence != 0 && unaryOperatorPrecedence >= parentPrecedence){
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax operand = parseExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionSyntax(operatorToken, operand);
        }else{
            left = parsePrimayExpression();
        }

        while(true){
            int precedence = SyntaxFacts.GetBinaryOperatorPrecedence(current().kind());
            if(precedence == 0 || precedence <= parentPrecedence)
                break;
            
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }

        return left;
    }



    
    private SyntaxToken matchToken(SyntaxKind kind){
        if(current().kind() == kind)
            return nextToken();
        
        this.diagnostics.reportUnexpectedToken(current().getSpan(),current().kind(), kind);
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }
    
    public SyntaxTree parse(){
        ExpressionSyntax expressionSyntax = parseExpression(0);
        SyntaxToken endOfFile = matchToken(SyntaxKind.endOfFile);
        return new SyntaxTree(this.diagnostics, expressionSyntax, endOfFile);
    }
    
    private ExpressionSyntax parsePrimayExpression(){

        switch(current().kind()){
            case leftParen:
                SyntaxToken left = nextToken();
                ExpressionSyntax expression = parseExpression(0);
                SyntaxToken right = matchToken(SyntaxKind.rightParen);
                return new ParenthesizedExpressionSyntax(left, expression, right);
            case trueKeyword:
            case falseKeyword:
                SyntaxToken keywordToken = nextToken();
                Boolean value = keywordToken.kind() == SyntaxKind.trueKeyword;
                return new LiteralExpressionSyntax(keywordToken, value);
            default:
                SyntaxToken numberToken = matchToken(SyntaxKind.number);
                return new LiteralExpressionSyntax(numberToken,numberToken.getValue());
        }
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }
    
    
    
}
