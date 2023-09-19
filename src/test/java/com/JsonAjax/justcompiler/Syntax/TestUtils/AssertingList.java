package com.JsonAjax.justcompiler.Syntax.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.JsonAjax.justcompiler.Syntax.SyntaxKind;
import com.JsonAjax.justcompiler.Syntax.SyntaxNode;
import com.JsonAjax.justcompiler.Syntax.SyntaxToken;

public final class AssertingList {

    List<SyntaxNode> flattenTree;
    Iterator<SyntaxNode> flattenTreeIterator;

    public AssertingList(SyntaxNode node) {
        this.flattenTree = flatten(node);
        this.flattenTreeIterator = flattenTree.iterator();
    }

    private List<SyntaxNode> flatten(SyntaxNode node) {
        List<SyntaxNode> flattenTree = new ArrayList<>();
        Stack<SyntaxNode> stack = new Stack<>();
        stack.push(node);

        while (!stack.empty()) {
            SyntaxNode n = stack.pop();
            flattenTree.add(n);

            List<SyntaxNode> children = n.getChildren();
            Collections.reverse(children);
            for (SyntaxNode child : children) {
                stack.push(child);
            }
        }
        return flattenTree;
    }

    public void AssertNode(SyntaxKind kind) {
        assertTrue(flattenTreeIterator.hasNext());
        SyntaxNode node = flattenTreeIterator.next();

        assertTrue(!(node instanceof SyntaxToken));
        assertEquals(kind, node.kind());
    }

    public void AssertToken(SyntaxKind kind, String text) {
        assertTrue(flattenTreeIterator.hasNext());
        SyntaxNode node = flattenTreeIterator.next();

        assertTrue(node instanceof SyntaxToken);
        assertEquals(kind, node.kind());
        assertEquals(text, ((SyntaxToken) node).getText());
    }
}
