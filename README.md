# Just a Compiler

This is a repo for the **Just Compiler**. A compiler for **Just language**. It is a turing complete JVM laguage built using java. The projuct is just for fun no serious devolepment inteded. The world do not need a new JVM language, let a lone one made using java.

## New in this version
It is now possible to use blocks and properly declare variablers using the key word "var". The output is the result of the last expression:
```bash
Just> {
|       var a = 5
|       a = a *2
|     }
10
```

It is also possible to declare variable in read only mode using the key word "let":
```bash
Just> let a = 8
8

Just> a = 5

[1,3]: Variable 'a' is read-only and cannotbe assigned to.
    a = 5
```


**Just** now support porper scoping, it is not possible to redeclare variable in the same scope or to acces variable out of scope:
```bash
Just> {
|       var a = 5
|       {
|         var a = true 
|       }     
|     }
true

Just> { 
|       var a = 5
|       var a = 6
|     }

[3,7]: Variable 'a' is already declared.
      var a = 6

Just> {
|       {              
|         var x = 1
|       }
|       x = 2
|     }

[5,3]: Variable x doesn't exist.
      x = 2
```



## features

For now this is just an expression evaluator not a compiler. It support only two types Integers and Booleans along side the following operations:
- Arithmetic operations: +, -, *, /
- Parenthesis: (,)
- Logical operations: ==, !=, ! 
- variables and assignments: a=5, 2+a
- Print the ASL tree
- Multi-line input
- Print diagnostics with error postision and highligt the error


## Setup

### Requirements 
- Java JDK 11 
- Maven 3.6

### Run
```bash
mvn install
java -jar target/JustCompiler-0.4-SNAPSHOT.jar
```