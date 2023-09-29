/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class SyntaxTree {

    private SourceText text;
    private CompilationUnitSyntax root;
    private SyntaxToken endOfFile;
    private DiagnosticsBag diagnostics;

    private SyntaxTree(SourceText text) {
        Parser parser = new Parser(text);
        CompilationUnitSyntax root  =  parser.parseCompilationUnit();
        diagnostics = parser.getDiagnostics();
        
        this.root = root;
        this.text = text;
    }

    public CompilationUnitSyntax getRoot() {
        return root;
    }

    public SyntaxToken getEndOfFile() {
        return endOfFile;
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }

    public static SyntaxTree parse(SourceText text){
        return new SyntaxTree(text);
    }
    

    public static SyntaxTree parse(String text){
        SourceText sourceText = SourceText.from(text);
        return new SyntaxTree(sourceText);
    }
    
    public static List<SyntaxToken> parseTokens(SourceText text){
        Lexer lexer = new Lexer(text.toString());
        ArrayList<SyntaxToken> tokens = new ArrayList<>();
        while(true){
            SyntaxToken token = lexer.nextToken();
            if(token.kind() == SyntaxKind.endOfFile)
                break;
            
            tokens.add(token);
        }
        return tokens;
    }

    public SourceText getText() {
        return text;
    }
    
    
    
}
