/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedEscaperMap;
import com.google.common.escape.CharEscaper;
import java.util.Map;

public abstract class ArrayBasedCharEscaper
extends CharEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final char safeMax;
    private final char safeMin;

    protected ArrayBasedCharEscaper(ArrayBasedEscaperMap arrc, char c, char c2) {
        Preconditions.checkNotNull(arrc);
        arrc = arrc.getReplacementArray();
        this.replacements = arrc;
        this.replacementsLength = arrc.length;
        char c3 = c;
        char c4 = c2;
        if (c2 < c) {
            c4 = '\u0000';
            c3 = '\uffff';
        }
        this.safeMin = c3;
        this.safeMax = c4;
    }

    protected ArrayBasedCharEscaper(Map<Character, String> map, char c, char c2) {
        this(ArrayBasedEscaperMap.create(map), c, c2);
    }

    @Override
    public final String escape(String string2) {
        Preconditions.checkNotNull(string2);
        int n = 0;
        do {
            String string3 = string2;
            if (n >= string2.length()) return string3;
            char c = string2.charAt(n);
            if (c < this.replacementsLength) {
                if (this.replacements[c] != null) return this.escapeSlow(string2, n);
            }
            if (c > this.safeMax) return this.escapeSlow(string2, n);
            if (c < this.safeMin) return this.escapeSlow(string2, n);
            ++n;
        } while (true);
    }

    @Override
    protected final char[] escape(char c) {
        char[] arrc;
        if (c < this.replacementsLength && (arrc = this.replacements[c]) != null) {
            return arrc;
        }
        if (c < this.safeMin) return this.escapeUnsafe(c);
        if (c > this.safeMax) return this.escapeUnsafe(c);
        return null;
    }

    protected abstract char[] escapeUnsafe(char var1);
}

