package com.JsonAjax.justcompiler.Binding;

public enum BoundNodeKind {
    
    //statments
    blockStatment,
    expressionStatement,
    BoundVariableDeclaration,

    //expressions
    UnaryExpression, LiteralExpression, BinaryExpression, VariableExpression, AssignmentExpression,
    
}
