package com.JsonAjax.justcompiler.Text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SourceTextTest {

    @ParameterizedTest
    @MethodSource("linesData")
    public void SourceText_IncludesLastLine(String text, int expectedLineCount){
        SourceText sourceText = SourceText.from(text);
        assertEquals(expectedLineCount, sourceText.getLines().size());
    }


    private static Stream<Arguments> linesData(){
        return Stream.of(
            Arguments.of(".",1),
            Arguments.of(".\r\n",2),
            Arguments.of(".\r\n\r\n",3)
        );
    }
    
}
