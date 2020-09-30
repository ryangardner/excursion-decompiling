/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

import java.util.NoSuchElementException;
import java.util.Vector;

class LineTokenizer {
    private static final String singles = "=";
    private int currentPosition = 0;
    private int maxPosition;
    private Vector stack = new Vector();
    private String str;

    public LineTokenizer(String string2) {
        this.str = string2;
        this.maxPosition = string2.length();
    }

    private void skipWhiteSpace() {
        int n;
        do {
            if ((n = this.currentPosition++) >= this.maxPosition) return;
        } while (Character.isWhitespace(this.str.charAt(n)));
    }

    public boolean hasMoreTokens() {
        if (this.stack.size() > 0) {
            return true;
        }
        this.skipWhiteSpace();
        if (this.currentPosition >= this.maxPosition) return false;
        return true;
    }

    public String nextToken() {
        int n = this.stack.size();
        if (n > 0) {
            Object object = this.stack;
            object = (String)((Vector)object).elementAt(--n);
            this.stack.removeElementAt(n);
            return object;
        }
        this.skipWhiteSpace();
        int n2 = this.currentPosition++;
        if (n2 >= this.maxPosition) throw new NoSuchElementException();
        n = this.str.charAt(n2);
        if (n != 34) {
            if (singles.indexOf(n) >= 0) {
                ++this.currentPosition;
                return this.str.substring(n2, this.currentPosition);
            }
        } else {
            CharSequence charSequence;
            n = 0;
            do {
                int n3;
                if ((n3 = this.currentPosition) >= this.maxPosition) {
                    return this.str.substring(n2, this.currentPosition);
                }
                charSequence = this.str;
                this.currentPosition = n3 + 1;
                if ((n3 = (int)((String)charSequence).charAt(n3)) == 92) {
                    ++this.currentPosition;
                    n = 1;
                    continue;
                }
                if (n3 == 34) break;
            } while (true);
            if (n == 0) {
                return this.str.substring(n2 + 1, this.currentPosition - 1);
            }
            charSequence = new StringBuffer();
            n = n2 + 1;
            do {
                if (n >= this.currentPosition - 1) {
                    return ((StringBuffer)charSequence).toString();
                }
                char c = this.str.charAt(n);
                if (c != '\\') {
                    ((StringBuffer)charSequence).append(c);
                }
                ++n;
            } while (true);
        }
        do {
            if ((n = ++this.currentPosition) >= this.maxPosition) return this.str.substring(n2, this.currentPosition);
            if (singles.indexOf(this.str.charAt(n)) >= 0) return this.str.substring(n2, this.currentPosition);
        } while (!Character.isWhitespace(this.str.charAt(this.currentPosition)));
        return this.str.substring(n2, this.currentPosition);
    }

    public void pushToken(String string2) {
        this.stack.addElement(string2);
    }
}

