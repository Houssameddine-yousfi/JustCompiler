package com.JsonAjax.justcompiler.Binding;

public class BoundBinaryExpression extends BoundExpression{

    BoundExpression left;
    BoundExpression right;
    BoundBinaryOperator operator;

    public BoundBinaryExpression(BoundExpression left, BoundBinaryOperator operator, BoundExpression right){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Class getType() {
        return left.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.BinaryExpression;
    }

    public BoundExpression getLeft() {
        return left;
    }

    public BoundExpression getRight() {
        return right;
    }

    public BoundBinaryOperator getOperator() {
        return operator;
    }

    
    
}
