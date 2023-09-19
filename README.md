# Just a Compiler

This is a repo for the Just Compiler. A compiler for **Just a language**. It is a turing complete JVM laguage built using java. The projuct is just for fun no serious devolepment inteded. The world do not need a new JVM language, let a lone one made using java.

## New in this version
It is now possible to use multi lines in one expression. The interpretor is also capable of showing the line number and the charachter number of the error if any:
```bash
Just> 5+2+
|     12
19

Just> 5+  
|     3+
|     1-
|     6
3

Just> a = 8
8

Just> a+
|     b

[2,1]: Variable b doesn't exist
```


## features

For now this is just an expression evaluator not a compiler. It support only two types Integers and Booleans along side the following operations:
- Arithmetic operations: +, -, *, /
- Parenthesis: (,)
- Logical operations: ==, !=, ! 
- variables and assignments: a=5, 2+a
- Print the ASL tree