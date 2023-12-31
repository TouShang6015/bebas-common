package com.org.bebas.utils.acword;

import java.io.Serializable;
import java.util.*;

/**
 * @author Leego Yih
 */
public class AcNode implements Serializable {
    private static final long serialVersionUID = -6350361756888572415L;
    private final int depth;
    private Map<Character, AcNode> success;
    private AcNode failure;
    private TreeSet<String> keywords;

    public AcNode(int depth) {
        this.depth = depth;
    }

    public AcNode nextState(char c) {
        return nextState(c, false);
    }

    public AcNode nextState(char c, boolean ignoreCase) {
        AcNode next = getState(c, ignoreCase);
        if (next != null) {
            return next;
        } else if (depth == 0) {
            return this;
        }
        return null;
    }

    public AcNode getState(char c) {
        return success != null ? success.get(c) : null;
    }

    public AcNode getState(char c, boolean ignoreCase) {
        if (success == null) {
            return null;
        }
        AcNode state = success.get(c);
        if (state != null) {
            return state;
        }
        if (ignoreCase) {
            char cc;
            if (Character.isLowerCase(c)) {
                cc = Character.toUpperCase(c);
            } else if (Character.isUpperCase(c)) {
                cc = Character.toLowerCase(c);
            } else {
                cc = c;
            }
            if (c != cc) {
                return success.get(cc);
            }
        }
        return null;
    }

    public AcNode addNode(CharSequence cs) {
        AcNode state = this;
        for (int i = 0; i < cs.length(); i++) {
            state = state.addNode(cs.charAt(i));
        }
        return state;
    }

    public AcNode addNode(char c) {
        if (success == null) {
            success = new HashMap<>();
        }
        AcNode state = success.get(c);
        if (state == null) {
            state = new AcNode(depth + 1);
            success.put(c, state);
        }
        return state;
    }

    public void addKeyword(String keyword) {
        if (this.keywords == null) {
            this.keywords = new TreeSet<>();
        }
        this.keywords.add(keyword);
    }

    public void addKeywords(Collection<String> keywords) {
        if (this.keywords == null) {
            this.keywords = new TreeSet<>();
        }
        this.keywords.addAll(keywords);
    }

    public Set<String> getKeywords() {
        return keywords != null ? keywords : Collections.emptySet();
    }

    public String getFirstKeyword() {
        return keywords != null && keywords.size() > 0 ? keywords.first() : null;
    }

    public AcNode getFailure() {
        return failure;
    }

    public void setFailure(AcNode failure) {
        this.failure = failure;
    }

    public Map<Character, AcNode> getSuccess() {
        return success != null ? success : Collections.emptyMap();
    }

    public int getDepth() {
        return depth;
    }

    public boolean isRoot() {
        return depth == 0;
    }
}