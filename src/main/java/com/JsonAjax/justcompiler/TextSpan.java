package com.JsonAjax.justcompiler;

public class TextSpan {
    private int start;
    private int length;
    

    public TextSpan(int start,int length){
        this.start = start;
        this.length = length;
    }


    public int getStart() {
        return start;
    }


    public int getLength() {
        return length;
    }

    public int getEnd(){
        return start + length ;
    } 
    
}
