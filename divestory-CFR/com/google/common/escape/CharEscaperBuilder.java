/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import com.google.common.escape.CharEscaper;
import com.google.common.escape.Escaper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class CharEscaperBuilder {
    private final Map<Character, String> map = new HashMap<Character, String>();
    private int max = -1;

    public CharEscaperBuilder addEscape(char c, String string2) {
        this.map.put(Character.valueOf(c), Preconditions.checkNotNull(string2));
        if (c <= this.max) return this;
        this.max = c;
        return this;
    }

    public CharEscaperBuilder addEscapes(char[] arrc, String string2) {
        Preconditions.checkNotNull(string2);
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            this.addEscape(arrc[n2], string2);
            ++n2;
        }
        return this;
    }

    public char[][] toArray() {
        char[][] arrarrc = new char[this.max + 1][];
        Iterator<Map.Entry<Character, String>> iterator2 = this.map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Character, String> entry = iterator2.next();
            arrarrc[entry.getKey().charValue()] = entry.getValue().toCharArray();
        }
        return arrarrc;
    }

    public Escaper toEscaper() {
        return new CharArrayDecorator(this.toArray());
    }

    private static class CharArrayDecorator
    extends CharEscaper {
        private final int replaceLength;
        private final char[][] replacements;

        CharArrayDecorator(char[][] arrc) {
            this.replacements = arrc;
            this.replaceLength = arrc.length;
        }

        @Override
        public String escape(String string2) {
            int n = string2.length();
            int n2 = 0;
            while (n2 < n) {
                char[][] arrc;
                char c = string2.charAt(n2);
                if (c < (arrc = this.replacements).length && arrc[c] != null) {
                    return this.escapeSlow(string2, n2);
                }
                ++n2;
            }
            return string2;
        }

        @Override
        protected char[] escape(char c) {
            if (c >= this.replaceLength) return null;
            return this.replacements[c];
        }
    }

}

