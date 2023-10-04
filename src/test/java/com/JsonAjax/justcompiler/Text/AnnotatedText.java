package com.JsonAjax.justcompiler.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AnnotatedText {
    
    String text;
    List<TextSpan> spans;

    
    public AnnotatedText(String text, List<TextSpan> spans) {
        this.text = text;
        this.spans = spans;
    }


    public String getText() {
        return text;
    }


    public List<TextSpan> getSpans() {
        return spans;
    }

    public static AnnotatedText Parse(String text){
        text = unindent(text);

        StringBuilder textBuilder = new StringBuilder();
        List<TextSpan> spans =  new ArrayList<>();
        Stack<Integer> startStack = new Stack<>();
        int position = 0;
        for (char c : text.toCharArray()) {
            if(c == '['){
                startStack.push(position);
            } else if (c == ']'){
                if( startStack.size() == 0)
                    throw new RuntimeException("Too many \']\' in text " + text);

                int start = startStack.pop();
                int end = position;
                spans.add(TextSpan.fromBounds(start, end));
            } else {
                position++;
                textBuilder.append(c);
            }
        }

        if(startStack.size() != 0)
            throw new RuntimeException("missing \']\' in text " + text);

        
        return new AnnotatedText(textBuilder.toString(), spans);
    }

    private static String unindent(String text){
        List<String> lines = unindentLines(text);
        return String.join("\n", lines);
    }


    public static List<String> unindentLines(String text){
        List<String> workingLines = new ArrayList<>();
        List<String> returnLines = new ArrayList<>();
        

        BufferedReader buffReader = new BufferedReader( new StringReader(text));
        int minIndentation = Integer.MAX_VALUE;
        String line;
        try {
            while( (line = buffReader.readLine()) != null){
                
                // ind the minimum indentation
                String trimed = line.trim();
                if(trimed.length() == 0)
                    continue;

                int indentatation = line.indexOf(trimed);
                minIndentation = Math.min(minIndentation, indentatation);

                // add line to line list
                workingLines.add(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        for(int i = 0; i < workingLines.size(); i++){
            returnLines.add(workingLines.get(i).substring(minIndentation));
        } 

        return returnLines;
    }
}
