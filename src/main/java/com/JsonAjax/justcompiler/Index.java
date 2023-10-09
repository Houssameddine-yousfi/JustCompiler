/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import com.JsonAjax.justcompiler.Syntax.SyntaxTree;
import com.JsonAjax.justcompiler.Text.SourceText;
import com.JsonAjax.justcompiler.Text.TextLine;
import com.JsonAjax.justcompiler.Text.TextSpan;

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

        Map<VariableSymbol, Object> variables = new HashMap<>();
        Compilation previous = null;
        StringBuilder textBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);

        boolean showATree = false;
        while (true) {

            if (textBuilder.length() == 0)
                System.out.print(ANSI_GREEN + "Just> " + ANSI_RESET);
            else
                System.out.print(ANSI_GREEN + "|     " + ANSI_RESET);

            String line = in.nextLine();
            

            if (textBuilder.length() == 0) {
                if (line.isBlank() || line.isEmpty()){
                    System.out.println();
                    continue;
                }
                    

                else if (line.equals("#showTree")) {
                    showATree = !showATree;
                    System.out.println(showATree ? "Showing parse tree." : "Hide parse tree.");
                    continue;
                }

                else if (line.equals("#exit")) {
                    in.close();
                    System.exit(0);
                }

                else if (line.equals("#reset")) {
                    previous = null;
                    System.out.println("Terminal has been reset! all variables has been droped.\n");
                    continue;
                }
            }

    

            textBuilder.append(line+"\n");
            String rawText = textBuilder.toString();

            SyntaxTree ast = SyntaxTree.parse(rawText);

            if(!line.isBlank() && !line.isEmpty() && !ast.getDiagnostics().isEmpty())
                continue;

            Compilation compilation = previous == null? 
                new Compilation(ast):
                previous.continueWith(ast);
            EvaluationResult result = compilation.evaluate(variables);

            DiagnosticsBag diagnostics = result.getDiagnostics();

            if (showATree)
                ast.getRoot().prettyPrint("", System.out);

            // if we find errors we display them else we evaluate
            if (!diagnostics.isEmpty()) {
                Iterator<Diagnostic> itr = diagnostics.iterator();
                SourceText text = ast.getText();
                while (itr.hasNext()) {
                    Diagnostic diag = itr.next();

                    int lineIndex = text.getLineIndex(diag.getSpan().getStart());
                    TextLine errLine = ast.getText().getLines().get(lineIndex);
                    int lineNumber = lineIndex + 1;
                    int character = diag.getSpan().getStart() - text.getLines().get(lineIndex).getStart() + 1;


                    TextSpan prefixSpan = TextSpan.fromBounds(errLine.getStart(), diag.getSpan().getStart());
                    TextSpan suffixSpan = TextSpan.fromBounds(diag.getSpan().getEnd(), errLine.getEnd());
                    
                    
                    System.out.println();
                    System.out.print(ANSI_RED);
                    System.out.print("[" + lineNumber + "," + character + "]: ");
                    System.out.println(diag.getMessage() + ANSI_RESET);

                    String prefix = ast.getText().toString(prefixSpan);
                    String error = ast.getText().toString(diag.getSpan());
                    String suffix = ast.getText().toString(suffixSpan);

                    System.out.println("    " + prefix + ANSI_RED + error + ANSI_RESET + suffix);
                    System.out.println();

                }
            } else {
                System.out.println(ANSI_PURPLE + result.getValue() + ANSI_RESET);
                System.out.println();
                previous = compilation;
            }

            textBuilder = new StringBuilder();
        }

    }

}
