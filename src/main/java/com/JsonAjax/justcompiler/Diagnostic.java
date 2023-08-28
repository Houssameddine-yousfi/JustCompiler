package com.JsonAjax.justcompiler;

public class Diagnostic {
    private TextSpan span;
    private String message;
    
    
    public Diagnostic(TextSpan span, String message) {
        this.span = span;
        this.message = message;
    }


    public TextSpan getSpan() {
        return span;
    }


    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return message ;
    }

}
