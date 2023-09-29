/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.Text.TextSpan;

/**
 *
 * @author hyousfi
 */
public class Lexer {

    private String text;
    private DiagnosticsBag diagnostics = new DiagnosticsBag();

    private int position;

    private int start;
    private SyntaxKind kind;
    private Object value;

    private char current() {
        return peek(0);
    }

    private char lookahead() {
        return peek(1);
    }

    private char peek(int offset) {
        int index = this.position + offset;
        if (index >= text.length())
            return '\0';

        return text.charAt(index);
    }

    private void next() {
        position++;
    }

    public Lexer(String text) {
        this.text = text;
    }

    public SyntaxToken nextToken() {

        this.start = this.position;
        this.kind = SyntaxKind.badToken;
        this.value = null;

        switch (current()) {
            case '\0':
                this.kind = SyntaxKind.endOfFile;
                break;
            case '+':
                this.kind = SyntaxKind.plus;
                this.position++;
                break;
            case '-':
                this.kind = SyntaxKind.minus;
                this.position++;
                break;
            case '*':
                this.kind = SyntaxKind.star;
                this.position++;
                break;
            case '/':
                this.kind = SyntaxKind.slash;
                this.position++;
                break;
            case '(':
                this.kind = SyntaxKind.leftParen;
                this.position++;
                break;
            case ')':
                this.kind = SyntaxKind.rightParen;
                this.position++;
                break;
            case '{':
                this.kind = SyntaxKind.leftBrace;
                this.position++;
                break;
            case '}':
                this.kind = SyntaxKind.rightBrace;
                this.position++;
                break;

            case '!':
                if (lookahead() == '=') {
                    this.position += 2;
                    this.kind = SyntaxKind.bangEquals;
                } else {
                    this.kind = SyntaxKind.bang;
                    this.position++;
                }
                break;
            case '&':
                if (lookahead() == '&') {
                    this.position += 2;
                    this.kind = SyntaxKind.ampersandAmpersand;
                }
                break;
            case '|':
                if (lookahead() == '|') {
                    this.position += 2;
                    this.kind = SyntaxKind.pipePipe;
                }
                break;
            case '=':
                if (lookahead() == '=') {
                    this.position += 2;
                    this.kind = SyntaxKind.equalsEquals;
                } else {
                    this.kind = SyntaxKind.equals;
                    this.position++;
                }
                break;

            // use cases for digits instead of Charachter.idDegit() for better performance
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                readNumberToken();
                break;

            // use the cases for common white spaces for better performance
            case ' ':
            case '\t':
            case '\n':
            case '\r':
                readWhiteSpace();
                break;
            default:
                if (Character.isLetter(current())) 
                    readIdentifierOrKeyword();
                else if (Character.isWhitespace(current())) 
                    readWhiteSpace();
                else {
                    this.diagnostics.reportBadCharacter(position, current());
                    this.position++;
                }
        }

        String tokenText = SyntaxFacts.getText(this.kind);
        if (tokenText == null)
            tokenText = this.text.substring(this.start, this.position);

        return new SyntaxToken(kind, this.start, tokenText, this.value);

    }

    private void readIdentifierOrKeyword() {
        while (Character.isLetter(current()))
            next();

        String text = this.text.substring(this.start, this.position);
        this.kind = SyntaxFacts.getKeywordKind(text);

    }

    private void readWhiteSpace() {
        while (Character.isWhitespace(current()))
            next();
        this.kind = SyntaxKind.whiteSpace;
    }

    private void readNumberToken() {
        while (Character.isDigit(current()))
            next();

        String text = this.text.substring(this.start, this.position);
        int val = 0;
        try {
            val = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            this.diagnostics.reportInvalidNumber(new TextSpan(this.start, this.position - this.start), text,
                    Integer.class);
        }
        this.value = val;
        this.kind = SyntaxKind.number;
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }

}
