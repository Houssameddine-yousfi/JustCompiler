package com.JsonAjax.justcompiler.Binding;

import java.util.Map;
import java.util.Optional;

import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.VariableSymbol;
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

    Map<VariableSymbol, Object> variables;
    private DiagnosticsBag diagnostics = new DiagnosticsBag();

    public Binder(Map<VariableSymbol, Object> variables){
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


        Optional<VariableSymbol> existingvariable = variables.keySet().stream()
            .filter(v -> name.equals(v.getName()))
            .findFirst();
        if(existingvariable.isPresent())
            variables.remove(existingvariable.get());
        
        VariableSymbol variable = new VariableSymbol(name, boundExpression.getType());
        variables.put(variable, null);

        return new BoundAssignmentExpression(variable, boundExpression);
    }

    private BoundExpression bindNameExpression(NameExpressionSyntax syntax) {
        String name = syntax.getIdentifierToken().getText();

        Optional<VariableSymbol> variable = variables.keySet().stream()
        .filter(v -> name.equals(v.getName()))
        .findFirst();

        if(variable.isEmpty()){
            diagnostics.reportUndefinedName(syntax.getIdentifierToken().getSpan(), name);
            return new BoundLiteralExpression(0);
        }
        
        return new BoundVariableExpression(variable.get());
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
