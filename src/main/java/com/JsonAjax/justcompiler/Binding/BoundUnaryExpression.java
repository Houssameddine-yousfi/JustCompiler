package com.JsonAjax.justcompiler.Binding;

public class BoundUnaryExpression extends BoundExpression {
    
    BoundUnaryOperatorKind operatorKind;
    BoundExpression operand;

    public BoundUnaryExpression(BoundUnaryOperatorKind operatorKind, 
        BoundExpression operand){
            this.operatorKind = operatorKind;
            this.operand = operand;
    }


    

    @Override
    public Class getType() {
        return this.operand.getType();
    }

        @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }

    public BoundUnaryOperatorKind getOperatorKind() {
        return operatorKind;
    }

    public BoundExpression getOperand() {
        return operand;
    }







    
}
