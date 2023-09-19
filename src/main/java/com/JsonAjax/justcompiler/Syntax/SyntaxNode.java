/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

import com.JsonAjax.justcompiler.Text.TextSpan;


/**
 *
 * @author ajax
 */
public abstract class SyntaxNode{
    public abstract SyntaxKind kind();
    public abstract void prettyPrint(String indentation,PrintStream printStream);
    public abstract List<SyntaxNode> getChildren();
    public TextSpan getSpan(){
        TextSpan first = getChildren().get(0).getSpan();
        TextSpan last  = getChildren().get(getChildren().size() - 1).getSpan();
        return TextSpan.fromBounds(first.getStart(),last.getEnd());
    }
}
