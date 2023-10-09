/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;
import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.Text.SourceText;

/**
 *
 * @author ajax
 */
public class Parser {
    
    private SourceText text;
    private List<SyntaxToken> tokens = new ArrayList<>();
    private DiagnosticsBag diagnostics = new DiagnosticsBag();
    
    private int position = 0;

    public Parser(SourceText text) {
        this.text = text;
        Lexer lexer = new Lexer(text.toString());
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

    private StatementSyntax parseStatement(){

        switch(current().kind()){
            case leftBrace:
                return parseBlockStatement();
            case letKeyword:
            case varKeyword:
                return parseVariableDeclaration();
            case ifKeyword:
                return parseIfStatment();
            case whileKeyword:
                return parseWhileKeyword();
            case forKeyword:
                return parseForKeyword();
            default:
                return parseExpressionStatment();
        }

    }

    
    private StatementSyntax parseBlockStatement(){
        List<StatementSyntax> statements = new ArrayList<>();
        SyntaxToken leftBraceToken = matchToken(SyntaxKind.leftBrace);

        while(current().kind() != SyntaxKind.endOfFile 
            && current().kind() != SyntaxKind.rightBrace){
                StatementSyntax statement = parseStatement();
                statements.add(statement);
        }

        SyntaxToken rightBraceToken = matchToken(SyntaxKind.rightBrace);
        return new BlockStatmentSyntax(leftBraceToken, statements, rightBraceToken);
    }

    private StatementSyntax parseVariableDeclaration() {
        SyntaxKind expected = current().kind() == SyntaxKind.letKeyword? SyntaxKind.letKeyword: SyntaxKind.varKeyword;
        SyntaxToken keyword = matchToken(expected);
        SyntaxToken identifier = matchToken(SyntaxKind.identifierToken);
        SyntaxToken equals = matchToken(SyntaxKind.equals);
        ExpressionSyntax initializer = parseExpression();
        return new VariableDeclarationSyntax(keyword, identifier, equals, initializer);
    }
    
    private StatementSyntax parseExpressionStatment(){
        ExpressionSyntax expression = parseExpression();
        return new ExpressionStatementSyntax(expression);
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
    
    public CompilationUnitSyntax parseCompilationUnit(){
        StatementSyntax statementSyntax = parseStatement();
        SyntaxToken endOfFile = matchToken(SyntaxKind.endOfFile);
        return new CompilationUnitSyntax(statementSyntax, endOfFile);
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

    private IfStatementSyntax parseIfStatment() {
        SyntaxToken keyword = matchToken(SyntaxKind.ifKeyword);
        ExpressionSyntax condition  = parseExpression();
        StatementSyntax statment = parseStatement();
        ElseClauseSyntax elseClause = parseElseClause();

        return new IfStatementSyntax(keyword, condition, statment, elseClause);
    }

    private ElseClauseSyntax parseElseClause() {
        if(current().kind() != SyntaxKind.elseKeyword)
            return null;
        
        SyntaxToken elseKeyword = matchToken(SyntaxKind.elseKeyword);
        StatementSyntax elseStatement = parseStatement();
        return new ElseClauseSyntax(elseKeyword, elseStatement);
    }

    private WhileStatementSyntax parseWhileKeyword() {

        SyntaxToken keyword = matchToken(SyntaxKind.whileKeyword);
        ExpressionSyntax condition = parseExpression();
        StatementSyntax body = parseStatement();
        return new WhileStatementSyntax(keyword,condition,body);
    }

    private ForStatementSyntax parseForKeyword() {
        
        SyntaxToken forKeword = matchToken(SyntaxKind.forKeyword);
        SyntaxToken identifier = matchToken(SyntaxKind.identifierToken);
        SyntaxToken equalsToken = matchToken(SyntaxKind.equals);
        ExpressionSyntax lowerBound = parseExpression() ;
        SyntaxToken toKeyword = matchToken(SyntaxKind.toKeyword);
        ExpressionSyntax upperbound = parseExpression();
        StatementSyntax body = parseStatement();
         
        return new ForStatementSyntax(forKeword,identifier,equalsToken,lowerBound,toKeyword,upperbound,body);
    }


    public DiagnosticsBag getDiagnostics() {
        return diagnostics; 
    }

}
