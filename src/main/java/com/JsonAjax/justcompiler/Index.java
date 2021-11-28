/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

import com.JsonAjax.justcompiler.Parcing.ExpressionSyntax;
import com.JsonAjax.justcompiler.Parcing.Parser;
import java.io.Console;
import java.util.Scanner;

/**
 *
 * @author hyousfi
 */
public class Index {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
             
            while (true) {
                
                System.out.println("Just>");
                String line = in.nextLine();
                
                Parser parser = new Parser(line);
                ExpressionSyntax expression = parser.parse();
                
                expression.prettyPrint("");

            }

        }

    }
