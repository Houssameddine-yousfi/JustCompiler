package com.JsonAjax.justcompiler.Text;

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


    public static TextSpan fromBounds(int start2, int end) {
        return new TextSpan(start2, end - start2);
    } 
    
}
