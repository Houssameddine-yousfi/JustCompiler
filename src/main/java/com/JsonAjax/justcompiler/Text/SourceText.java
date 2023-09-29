package com.JsonAjax.justcompiler.Text;

import java.util.ArrayList;
import java.util.List;

public final class SourceText {

    private String text;
    private List<TextLine> lines;

    private SourceText(String text) {
        this.text = text;
        lines = parseLines(this, text);
    }

    

    public List<TextLine> getLines() {
        return lines;
    }



    public int getLineIndex(int position){
        int lower =0;
        int upper = this.lines.size() - 1;

        while(lower <= upper){
            int index = lower + (upper - lower) / 2;
            int start = lines.get(index).getStart();

            if(position == start)
                return index;
            
            if(start > position)
            {
                upper = index - 1;
            }
            else
            {
                lower = index + 1;
            }
        }

        return lower -1;
    }

    private static List<TextLine> parseLines(SourceText sourceText, String text) {

        List<TextLine> lines = new ArrayList<>();
        int lineStart = 0;
        int position = 0;

        while ( position < text.length()) {
            int lineBreakWidth = GetLineBreakWidth(text, position);
            if(lineBreakWidth == 0)
                position ++;
            else{
                addLine(lines, sourceText, lineStart, position, lineBreakWidth);
                
                position += lineBreakWidth;
                lineStart = position;
            }
        }

        if(position>=lineStart){
            addLine(lines, sourceText, lineStart, position, 0);
        }

        return lines;
    }

    private static void addLine(List<TextLine> lines, SourceText sourceText, int lineStart, int position, int lineBreakWidth) {
        int linelength = position - lineStart;
        int lineLengthIncludingLineBreak = linelength + lineBreakWidth;
        TextLine line = new TextLine(sourceText, lineStart, linelength, lineLengthIncludingLineBreak);
        lines.add(line);
    }

    private static int GetLineBreakWidth(String text, int position) {
        
        char c =  text.charAt(position);
        char l = position+1 >= text.length()? '\0' : text.charAt(position+1);

        if (c == '\r' && l == '\n')
            return 2;
        
        if(c == '\r' || c == '\n')
            return 1;
        
        return 0;
    }

    public static SourceText from(String text) {

        return new SourceText(text);
    }



    @Override
    public String toString() {
        return  text;
    }

    public String toString(int start, int length){
        return text.substring(start, start+length);
    }
    
    public String toString(TextSpan span){
        return text.substring(span.getStart(), span.getEnd());
    }

}
