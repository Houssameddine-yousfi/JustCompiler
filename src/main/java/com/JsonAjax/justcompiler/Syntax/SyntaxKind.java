/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

/**
 *
 * @author hyousfi
 */
public enum SyntaxKind {
    // Tokens
    badToken,
    endOfFile, 
    whiteSpace,
    number,
    plus,
    minus,
    slash,
    star,
    leftParen,
    rightParen,
    bang,
    equals,
    ampersandAmpersand, 
    pipePipe,
    bangEquals, 
    equalsEquals,
    lessOrEquals, 
    less, 
    greaterOrEquals, 
    greater,
    leftBrace, 
    rightBrace,
    identifierToken,


    // keywords
    trueKeyword,
    falseKeyword,
    letKeyword, 
    varKeyword,
    ifKeyword, 
    elseKeyword,

    
    // Statments
    blockStatment,
    expressionStatment,
    variableDeclaration, 
    ifStatement,

    // Expressions
    binaryExpression,
    unaryExpression,
    literalExpression, 
    parenthesizedExpression, 
    nameExpression, 
    assignmentExpression,

    // Nodes
    compilationUnit,
    elseClause,     
}
