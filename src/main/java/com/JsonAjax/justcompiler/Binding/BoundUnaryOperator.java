package com.JsonAjax.justcompiler.Binding;

import com.JsonAjax.justcompiler.Syntax.SyntaxKind;

public final class BoundUnaryOperator {
    
    private SyntaxKind syntaxKind; 
    private BoundUnaryOperatorKind kind; 
    private Class operandType; 
    private Class resultType;

    protected BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class operandType, Class resultType){
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.operandType = operandType;
        this.resultType = resultType;
    }

    protected BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class operandType){
        this(syntaxKind, kind, operandType, operandType);
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundUnaryOperatorKind getKind() {
        return kind;
    }

    public Class getOperandType() {
        return operandType;
    }

    public Class getResultType() {
        return resultType;
    }

    public static BoundUnaryOperator[] operators = {
        new BoundUnaryOperator(SyntaxKind.bang, BoundUnaryOperatorKind.LogicalNegation, Boolean.class),
        new BoundUnaryOperator(SyntaxKind.plus, BoundUnaryOperatorKind.Identity, Integer.class),
        new BoundUnaryOperator(SyntaxKind.minus, BoundUnaryOperatorKind.Negation, Integer.class),
    };

    public static BoundUnaryOperator bind(SyntaxKind syntaxKind, Class operandType){
        for (BoundUnaryOperator operator : operators) {
            if(operator.syntaxKind == syntaxKind && operator.operandType == operandType)
                return operator;
        }
        return null;
    }

    
}
