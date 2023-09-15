/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ajax
 */
public class BinaryExpressionSyntax extends ExpressionSyntax{

    private ExpressionSyntax left;
    private ExpressionSyntax right;
    private SyntaxToken operatorToken;

    public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operatorToken, ExpressionSyntax right) {
        this.left = left;
        this.right = right;
        this.operatorToken = operatorToken;
    }
    
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.binaryExpression;
    }
    
    @Override
    public void prettyPrint(String indentation,PrintStream printStream){
        printStream.println( "BinaryExpression");
        printStream.print(indentation+"├──");
        left.prettyPrint("│   " + indentation, printStream);
        printStream.println(indentation+"├──" + operatorToken.kind());
        printStream.print(indentation+"└──");
        right.prettyPrint(indentation + "    " , printStream);   
    }

    public ExpressionSyntax getLeft() {
        return left;
    }

    public ExpressionSyntax getRight() {
        return right;
    }

    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }


    @Override
    public List<SyntaxNode> getChildren() {
        List<SyntaxNode> list = new ArrayList<>();
        list.add(this.left);
        list.add(this.operatorToken);
        list.add(this.right);
        return list;
    }
    
    
}
