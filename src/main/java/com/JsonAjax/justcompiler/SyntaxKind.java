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
public enum SyntaxKind {
    number,
    whiteSpace,
    plus,
    minus,
    slash,
    star,
    leftParen,
    rightParen,
    badToken,
    endOfFile, 
    binaryExpression, numberExpression
}
