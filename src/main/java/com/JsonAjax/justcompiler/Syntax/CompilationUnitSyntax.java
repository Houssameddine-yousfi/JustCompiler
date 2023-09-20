package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

public class CompilationUnitSyntax extends SyntaxNode {

    private ExpressionSyntax expression;
    private SyntaxToken endOfFileToken;

    

    public CompilationUnitSyntax(ExpressionSyntax expression, SyntaxToken endOfFileToken) {
        this.expression = expression;
        this.endOfFileToken = endOfFileToken;
    }

    public ExpressionSyntax getExpression() {
        return expression;
    }

    public SyntaxToken getEndOfFileToken() {
        return endOfFileToken;
    }

    @Override
    public SyntaxKind kind() {
        return SyntaxKind.compilationUnit;
    }

    @Override
    public void prettyPrint(String indentation, PrintStream printStream) {
        printStream.println( "CompilationUnit");
        
        printStream.print(indentation+"└──");
        expression.prettyPrint(indentation + "    ", printStream);
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(expression);
    }
    
}
