package com.JsonAjax.justcompiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.JsonAjax.justcompiler.Syntax.Parser;
import com.JsonAjax.justcompiler.Syntax.SyntaxTree;

public class EvaluationTest {


    @ParameterizedTest
    @MethodSource("getExpressionAndEvaluation")
    public void  evaluaiton_roundTrips(String text, Object expectedValue){
        SyntaxTree syntaxTree = SyntaxTree.parse(text);
        Compilation compilation = new Compilation(syntaxTree);
        Map<VariableSymbol,Object> variables = new HashMap<>();
        EvaluationResult result = compilation.evaluate(variables);

        assertTrue(result.getDiagnostics().isEmpty() );
        assertEquals(expectedValue, result.getValue());
    }

    public static Stream<Arguments> getExpressionAndEvaluation(){
        List<Arguments> list = List.of(
            Arguments.of("1", 1),
            Arguments.of("+1", 1),
            Arguments.of("-1", -1),
            Arguments.of("14 + 12", 26),
            Arguments.of("13 - 3", 10),
            Arguments.of("5 * 2", 10),
            Arguments.of("9/3", 3),
            Arguments.of("(10)", 10),
            Arguments.of("2 == 2", true),
            Arguments.of("2 == 3", false),
            Arguments.of("2 != 2", false),
            Arguments.of("2 != 3", true),
             Arguments.of("false == false", true),
            Arguments.of("true == false", false),
            Arguments.of("true != true", false),
            Arguments.of("true != false", true),

            Arguments.of("true", true),
            Arguments.of("false", false),
            Arguments.of("!true", false),
            Arguments.of("!false", true),
            Arguments.of("true && true", true),
            Arguments.of("true && false", false),
            Arguments.of("true || true", true),
            Arguments.of("true || false", true),
            
            Arguments.of("{var a = 0 (a = 10) * a}", 100)
        );
        
        return list.stream();
    }


}
