package com.JsonAjax.justcompiler.Binding;

public enum BoundNodeKind {
    
    //statments
    blockStatment,
    expressionStatement,
    boundVariableDeclaration,
    ifStatement,

    //expressions
    UnaryExpression, LiteralExpression, BinaryExpression, VariableExpression, AssignmentExpression, 
    
}
