/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JsonAjax.justcompiler.Syntax;

import java.io.PrintStream;
import java.util.List;

import com.JsonAjax.justcompiler.Text.TextSpan;


/**
 *
 * @author ajax
 */
public abstract class SyntaxNode{
    public abstract SyntaxKind kind();
    public abstract List<SyntaxNode> getChildren();

    private void prettyPrintRecusive(String indentation,PrintStream out){

        String linePrint = this.kind().toString();
        if(this instanceof SyntaxToken)
            linePrint = linePrint.concat(": '" + ((SyntaxToken) this).getText() + "'");

        out.println(linePrint);
        int i = 1;
        List<SyntaxNode> children = this.getChildren();
        for (SyntaxNode child : children) {
            if(i<children.size()){
                out.print( indentation + "├──");
                child.prettyPrintRecusive(indentation + "│   ", out);
            }else{
                out.print( indentation + "└──");
                child.prettyPrintRecusive(indentation + "    ", out);
            }
            i++;
        }
      
    }

    public void prettyPrint(PrintStream out){
        prettyPrintRecusive("", out);
    }

    public TextSpan getSpan(){
        TextSpan first = getChildren().get(0).getSpan();
        TextSpan last  = getChildren().get(getChildren().size() - 1).getSpan();
        return TextSpan.fromBounds(first.getStart(),last.getEnd());
    }
}
