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
        int op1precedence = SyntaxFacts.getBinaryOperatorPrecedence(op1);
        int op2precedence = SyntaxFacts.getBinaryOperatorPrecedence(op2);
        String op1Text = SyntaxFacts.getText(op1);
        String op2Text = SyntaxFacts.getText(op2);

        String text = "a "+ op1Text + " b " + op2Text + " c";
        SyntaxTree ast = SyntaxTree.parse(text);

        AssertingList e = new AssertingList(ast.getRoot());
        e.AssertNode(SyntaxKind.compilationUnit);
        e.AssertNode(SyntaxKind.expressionStatment);
       
        if (op1precedence >= op2precedence){

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

    @ParameterizedTest
    @MethodSource("getUnaryBinaryOperatorPairsStream")
    public void parser_UnaryExpression_HonorsPrecedences(SyntaxKind unaryKind, SyntaxKind binaryKind){
        int unaryPrecedence = SyntaxFacts.getUnaryOperatorPrecedence(unaryKind);
        int binaryPrecedence = SyntaxFacts.getBinaryOperatorPrecedence(binaryKind);
        String unaryText = SyntaxFacts.getText(unaryKind);
        String binaryText = SyntaxFacts.getText(binaryKind);

        String text = unaryText + " a " + binaryText + " b";
        
        SyntaxTree ast = SyntaxTree.parse(text);
        AssertingList e = new AssertingList(ast.getRoot());
        
        e.AssertNode(SyntaxKind.compilationUnit);
        e.AssertNode(SyntaxKind.expressionStatment);
        
        if (unaryPrecedence >= binaryPrecedence){
            
            e.AssertNode(SyntaxKind.binaryExpression);
            e.AssertNode(SyntaxKind.unaryExpression);
            e.AssertToken(unaryKind, unaryText);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "a");
            e.AssertToken(binaryKind, binaryText);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "b");



        }else {

            e.AssertNode(SyntaxKind.unaryExpression);
            e.AssertToken(unaryKind, unaryText);
            e.AssertNode(SyntaxKind.binaryExpression);   
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "a");
            e.AssertToken(binaryKind, binaryText);
            e.AssertNode(SyntaxKind.nameExpression);
            e.AssertToken(SyntaxKind.identifierToken, "b");
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

    public static Stream<Arguments> getUnaryBinaryOperatorPairsStream(){
        List<Arguments> list = new ArrayList<>();
        for (SyntaxKind kind1 : SyntaxFacts.getUnaryOperatorKinds()) {
            for (SyntaxKind kind2 : SyntaxFacts.getBinaryOperatorKinds()) {
                list.add(Arguments.of(kind1,kind2));
            }
        }
        return list.stream();
    }
    
}
