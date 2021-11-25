/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler;

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
                Lexer lexer = new Lexer(line);
                
                while (true){
                    SyntaxToken token = lexer.nextToken();
                    if (token.getSyntaxKind() == SyntaxKind.endOfFile)
                        break;
                    
                    System.out.print(token);
                }

            }

        }

    }
