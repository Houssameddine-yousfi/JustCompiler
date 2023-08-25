package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.Syntax.SyntaxKind;

public final class BoundBinaryOperator {
    
    private SyntaxKind syntaxKind; 
    private BoundBinaryOperatorKind kind; 
    private Class leftType;
    private Class rightType; 
    private Class resultType;

    protected BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class leftType, Class rightType, Class resultType){
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.leftType = leftType;
        this.rightType = rightType;
        this.resultType = resultType;
    }

    protected BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class type){
        this(syntaxKind, kind, type, type, type);
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundBinaryOperatorKind getKind() {
        return kind;
    }

   
    public Class getLeftType() {
        return leftType;
    }

    public Class getRightType() {
        return rightType;
    }

    public Class getResultType() {
        return resultType;
    }

    public static BoundBinaryOperator[] operators = {
        new BoundBinaryOperator(SyntaxKind.plus, BoundBinaryOperatorKind.Addition, Integer.class),
        new BoundBinaryOperator(SyntaxKind.minus, BoundBinaryOperatorKind.Substraction, Integer.class),
        new BoundBinaryOperator(SyntaxKind.star, BoundBinaryOperatorKind.Multiplication, Integer.class),
        new BoundBinaryOperator(SyntaxKind.slash, BoundBinaryOperatorKind.Division, Integer.class),
        new BoundBinaryOperator(SyntaxKind.ampersandAmpersand, BoundBinaryOperatorKind.LogicalAnd, Boolean.class),
        new BoundBinaryOperator(SyntaxKind.pipePipe, BoundBinaryOperatorKind.LogicalOr, Boolean.class),
    };

    public static BoundBinaryOperator bind(SyntaxKind syntaxKind, Class rightType, Class leftType){
        for (BoundBinaryOperator operator : operators) {
            if(operator.syntaxKind == syntaxKind && operator.rightType == rightType && operator.leftType == leftType )
                return operator;
        }
        return null;
    }

    
}
