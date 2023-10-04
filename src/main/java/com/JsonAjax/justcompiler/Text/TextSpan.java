package com.JsonAjax.justcompiler.Text;

public class TextSpan {
    private int start;
    private int length;
    

    public TextSpan(int start,int length){
        this.start = start;
        this.length = length>0?length:0;
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + start;
        result = prime * result + length;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextSpan other = (TextSpan) obj;
        if (start != other.start)
            return false;
        if (length != other.length)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "TextSpan [" + start + "..." + length + "]";
    }

    

    
}
