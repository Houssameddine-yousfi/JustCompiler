/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

import com.JsonAjax.justcompiler.Parcing.Evaluator;
import com.JsonAjax.justcompiler.Parcing.ExpressionSyntax;
import com.JsonAjax.justcompiler.Parcing.Parser;
import com.JsonAjax.justcompiler.Parcing.SyntaxTree;
import java.io.Console;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hyousfi
 */
public class Index {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
             
        boolean showATree = false;
            while (true
                    ) {
                
                System.out.println("Just>");
                String line = in.nextLine();
                
                if(line.equals("#showTree")){
                    showATree = !showATree;
                                        continue;
                }
                if(line.equals("#exit")){
                    System.exit(0);
                }
                
                Parser parser = new Parser(line);
                SyntaxTree ast = parser.parse();
                
                if(showATree) ast.getRoot().prettyPrint("");
                
                List<String> diagnostics = ast.getDiagnostics();
                
                // if we find errors we display them else we evaluate
                if(!diagnostics.isEmpty()){
                    for (String diagnostic : diagnostics) {
                        System.out.println(ANSI_RED + diagnostic + ANSI_RESET);
                    }
                } else{
                    
                    Evaluator evaluator = new Evaluator(ast.getRoot());
                    int val = evaluator.evaluate();
                    
                    System.out.println("" + val);
                }
            }

        }

    }
