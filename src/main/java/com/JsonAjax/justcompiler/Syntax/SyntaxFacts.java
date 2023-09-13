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
            case falseKeyword:
                return "false";
            case trueKeyword:
                return "true";
            default:
                return null;
        }
    }
}
