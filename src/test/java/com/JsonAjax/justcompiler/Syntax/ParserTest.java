package com.JsonAjax.justcompiler.Syntax;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.JsonAjax.justcompiler.Syntax.TestUtils.AssertingList;
 
public class ParserTest {


    @ParameterizedTest
    @MethodSource("getBinaryOperatorPairsStream")
    public void parser_BinaryExpression_HonorsPrecedences(SyntaxKind op1, SyntaxKind op2){
        int op1precedenced = SyntaxFacts.getBinaryOperatorPrecedence(op1);
        int op2precedenced = SyntaxFacts.getBinaryOperatorPrecedence(op2);
        String op1Text = SyntaxFacts.getText(op1);
        String op2Text = SyntaxFacts.getText(op2);

        String text = "a "+ op1Text + " b " + op2Text + " c";
        Parser parser = new Parser(text);
        SyntaxTree ast = parser.parse();

        if (op1precedenced >= op2precedenced){
            AssertingList e = new AssertingList(ast.getRoot());
            e.AssertNode(SyntaxKind.binaryExpression);
            e.AssertNode(SyntaxKind.binaryExpression);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "a");
            e.AssertToken(op1, op1Text);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "b");
            e.AssertToken(op2, op2Text);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "c");


        }else {
            AssertingList e = new AssertingList(ast.getRoot());
            e.AssertNode(SyntaxKind.binaryExpression);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "a");
            e.AssertToken(op1, op1Text);
            e.AssertNode(SyntaxKind.binaryExpression);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "b");
            e.AssertToken(op2, op2Text);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "c");
        }

    }

    public static Stream<Arguments> getBinaryOperatorPairsStream(){
        List<Arguments> list = new ArrayList<>();
        for (SyntaxKind kind1 : SyntaxFacts.getBinaryOperatorKinds()) {
            for (SyntaxKind kind2 : SyntaxFacts.getBinaryOperatorKinds()) {
                list.add(Arguments.of(kind1,kind2));
            }
        }
        return list.stream();
    }
    
}
