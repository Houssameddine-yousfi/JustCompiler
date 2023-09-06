package com.JsonAjax.justcompiler.Syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LexerTest {
    public static List<Arguments> getTokensList(){
        return  List.of(
            Arguments.of(SyntaxKind.plus, "+"),
            Arguments.of(SyntaxKind.minus, "-"),
            Arguments.of(SyntaxKind.slash, "/"),
            Arguments.of(SyntaxKind.star, "*"),
            Arguments.of(SyntaxKind.leftParen, "("),
            Arguments.of(SyntaxKind.rightParen, ")"),
            Arguments.of(SyntaxKind.bang, "!"),
            Arguments.of(SyntaxKind.equals, "="),
            Arguments.of(SyntaxKind.ampersandAmpersand,  "&&"),
            Arguments.of(SyntaxKind.pipePipe, "||"),
            Arguments.of(SyntaxKind.bangEquals,  "!="),
            Arguments.of(SyntaxKind.equalsEquals, "=="),
            Arguments.of(SyntaxKind.falseKeyword, "false"),
            Arguments.of(SyntaxKind.trueKeyword, "true"),
            
            Arguments.of(SyntaxKind.identifierToken,"a"),
            Arguments.of(SyntaxKind.identifierToken,"abc"),

            Arguments.of(SyntaxKind.number, "0"),
            Arguments.of(SyntaxKind.number, "156")
        );
    }

    public static List<Arguments> getSeperatorsList(){
        return  List.of(      
            Arguments.of(SyntaxKind.whiteSpace, " "),
            Arguments.of(SyntaxKind.whiteSpace, "  "),
            Arguments.of(SyntaxKind.whiteSpace, "\r"),
            Arguments.of(SyntaxKind.whiteSpace, "\n"),
            Arguments.of(SyntaxKind.whiteSpace, "\r\n"),
            Arguments.of(SyntaxKind.whiteSpace, "\t")
        );
    }

    public static Stream<Arguments> getTokensStream(){
        return Stream.concat(getTokensList().stream(),getSeperatorsList().stream());
    }


    @ParameterizedTest
    @MethodSource("getTokensStream")
    void lexer_lexes_Tokens(SyntaxKind kind, String text) {
        
        List<SyntaxToken> tokens = SyntaxTree.ParseTokens(text);
        assertEquals(1, tokens.size());

        SyntaxToken token = tokens.get(0);
        assertEquals(token.kind(), kind);
        assertEquals(token.getText(), text);
    }

    private static boolean requiresSeparator(SyntaxKind kind1, SyntaxKind kind2){
        
        boolean t1IsKeyword = kind1.toString().endsWith("Keyword");
        boolean t2IsKeyword = kind2 .toString().endsWith("Keyword");
        
        if(kind1 == SyntaxKind.identifierToken && kind2 == SyntaxKind.identifierToken)
            return true;
        
        if(t1IsKeyword && t2IsKeyword)
            return true;
        
        if(t1IsKeyword && kind2 == SyntaxKind.identifierToken)
            return true;
        
        if(kind1 == SyntaxKind.identifierToken && t2IsKeyword)
            return true;
        

        if(kind1 == SyntaxKind.number && kind2 == SyntaxKind.number)
            return true;
        
        
        if(kind1 == SyntaxKind.bang && kind2 == SyntaxKind.equals)
            return true;
        
        if(kind1 == SyntaxKind.bang && kind2 == SyntaxKind.equalsEquals)
            return true;
        
        if(kind1 == SyntaxKind.equals && kind2 == SyntaxKind.equals)
            return true;

        if(kind1 == SyntaxKind.equals && kind2 == SyntaxKind.equalsEquals)
            return true;


        return false;
    }

    public static Stream<Arguments> GetTokenPairesStream(){
        List<Arguments> pairesList= new ArrayList<>();
        for (Arguments arguments1 : getTokensList()) {
            
            SyntaxKind kind1 = (SyntaxKind) arguments1.get()[0];
            for (Arguments arguments2 : getTokensList()) {

                SyntaxKind kind2 = (SyntaxKind) arguments2.get()[0];
                if(!requiresSeparator(kind1, kind2))
                    pairesList.add(Arguments.of(
                        kind1, arguments1.get()[1],
                        kind2, arguments2.get()[1]));
            }
        }
        return pairesList.stream();
    }

    public static Stream<Arguments> GetSeperatedTokenPairesStream(){
        List<Arguments> pairesList= new ArrayList<>();
        for (Arguments arguments1 : getTokensList()) {
            
            SyntaxKind kind1 = (SyntaxKind) arguments1.get()[0];
            for (Arguments arguments2 : getTokensList()) {

                SyntaxKind kind2 = (SyntaxKind) arguments2.get()[0];
                if(!requiresSeparator(kind1, kind2))
                    continue;
                
                for (Arguments separatorsArg : getSeperatorsList()) {
                    pairesList.add(Arguments.of(
                        kind1, arguments1.get()[1],
                        (SyntaxKind) separatorsArg.get()[0], separatorsArg.get()[1],
                        kind2, arguments2.get()[1]));
                }
                
            }
        }
        return pairesList.stream();
    }

    @ParameterizedTest
    @MethodSource("GetTokenPairesStream")
    void lexer_lexes_TokensPaires(SyntaxKind kind1, String text1, SyntaxKind kind2, String text2) {
        
        String text = text1+text2;

        List<SyntaxToken> tokens = SyntaxTree.ParseTokens(text);
        assertEquals(2, tokens.size());

        SyntaxToken token = tokens.get(0);
        assertEquals(token.kind(), kind1);
        assertEquals(token.getText(), text1);

        token = tokens.get(1);
        assertEquals(token.kind(), kind2);
        assertEquals(token.getText(), text2);
    }


    @ParameterizedTest
    @MethodSource("GetSeperatedTokenPairesStream")
    void lexer_lexes_Separated_TokensPaires(SyntaxKind kind1, String text1, 
                    SyntaxKind sepKind, String sepText,
                    SyntaxKind kind2, String text2) {
        
        String text = text1+sepText+text2;

        List<SyntaxToken> tokens = SyntaxTree.ParseTokens(text);
        assertEquals(3, tokens.size());

        SyntaxToken token = tokens.get(0);
        assertEquals(token.kind(), kind1);
        assertEquals(token.getText(), text1);

        token = tokens.get(1);
        assertEquals(token.kind(), sepKind);
        assertEquals(token.getText(), sepText);

        token = tokens.get(2);
        assertEquals(token.kind(), kind2);
        assertEquals(token.getText(), text2);
    }


}
