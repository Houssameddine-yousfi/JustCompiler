/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler.Parcing;

import com.JsonAjax.justcompiler.SyntaxToken;
import java.util.ArrayList;

/**
 *
 * @author ajax
 */
public class SyntaxTree {
    private ExpressionSyntax root;
    private SyntaxToken endOfFile;
    private ArrayList<String> diagnostics;

    public SyntaxTree(ArrayList<String> diagnostics, ExpressionSyntax root, SyntaxToken endOfFile) {
        this.root = root;
        this.endOfFile = endOfFile;
        this.diagnostics = diagnostics;
    }

    public ExpressionSyntax getRoot() {
        return root;
    }

    public SyntaxToken getEndOfFile() {
        return endOfFile;
    }

    public ArrayList<String> getDiagnostics() {
        return diagnostics;
    }
    
    
    
    
    
}
