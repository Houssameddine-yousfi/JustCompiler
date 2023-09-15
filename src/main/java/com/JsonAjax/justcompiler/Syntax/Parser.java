/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;
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


    private ExpressionSyntax parseExpression(){
        return parseAssignmentExpression();
    }

    private ExpressionSyntax parseAssignmentExpression(){
        if (peek(0).kind() == SyntaxKind.identifierToken &&
            peek(1).kind() == SyntaxKind.equals){
            SyntaxToken identifierToken = nextToken();
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parseAssignmentExpression();
            return new AssignmentExpressionSyntax(identifierToken, operatorToken, right);
        }
        
        return parseBinaryExpression();
    }

    private ExpressionSyntax parseBinaryExpression(){
        return parseBinaryExpression(0);
    }

    private ExpressionSyntax parseBinaryExpression(int parentPrecedence){
        ExpressionSyntax left; 
        int unaryOperatorPrecedence = SyntaxFacts.getUnaryOperatorPrecedence(current().kind());

        if(unaryOperatorPrecedence != 0 && unaryOperatorPrecedence >= parentPrecedence){
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax operand = parseBinaryExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionSyntax(operatorToken, operand);
        }else{
            left = parsePrimayExpression();
        }

        while(true){
            int precedence = SyntaxFacts.getBinaryOperatorPrecedence(current().kind());
            if(precedence == 0 || precedence <= parentPrecedence)
                break;
            
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parseBinaryExpression(precedence);
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
        ExpressionSyntax expressionSyntax = parseExpression();
        SyntaxToken endOfFile = matchToken(SyntaxKind.endOfFile);
        return new SyntaxTree(this.diagnostics, expressionSyntax, endOfFile);
    }
    
    private ExpressionSyntax parsePrimayExpression(){
        switch(current().kind()){
            case leftParen:
                return parseParenthesisedExpression();
            case trueKeyword:
            case falseKeyword:
                return parseBooleanLiteral();
            case identifierToken:
                return parseNameExpression();
            default:
                return parseNumberLiteral();
        }
    }

    private ExpressionSyntax parseNumberLiteral() {
        SyntaxToken numberToken = matchToken(SyntaxKind.number);
        return new LiteralExpressionSyntax(numberToken,numberToken.getValue());
    }

    private ExpressionSyntax parseParenthesisedExpression() {
        SyntaxToken left = nextToken();
        ExpressionSyntax expression = parseExpression();
        SyntaxToken right = matchToken(SyntaxKind.rightParen);
        return new ParenthesizedExpressionSyntax(left, expression, right);
    }

    private ExpressionSyntax parseBooleanLiteral() {
        SyntaxToken keywordToken = nextToken();
        Boolean value = keywordToken.kind() == SyntaxKind.trueKeyword;
        return new LiteralExpressionSyntax(keywordToken, value);
    }

    private ExpressionSyntax parseNameExpression() {
        SyntaxToken identifierToken = matchToken(SyntaxKind.identifierToken);
        return new NameExpressionSyntax(identifierToken);
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics; 
    }
    
    
    
}
