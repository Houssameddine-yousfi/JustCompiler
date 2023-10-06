package com.JsonAjax.justcompiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;
import javax.swing.Spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.JsonAjax.justcompiler.Syntax.Parser;
import com.JsonAjax.justcompiler.Syntax.SyntaxTree;
import com.JsonAjax.justcompiler.Text.AnnotatedText;
import com.JsonAjax.justcompiler.Text.TextSpan;

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

            Arguments.of("3 < 4", true),
            Arguments.of("3 < 2", false),

            Arguments.of("3 <= 4", true),
            Arguments.of("3 <= 3", true),
            Arguments.of("3 <= 2", false),

            Arguments.of("3 > 2", true),
            Arguments.of("3 > 4", false),

            Arguments.of("3 >= 2", true),
            Arguments.of("3 >= 3", true),
            Arguments.of("3 >= 4", false),

            Arguments.of("true", true),
            Arguments.of("false", false),
            Arguments.of("!true", false),
            Arguments.of("!false", true),
            Arguments.of("true && true", true),
            Arguments.of("true && false", false),
            Arguments.of("true || true", true),
            Arguments.of("true || false", true),
            
            Arguments.of("{var a = 0 (a = 10) * a}", 100),
            Arguments.of("{var a = 0 if a == 0  a = 10 a}", 10),
            Arguments.of("{var a = 0 if a == 20 a = 10 a}", 0),
            Arguments.of("{var a = 0 if a == 0  a = 10 else a = 5 a}", 10),
            Arguments.of("{var a = 0 if a == 20 a = 10 else a = 5 a}", 5)
        );
        
        return list.stream();
    }

    @Test
    public void Evaluator_VariableDeclaration_Reports_Redeclaration(){
        String text = """
            {
                var x = 10
                var y = 100
                {
                    var x = 10
                }
                var [x] = 5
            }
        """;

        String diagnostics = "Variable 'x' is already declared.";
        assertHasDiagnostics(text, diagnostics);
    }

    @Test
    public void Evaluator_Name_Reports_Undefined(){
        String text = """
            [x] = 10
        """;

        String diagnostics = "Variable 'x' doesn't exist.";
        assertHasDiagnostics(text, diagnostics);
    }

    @Test
    public void Evaluator_Assignment_Reports_CannotAssign(){
        String text = """
            {
                let x = 5
                x [=] 10
            }
        """;

        String diagnostics = "Variable 'x' is read-only and cannot be assigned to.";
        assertHasDiagnostics(text, diagnostics);
    }

    @Test
    public void Evaluator_Assignment_Reports_CannotConvert(){
        String text = """
            {
                var x = 5
                x = [true]
            }
        """;

        String diagnostics = "Cannot convert type 'class java.lang.Boolean' to type 'class java.lang.Integer'.";
        assertHasDiagnostics(text, diagnostics);
    }

    @Test
    public void Evaluator_Unary_Reports_Undefined(){
        String text = """
            [+]true
        """;

        String diagnostics = "Unary operator '+' is not defined for type 'class java.lang.Boolean'.";
        assertHasDiagnostics(text, diagnostics);
    }

    @Test
    public void Evaluator_Binary_Reports_Undefined(){
        String text = """
            10 [+] false
        """;

        String diagnostics = "Binary operator '+' is not defined for types 'class java.lang.Integer' and 'class java.lang.Boolean'.";
        assertHasDiagnostics(text, diagnostics);
    }

    private void assertHasDiagnostics(String text, String diagnosticText){
        AnnotatedText annotatedText = AnnotatedText.Parse(text);
        SyntaxTree syntaxTree = SyntaxTree.parse(annotatedText.getText());
        var compilation = new Compilation(syntaxTree);
        EvaluationResult result = compilation.evaluate(new HashMap<VariableSymbol,Object>());

        List<String> expectedDiagnostics = AnnotatedText.unindentLines(diagnosticText);

        if(annotatedText.getSpans().size() != expectedDiagnostics.size())
            throw new RuntimeException( "Test not well definded: Must mark as many spans as the number of expected diagnostics");

        if(annotatedText.getSpans().size() != result.getDiagnostics().size())
            fail("Number of expected diagnostics does not equal the number of actual diagnostics");

        Iterator<Diagnostic> itr = result.getDiagnostics().iterator();
        int i =0;
        while(itr.hasNext()){
            
            Diagnostic diag = itr.next();

            String expectedMessage = expectedDiagnostics.get(i);
            String actualMessage   = diag.getMessage();
            assertEquals(expectedMessage,actualMessage);

            TextSpan expectedSpan = annotatedText.getSpans().get(i);
            TextSpan actualSpan   = diag.getSpan();
            assertEquals(expectedSpan,actualSpan);
            i++;
        }
        
    }

}
