package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;

public class SyntaxFacts {
    
    protected static int getBinaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case star:
            case slash:
                return 5;

            case plus:
            case minus:
                return 4;

            case equalsEquals:
            case bangEquals:
            case less:
            case lessOrEquals:
            case greater:
            case greaterOrEquals:
                return 3;

            case ampersandAmpersand:
                return 2;

            case pipePipe:
                return 1;

            default:
                return 0;
        }
    }

    protected static int getUnaryOperatorPrecedence(SyntaxKind kind){
        switch(kind){
            case plus:
            case minus:
            case bang:
                return 6;

            default:
                return 0;
        }
    }

    public static SyntaxKind getKeywordKind(String text) {   
        switch (text){
            case "true":
                return SyntaxKind.trueKeyword;
            case "false":
                return SyntaxKind.falseKeyword;
            case "if":
                return SyntaxKind.ifKeyword;
            case "else":
                return SyntaxKind.elseKeyword;
            case "let":
                return SyntaxKind.letKeyword;
            case "var":
                return SyntaxKind.varKeyword;
            case "while":
                return SyntaxKind.whileKeyword;
            case "for":
                return SyntaxKind.forKeyword;
            case "to":
                return SyntaxKind.toKeyword;
            default:
                return SyntaxKind.identifierToken;
        }
    }

    public static List<SyntaxKind> getBinaryOperatorKinds(){
        List<SyntaxKind> list = new ArrayList<>();
        for (SyntaxKind kind : SyntaxKind.values()) {
            if(getBinaryOperatorPrecedence(kind)>0)
                list.add(kind);
        }
        return list;
    }

    public static List<SyntaxKind> getUnaryOperatorKinds(){
        List<SyntaxKind> list = new ArrayList<>();
        for (SyntaxKind kind : SyntaxKind.values()) {
            if(getUnaryOperatorPrecedence(kind)>0)
                list.add(kind);
        }
        return list;
    }

    public static String getText(SyntaxKind kind){
        switch(kind){
            case plus:
                return "+";
            case minus:
                return "-";
            case slash:
                return "/";
            case star:
                return "*";
            case leftParen:
                return "(";
            case rightParen:
                return ")";
            case leftBrace:
                return "{";
            case rightBrace:
                return "}";
            case bang:
                return "!";
            case equals:
                return "=";
            case ampersandAmpersand:
                return  "&&";
            case pipePipe:
                return "||";
            case bangEquals:
                return  "!=";
            case equalsEquals:
                return "==";
            case less:
                return "<";
            case lessOrEquals:
                return "<=";
            case greater:
                return ">";
            case greaterOrEquals:
                return ">=";
            case falseKeyword:
                return "false";
            case trueKeyword:
                return "true";
            case letKeyword:
                return "let";
            case varKeyword:
                return "var";
            case ifKeyword:
                return "if";
            case elseKeyword:
                return "else";
            case whileKeyword:
                return "while";
            case forKeyword:
                return "for";
            case toKeyword:
                return "to";
            default:
                return null;
        }
    }
}
