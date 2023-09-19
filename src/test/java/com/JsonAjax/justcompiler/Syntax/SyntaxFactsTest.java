package com.JsonAjax.justcompiler.Syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.JsonAjax.justcompiler.Text.SourceText;

public class SyntaxFactsTest {
    
    @ParameterizedTest
    @MethodSource("getSyntaxKindStream")
    void sytaxFact_GetText_RoundTrips( SyntaxKind kind){
        
        String text = SyntaxFacts.getText(kind);
        if(text == null)
            return;
        
        List<SyntaxToken> tokens = SyntaxTree.parseTokens(SourceText.from(text));
        assertEquals(1,tokens.size());
        
        SyntaxToken token = tokens.get(0);
        assertEquals(kind, token.kind());
        assertEquals(text, token.getText());

    }

    public static Stream<Arguments> getSyntaxKindStream(){
        return Stream.of(SyntaxKind.values()).map((kind) -> Arguments.of(kind));
    }
    

}
