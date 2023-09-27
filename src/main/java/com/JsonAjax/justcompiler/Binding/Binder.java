package com.JsonAjax.justcompiler.Binding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import com.JsonAjax.justcompiler.Diagnostic;
import com.JsonAjax.justcompiler.DiagnosticsBag;
import com.JsonAjax.justcompiler.VariableSymbol;
import com.JsonAjax.justcompiler.Syntax.AssignmentExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.BinaryExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.BlockStatmentSyntax;
import com.JsonAjax.justcompiler.Syntax.CompilationUnitSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionStatementSyntax;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.LiteralExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.NameExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.ParenthesizedExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.StatementSyntax;
import com.JsonAjax.justcompiler.Syntax.SyntaxNode;
import com.JsonAjax.justcompiler.Syntax.UnaryExpressionSyntax;

/**
 * Binder is a type checker. It walks the syntacs tree and create the bound tree
 */
public class Binder {

    private DiagnosticsBag diagnostics = new DiagnosticsBag();
    private BoundScope scope;

    public Binder(BoundScope parentScope ){
        this.scope = new BoundScope(parentScope);
    }

    private static BoundScope createParentScopes(BoundGlobalScope previous){
        Stack<BoundGlobalScope> stack = new Stack<>();
        while(previous != null){
            stack.push(previous);
            previous = previous.getPrevious();
        }
        BoundScope parent = null;

        while(!stack.empty()){
            previous = stack.pop();
            BoundScope scope = new BoundScope(parent);
            for (VariableSymbol variableSymbol : previous.getVariables())
                scope.tryDeclare(variableSymbol);
            parent = scope;
        }

        return parent;
    }

    public static BoundGlobalScope bindGlobalScope(BoundGlobalScope previous, CompilationUnitSyntax syntax) throws Exception{
        BoundScope parentScope = createParentScopes(previous);
        Binder binder = new Binder(parentScope);
        BoundStatement statement = binder.bindStatement(syntax.getStatement());
        List<VariableSymbol> variables = binder.scope.getDeclaredVariables();
        DiagnosticsBag diagnostics = binder.getDiagnostics();
        return new BoundGlobalScope(previous, diagnostics, variables, statement);
    }

    private BoundStatement bindStatement(StatementSyntax syntax) throws Exception {

        switch(syntax.kind()){
            case blockStatment:
                return bindBlockStatement((BlockStatmentSyntax) syntax);
            case expressionStatment:
                return bindExpressionStatement((ExpressionStatementSyntax) syntax);
            
            default:
                throw new Exception("Unexpected syntax " + syntax.kind());
        }
    }

    private BoundStatement bindBlockStatement(BlockStatmentSyntax syntax) throws Exception {
        List<BoundStatement> statements = new ArrayList<>();
        for (StatementSyntax statementSyntax : syntax.getStatements()) {
            BoundStatement statement = bindStatement(statementSyntax);
            statements.add(statement);
        }
        return new BoundBlockStatement(statements);
    }

    private BoundStatement bindExpressionStatement(ExpressionStatementSyntax syntax) throws Exception{
        BoundExpression expression = bindExpression(syntax.getExpression());
        return new BoundExpressionStatement(expression);
    }

    private BoundExpression bindExpression(ExpressionSyntax syntax) throws Exception {

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

    

    private BoundExpression bindNameExpression(NameExpressionSyntax syntax) {
        String name = syntax.getIdentifierToken().getText();
        Optional<VariableSymbol> optioinalVariable = scope.tryLookup(name);
        if(optioinalVariable.isEmpty()){
            diagnostics.reportUndefinedName(syntax.getIdentifierToken().getSpan(), name);
            return new BoundLiteralExpression(0);
        }
        
        return new BoundVariableExpression(optioinalVariable.get());
    }


    private BoundExpression bindAssignmentExpression(AssignmentExpressionSyntax syntax) throws Exception {
        
        String name = syntax.getIdentifierToken().getText();
        BoundExpression boundExpression = bindExpression(syntax.getExpression());
    
        VariableSymbol variable;
        Optional<VariableSymbol> optionalVar = scope.tryLookup(name);
        if(optionalVar.isEmpty()){
            variable = new VariableSymbol(name, boundExpression.getType());
            scope.tryDeclare(variable);
        }else{
            variable = optionalVar.orElseThrow();
        }

        if(boundExpression.getType() != variable.getType()){
            diagnostics.reportVariableCannotConvert(syntax.getExpression().getSpan(), boundExpression.getType(), variable.getType());
            return boundExpression;
        }
        

        return new BoundAssignmentExpression(variable, boundExpression);
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
