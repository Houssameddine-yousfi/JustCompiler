/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

import java.io.Console;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.JsonAjax.justcompiler.Binding.Binder;
import com.JsonAjax.justcompiler.Binding.BoundExpression;
import com.JsonAjax.justcompiler.Syntax.ExpressionSyntax;
import com.JsonAjax.justcompiler.Syntax.Parser;
import com.JsonAjax.justcompiler.Syntax.SyntaxTree;

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
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Map<VariableSymbol,Object> variables = new HashMap<>();

        Scanner in = new Scanner(System.in);
             
        boolean showATree = false;
        while (true) {
            
            System.out.println("Just>");
            String line = in.nextLine();
            
            if(line.equals("#showTree")){
                showATree = !showATree;
                System.out.println( showATree? "Showing parse tree." : "Hide parse tree.");
                continue;
            }

            if(line.equals("#exit")){
                System.exit(0);
            }
            
            Parser parser = new Parser(line);
            SyntaxTree ast = parser.parse();
            
            Compilation compilation = new Compilation(ast);
            EvaluationResult result = compilation.evaluate(variables);

            
            DiagnosticsBag diagnostics = result.getDiagnostics();
            
            if(showATree) ast.getRoot().prettyPrint("");
            
            
            
            // if we find errors we display them else we evaluate
            if(!diagnostics.isEmpty()){
                Iterator<Diagnostic> itr = diagnostics.iterator();
                while ( itr.hasNext()) {
                    Diagnostic diag = itr.next();
                    System.out.println();
                    System.out.println(ANSI_RED + diag.getMessage() + ANSI_RESET);

                    
                    String prefix = line.substring(0,diag.getSpan().getStart());
                    String error = line.substring(diag.getSpan().getStart(),diag.getSpan().getEnd());
                    String suffix = line.substring(diag.getSpan().getEnd());

                    System.out.println("    " + prefix + ANSI_RED + error + ANSI_RESET+ suffix);
                    System.out.println();

                }
            } else{
                System.out.println("" + result.getValue());
                System.out.println();
            }
        }

    }

}
