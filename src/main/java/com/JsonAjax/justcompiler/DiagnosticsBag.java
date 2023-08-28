package com.JsonAjax.justcompiler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.JsonAjax.justcompiler.Syntax.SyntaxKind;

public class DiagnosticsBag implements Iterable {
    public Set<Diagnostic> diagnostics = new HashSet<>();

    private void report(TextSpan span, String msg){
        Diagnostic diag = new Diagnostic(span, msg);
        diagnostics.add(diag);
    }
    
    public void addAll(DiagnosticsBag diagnostics){
        this.diagnostics.addAll(diagnostics.diagnostics);
    }
    

    @Override
    public Iterator iterator() {
       return this.diagnostics.iterator();
    }

    public void reportInvalidNumber(TextSpan textSpan, String text, Class type) {
        String message = "The number " + text + " isn't a valid {"+ type +"}.";
        report(textSpan, message);
    }

    public void reportBadCharacter(int position, char character) {
        String message = "Bad character input: '" + character + "'.";
        report(new TextSpan(position, 1), message);
    }

    public void reportUnexpectedToken(TextSpan span, SyntaxKind actualKind, SyntaxKind expectedKind) {
        String message = "Unexpected token <" + actualKind + ">, expected <" + expectedKind + ">.";
        report(span, message);
    }

    public void reportUndefindUnaryOperator(TextSpan span, String operatorText, Class operandType) {
        String message = "Unary operator " + operatorText + " is not defined for type " + operandType;
        report(span, message);
    }

    public void reportUndefindBinaryOperator(TextSpan span, String operatorText, Class typeLeft, Class typeRight) {
        String message = "Binary operator " + operatorText
        + " is not defined for types " 
        + typeLeft + " and "
        + typeRight;
        report(span, message);
    }

    public boolean isEmpty() {
        return this.diagnostics.isEmpty();
    }


    
}
