package com.JsonAjax.justcompiler.Syntax;

import java.util.ArrayList;
import java.util.List;

public class UnaryExpressionSyntax extends ExpressionSyntax{

    private ExpressionSyntax operand;
    private SyntaxToken operatorToken;

    public UnaryExpressionSyntax(SyntaxToken operatorToken, ExpressionSyntax operand) {
        this.operand = operand;
        this.operatorToken = operatorToken;
    } 
    
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.unaryExpression;
    }
    

    

    public ExpressionSyntax getOperand() {
        return operand;
    }


    public void setOperand(ExpressionSyntax operand) {
        this.operand = operand;
    }


    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }


    @Override
    public List<SyntaxNode> getChildren() {
       List<SyntaxNode> list = new ArrayList<>();
        list.add(this.operatorToken);
        list.add(this.operand);
        return list;
    }
}
