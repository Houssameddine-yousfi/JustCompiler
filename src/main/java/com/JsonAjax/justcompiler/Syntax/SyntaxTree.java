/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JsonAjax.justcompiler.Syntax;
import java.util.ArrayList;
import java.util.List;

import com.JsonAjax.justcompiler.DiagnosticsBag;

/**
 *
 * @author ajax
 */
public class SyntaxTree {
    private ExpressionSyntax root;
    private SyntaxToken endOfFile;
    private DiagnosticsBag diagnostics;

    public SyntaxTree(DiagnosticsBag diagnostics, ExpressionSyntax root, SyntaxToken endOfFile) {
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

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }
    
    public static List<SyntaxToken> ParseTokens(String text){
        Lexer lexer = new Lexer(text);
        ArrayList<SyntaxToken> tokens = new ArrayList<>();
        while(true){
            SyntaxToken token = lexer.nextToken();
            if(token.kind() == SyntaxKind.endOfFile)
                break;
            
            tokens.add(token);
        }
        return tokens;
    }
    
    
    
}
