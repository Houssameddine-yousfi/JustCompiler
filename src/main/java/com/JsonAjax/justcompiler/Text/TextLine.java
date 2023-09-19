package com.JsonAjax.justcompiler.Text;

public final class TextLine {
    private SourceText text;
    private int start;
    private int length;
    private int lengthIncludingLineBreak;
    
    public TextLine(SourceText text, int stat, int length, int lengthIncludingLineBreak) {
        this.text = text;
        this.start = stat;
        this.length = length;
        this.lengthIncludingLineBreak = lengthIncludingLineBreak;
    }

    public SourceText getText() {
        return text;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getLengthIncludingLineBreak() {
        return lengthIncludingLineBreak;
    }


    public int getEnd(){
        return start + length;
    }

    public TextSpan getTextSpan(){
        return new TextSpan(this.start, this.length);
    }

    public TextSpan getTextSpanIncludingLineBreak(){
        return new TextSpan(this.start, this.lengthIncludingLineBreak);
    }

    @Override
    public String toString() {
        return  text.toString(getTextSpan());
    }
}
