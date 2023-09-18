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
    private ExpressionSyntax root;
    private SyntaxToken endOfFile;
    private DiagnosticsBag diagnostics;

    public SyntaxTree(SourceText text, DiagnosticsBag diagnostics, ExpressionSyntax root, SyntaxToken endOfFile) {
        this.root = root;
        this.endOfFile = endOfFile;
        this.diagnostics = diagnostics;
        this.text = text;
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

    public static SyntaxTree parse(SourceText text){
        Parser parser = new Parser(text);
        return parser.parse();
    }
    

    public static SyntaxTree parse(String text){
        SourceText sourceText = SourceText.from(text);
        Parser parser = new Parser(sourceText);
        return parser.parse();
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
