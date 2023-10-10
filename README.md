# Just a Compiler

This is a repo for the **Just Compiler**. A compiler for **Just language**. It is a turing complete JVM laguage built using java. The projuct is just for fun no serious devolepment inteded. The world do not need a new JVM language, let a lone one made using java. In the current state **Just** is just a basic intrepreted langure that run on the jvm.

## New in this version

- Support for more logical operations: <, <=, >, >= 
- If else statements
- While statements
- For  statements
- Better parse tree display


## features

For now this is just an expression evaluator not a compiler. It support only two types Integers and Booleans along side the following operations:
- Arithmetic operations: +, -, *, /
- Parenthesis: (,)
- Logical operations: ==, !=, !,
- Print the ASL tree
- Multi-line input
- Print diagnostics with error postision and highligt the error
- Proper scoping and declaration for variables


## Setup

### Requirements 
- Java JDK 17+ 
- Maven 3.6+

### Run
```bash
mvn install
java -jar target/JustCompiler-0.4-SNAPSHOT.jar
```

## Exapmles
Some examples of expressions using the two supported data types: Boolean and Integer:
```js
Just> 5+3*2-(8-2)  
5

Just> 18 == 15
false

Just> 5 + true

[1,3]: Binary operator '+' is not defined for types 'class java.lang.Integer' and 'class java.lang.Boolean'.
    5 + true

Just> 18 == false

[1,4]: Binary operator '==' is not defined for types 'class java.lang.Integer' and 'class java.lang.Boolean'.
    18 == false

Just> 
```

It possible to use blocks and properly declare variablers using the key word "var". The output is the result of the last expression:
```js
Just> {
|       var a = 5
|       a = a *2
|     }
10
```

It is also possible to declare variable in read only mode using the key word "const":
```js
Just> const a = 8
8

Just> a = 5

[1,3]: Variable 'a' is read-only and cannotbe assigned to.
    a = 5
```


**Just** support porper scoping, it is not possible to redeclare variable in the same scope or to acces variable out of scope:
```js
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
Examples of "if/else" statements:

```js
Just> {
|       var a=0      
|       if a == 0
|        a
|     }
0

Just> {
|       var a=5
|       if a == 0
|          a=10
|       else 
|          a=20
|       a
|     }
20
```

An example of "for" statement:
```js
Just> {
|        var result=0
|        for i=1 to 10 {
|           result = result + i
|        }
|        result
|     }
55
```

An example of "while" statemet:
```js
Just> {
|        var i=1
|        var result=0 
|        while i<=10 {
|           result = result + i
|           i = i+1 
|        }
|        result
|     }
55
```

An example of printing the parse tree:
```js
Just> #showTree
Showing parse tree.

Just> var a = 5 * 2
compilationUnit
└──variableDeclaration
    ├──varKeyword: 'var'
    ├──identifierToken: 'a'
    ├──equals: '='
    └──binaryExpression
        ├──literalExpression
        │   └──number: '5'
        ├──star: '*'
        └──literalExpression
            └──number: '2'
10
```