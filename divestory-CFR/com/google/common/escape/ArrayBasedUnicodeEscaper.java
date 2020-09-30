/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedEscaperMap;
import com.google.common.escape.UnicodeEscaper;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ArrayBasedUnicodeEscaper
extends UnicodeEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMax;
    private final char safeMaxChar;
    private final int safeMin;
    private final char safeMinChar;

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap arrc, int n, int n2, @NullableDecl String string2) {
        Preconditions.checkNotNull(arrc);
        arrc = arrc.getReplacementArray();
        this.replacements = arrc;
        this.replacementsLength = arrc.length;
        int n3 = n;
        int n4 = n2;
        if (n2 < n) {
            n4 = -1;
            n3 = Integer.MAX_VALUE;
        }
        this.safeMin = n3;
        this.safeMax = n4;
        if (n3 >= 55296) {
            this.safeMinChar = (char)65535;
            this.safeMaxChar = (char)(false ? 1 : 0);
            return;
        }
        this.safeMinChar = (char)n3;
        this.safeMaxChar = (char)Math.min(n4, 55295);
    }

    protected ArrayBasedUnicodeEscaper(Map<Character, String> map, int n, int n2, @NullableDecl String string2) {
        this(ArrayBasedEscaperMap.create(map), n, n2, string2);
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
            if (c > this.safeMaxChar) return this.escapeSlow(string2, n);
            if (c < this.safeMinChar) return this.escapeSlow(string2, n);
            ++n;
        } while (true);
    }

    @Override
    protected final char[] escape(int n) {
        char[] arrc;
        if (n < this.replacementsLength && (arrc = this.replacements[n]) != null) {
            return arrc;
        }
        if (n < this.safeMin) return this.escapeUnsafe(n);
        if (n > this.safeMax) return this.escapeUnsafe(n);
        return null;
    }

    protected abstract char[] escapeUnsafe(int var1);

    @Override
    protected final int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        while (n < n2) {
            char c = charSequence.charAt(n);
            if (c < this.replacementsLength) {
                if (this.replacements[c] != null) return n;
            }
            if (c > this.safeMaxChar) return n;
            if (c < this.safeMinChar) {
                return n;
            }
            ++n;
        }
        return n;
    }
}

