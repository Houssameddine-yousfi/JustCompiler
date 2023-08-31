# Just a Compiler

This is a repo for the Just Compiler. A compiler for **Just a language**. It is a turing complete JVM laguage built using java. The projuct is just for fun no serious devolepment inteded. The world do not need a new JVM language, let a lone one made using java.

## New in this version
It is now possible to use variables and assignments, for example:
```java
Just>
a=5
5

Just>
a+2
7

Just>
a=true
true

Just>
a && false
false
```


## features

For now this is just an expression evaluator not a compiler. It support only two types Integers and Booleans along side the following operations:
- Arithmetic operations: +, -, *, /
- Parenthesis: (,)
- Logical operations: ==, !=, ! 