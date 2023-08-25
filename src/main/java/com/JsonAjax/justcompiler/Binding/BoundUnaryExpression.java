package com.JsonAjax.justcompiler.Binding;

public class BoundUnaryExpression extends BoundExpression {
    
    BoundUnaryOperator operator;
    BoundExpression operand;

    public BoundUnaryExpression(BoundUnaryOperator operator, 
        BoundExpression operand){
            this.operator = operator;
            this.operand = operand;
    }


    

    @Override
    public Class getType() {
        return operator.getResultType();
    }

        @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }

    public BoundUnaryOperator getOperator() {
        return operator;
    }

    public BoundExpression getOperand() {
        return operand;
    }







    
}
