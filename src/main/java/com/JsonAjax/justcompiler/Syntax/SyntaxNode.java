/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.util.List;


/**
 *
 * @author ajax
 */
public abstract class SyntaxNode{
    public abstract SyntaxKind kind();
    public abstract void prettyPrint(String indentation);
    public abstract List<SyntaxNode> getChildren();
}
