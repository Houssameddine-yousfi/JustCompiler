package com.JsonAjax.justcompiler.Binding;


import java.util.ArrayList;
import java.util.List;

import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.Syntax.BinaryExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.LiteralExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ParenthesizedExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.UnaryExpressionSyntax;

/**
 * Binder is a type checker. It walks the syntacs tree and create the bound tree
 */
public class Binder {

    private DiagnosticsBag diagnostics = new DiagnosticsBag();

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
        BoundBinaryOperator boundOperator = BoundBinaryOperator.bind(syntax.getOperatorToken().kind(), boundLeft.getType(), boundRight.getType());

        if(boundOperator == null){
            this.diagnostics.reportUndefindBinaryOperator(syntax.getOperatorToken().getSpan(),
                syntax.getOperatorToken().getText(),
                boundLeft.getType(),
                boundRight.getType());
            return boundLeft;
        }
        return new BoundBinaryExpression(boundLeft, boundOperator  , boundRight);
    }
    


    private BoundExpression bindParenthesizedExpression(ParenthesizedExpressionSyntax syntax) throws Exception{
        BoundExpression innerBoundExpression = bindExpression(syntax.getExpression());
        return innerBoundExpression;
    }

    

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) throws Exception {
        BoundExpression boundOperand = bindExpression(syntax.getOperand());
        BoundUnaryOperator boundOperator = BoundUnaryOperator.bind(syntax.getOperatorToken().kind(), boundOperand.getType());
        if(boundOperator == null){
            this.diagnostics.reportUndefindUnaryOperator(syntax.getOperatorToken().getSpan(), syntax.getOperatorToken().getText(), boundOperand.getType());
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperator  , boundOperand);
    }

    public DiagnosticsBag getDiagnostics() {
        return diagnostics;
    }
    
}
