package com.JsonAjax.justcompiler.Syntax;

public class UnaryExpressionSyntax extends ExpressionSyntax{

    private ExpressionSyntax operand;
    private SyntaxToken operatorToken;

    public UnaryExpressionSyntax(SyntaxToken operatorToken, ExpressionSyntax operand) {
        this.operand = operand;
        this.operatorToken = operatorToken;
    } 
    
    
    @Override
    public SyntaxKind kind() {
        return SyntaxKind.UnaryExpressionSyntax;
    }
    
    @Override
    public void prettyPrint(String indentation){
        System.out.println( "UnaryExpression");
        
        System.out.println(indentation+"├──" + operatorToken.kind());
        System.out.print(indentation+"└──");
        operand.prettyPrint(indentation + "    " );   
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
}
