package com.JsonAjax.justcompiler.Binding;


import java.util.ArrayList;
import java.util.List;

import com.JsonAjax.justcompiler.Syntax.BinaryExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.LiteralExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ParenthesizedExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.SyntaxKind;
import com.JsonAjax.justcompiler.Syntax.UnaryExpressionSyntax;

/**
 * Binder is a type checker. It walks the syntacs tree and create the bound tree
 */
public class Binder {

    private List<String> diagnostics = new ArrayList<>();

    public BoundExpression bindExpression(ExpressionSyntax syntax) throws Exception {

        switch(syntax.kind()){
            case unaryExpression:
                return bindUnaryExpression((UnaryExpressionSyntax) syntax);
            case binaryExpression:
                return bindBinaryExpression((BinaryExpressionSyntax) syntax);
            case literalExpression:
                return bindLiteralExpression((LiteralExpressionSyntax) syntax);
            case parenthesizedExpression:
                return bindParenthesizedExpression((ParenthesizedExpressionSyntax) syntax);
            default:
                throw new Exception("Unexpected syntax " + syntax.kind());
        }
    }

    private BoundExpression bindLiteralExpression(LiteralExpressionSyntax syntax) throws Exception{
        Object value = syntax.getValue();
        value = (value==null)?0:value;
        return new BoundLiteralExpression(value);
    }

    private BoundExpression bindBinaryExpression(BinaryExpressionSyntax syntax) throws Exception{
        BoundExpression boundLeft = bindExpression(syntax.getLeft());
        BoundExpression boundRight = bindExpression(syntax.getRight());
        BoundBinaryOperatorKind boundOperatorKind = bindBinaryOperatorKind(syntax.getOperatorToken().kind(), boundLeft.getType(), boundRight.getType());

        if(boundOperatorKind == null){
            this.diagnostics.add("Binary operator " + syntax.getOperatorToken().getText() 
                + " is not defined for types " 
                + boundLeft.getType() + " and "
                + boundRight.getType());
            return boundLeft;
        }
        return new BoundBinaryExpression(boundLeft, boundOperatorKind  , boundRight);
    }
    


    private BoundExpression bindParenthesizedExpression(ParenthesizedExpressionSyntax syntax) throws Exception{
        return null;
    }

    

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) throws Exception {
        BoundExpression boundOperand = bindExpression(syntax.getOperand());
        BoundUnaryOperatorKind boundOperatorKind = bindUnaryOperatorKind(syntax.getOperatorToken().kind(), boundOperand.getType());
        if(boundOperatorKind == null){
            this.diagnostics.add("Unary operator " + syntax.getOperatorToken().getText() + " is not defined for type " + boundOperand.getType());
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperatorKind  , boundOperand);
    }


    private BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxKind kind, Class operandType) throws Exception {
        if (operandType == Integer.class) {
            switch (kind) {
                case plus:
                    return BoundUnaryOperatorKind.Identity;
                case minus:
                    return BoundUnaryOperatorKind.Negation;

            }
        }
        
        else if (operandType == Boolean.class) {
            switch(kind) {
                case bang:
                    return BoundUnaryOperatorKind.LogicalNegation;
            }
        }
        return null;

        
    }

    private BoundBinaryOperatorKind bindBinaryOperatorKind(SyntaxKind kind, Class leftType, Class rightType) throws Exception {
        
        if(leftType == Integer.class && rightType == Integer.class) {
            switch (kind) {
                case plus:
                    return BoundBinaryOperatorKind.Addition;
                case minus:
                    return BoundBinaryOperatorKind.Substraction;
                case star:
                    return BoundBinaryOperatorKind.Multiplication;
                case slash:
                    return BoundBinaryOperatorKind.Division;
                default:
                    throw new Exception("Unexpected unary operator " + kind);
            }
        }

        if(leftType == Boolean.class && rightType == Boolean.class) {
            switch (kind) {
                case ampersandAmpersand:
                    return BoundBinaryOperatorKind.LogicalAnd;
                case pipePipe:
                    return BoundBinaryOperatorKind.LogicalOr;
            }
        }
        
        return null;
        
        
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }
    
}
