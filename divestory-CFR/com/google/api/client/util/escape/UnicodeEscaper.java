/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.escape;

import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.Platform;

public abstract class UnicodeEscaper
extends Escaper {
    private static final int DEST_PAD = 32;

    protected static int codePointAt(CharSequence charSequence, int n, int n2) {
        if (n >= n2) throw new IndexOutOfBoundsException("Index exceeds specified range");
        int n3 = n + 1;
        char c = charSequence.charAt(n);
        if (c < '\ud800') return c;
        if (c > '\udfff') {
            return c;
        }
        if (c > '\udbff') {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unexpected low surrogate character '");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append("' with value ");
            ((StringBuilder)charSequence).append((int)c);
            ((StringBuilder)charSequence).append(" at index ");
            ((StringBuilder)charSequence).append(n3 - 1);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (n3 == n2) {
            return -c;
        }
        char c2 = charSequence.charAt(n3);
        if (Character.isLowSurrogate(c2)) {
            return Character.toCodePoint(c, c2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Expected low surrogate but got char '");
        ((StringBuilder)charSequence).append(c2);
        ((StringBuilder)charSequence).append("' with value ");
        ((StringBuilder)charSequence).append((int)c2);
        ((StringBuilder)charSequence).append(" at index ");
        ((StringBuilder)charSequence).append(n3);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static char[] growBuffer(char[] arrc, int n, int n2) {
        char[] arrc2 = new char[n2];
        if (n <= 0) return arrc2;
        System.arraycopy(arrc, 0, arrc2, 0, n);
        return arrc2;
    }

    @Override
    public abstract String escape(String var1);

    protected abstract char[] escape(int var1);

    protected final String escapeSlow(String string2, int n) {
        char[] arrc;
        int n2 = string2.length();
        char[] arrc2 = Platform.charBufferFromThreadLocal();
        int n3 = 0;
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n5 < n2) {
            n4 = UnicodeEscaper.codePointAt(string2, n5, n2);
            if (n4 < 0) throw new IllegalArgumentException("Trailing high surrogate at end of input");
            char[] arrc3 = this.escape(n4);
            n4 = Character.isSupplementaryCodePoint(n4) ? 2 : 1;
            int n6 = n4 + n5;
            arrc = arrc2;
            int n7 = n3;
            n4 = n;
            if (arrc3 != null) {
                int n8 = n5 - n3;
                n7 = n + n8;
                n4 = arrc3.length + n7;
                arrc = arrc2;
                if (arrc2.length < n4) {
                    arrc = UnicodeEscaper.growBuffer(arrc2, n, n4 + n2 - n5 + 32);
                }
                n4 = n;
                if (n8 > 0) {
                    string2.getChars(n3, n5, arrc, n);
                    n4 = n7;
                }
                n = n4;
                if (arrc3.length > 0) {
                    System.arraycopy(arrc3, 0, arrc, n4, arrc3.length);
                    n = n4 + arrc3.length;
                }
                n7 = n6;
                n4 = n;
            }
            n5 = this.nextEscapeIndex(string2, n6, n2);
            arrc2 = arrc;
            n3 = n7;
            n = n4;
        }
        n5 = n2 - n3;
        arrc = arrc2;
        n4 = n;
        if (n5 <= 0) return new String(arrc, 0, n4);
        n4 = n5 + n;
        arrc = arrc2;
        if (arrc2.length < n4) {
            arrc = UnicodeEscaper.growBuffer(arrc2, n, n4);
        }
        string2.getChars(n3, n2, arrc, n);
        return new String(arrc, 0, n4);
    }

    protected abstract int nextEscapeIndex(CharSequence var1, int var2, int var3);
}

