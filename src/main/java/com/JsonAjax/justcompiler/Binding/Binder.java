package com.JsonAjax.justcompiler.Binding;

import java.net.BindException;
import java.util.Map;

import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.Syntax.AssignmentExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.BinaryExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.LiteralExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.NameExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ParenthesizedExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.UnaryExpressionSyntax;

/**
 * Binder is a type checker. It walks the syntacs tree and create the bound tree
 */
public class Binder {

    Map<String, Object> variables;
    private DiagnosticsBag diagnostics = new DiagnosticsBag();

    public Binder(Map<String, Object> variables){
        this.variables = variables;
    }

    public BoundExpression bindExpression(ExpressionSyntax syntax) throws Exception {

        switch(syntax.kind()){
            case parenthesizedExpression:
                return bindParenthesizedExpression((ParenthesizedExpressionSyntax) syntax);
            case literalExpression:
                return bindLiteralExpression((LiteralExpressionSyntax) syntax);
            case nameExpression:
                return bindNameExpression((NameExpressionSyntax) syntax);
            case assignmentExpression:
                return bindAssignmentExpression((AssignmentExpressionSyntax) syntax);
            case unaryExpression:
                return bindUnaryExpression((UnaryExpressionSyntax) syntax);
            case binaryExpression:
                return bindBinaryExpression((BinaryExpressionSyntax) syntax);
                        
            default:
                throw new Exception("Unexpected syntax " + syntax.kind());
        }
    }

    private BoundExpression bindAssignmentExpression(AssignmentExpressionSyntax syntax) throws Exception {
        String name = syntax.getIdentifierToken().getText();
        BoundExpression boundExpression = bindExpression(syntax.getExpression());

        Object defaultValue = boundExpression.getType() == Integer.class
            ? 0
            : boundExpression.getType() == Boolean.class 
            ? false
            : null;
        
        if(defaultValue == null)
            throw new Exception("Unsupported variable type: " + boundExpression.getType());

        variables.put(name, defaultValue);
        return new BoundAssignmentExpression(name, boundExpression);
    }

    private BoundExpression bindNameExpression(NameExpressionSyntax syntax) {
        String name = syntax.getIdentifierToken().getText();
        if(!variables.containsKey(name)){
            diagnostics.reportUndefinedName(syntax.getIdentifierToken().getSpan(), name);
            return new BoundLiteralExpression(0);
        }
        
        Object value = variables.get(name);
        Class type = value.getClass();
        return new BoundVariableExpression(name, type);
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
        return bindExpression(syntax.getExpression());
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
