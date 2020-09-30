/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;

public final class PercentEscaper
extends UnicodeEscaper {
    private static final char[] PLUS_SIGN = new char[]{'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String string2, boolean bl) {
        Preconditions.checkNotNull(string2);
        if (string2.matches(".*[0-9A-Za-z].*")) throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        string2 = stringBuilder.toString();
        if (bl) {
            if (string2.contains(" ")) throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = bl;
        this.safeOctets = PercentEscaper.createSafeOctets(string2);
    }

    private static boolean[] createSafeOctets(String arrbl) {
        int n;
        char[] arrc = arrbl.toCharArray();
        int n2 = arrc.length;
        int n3 = 0;
        int n4 = -1;
        for (n = 0; n < n2; ++n) {
            n4 = Math.max(arrc[n], n4);
        }
        arrbl = new boolean[n4 + 1];
        n4 = arrc.length;
        n = n3;
        while (n < n4) {
            arrbl[arrc[n]] = true;
            ++n;
        }
        return arrbl;
    }

    @Override
    public String escape(String arrbl) {
        Preconditions.checkNotNull(arrbl);
        int n = arrbl.length();
        int n2 = 0;
        do {
            Object object = arrbl;
            if (n2 >= n) return object;
            char c = arrbl.charAt(n2);
            if (c >= ((boolean[])(object = this.safeOctets)).length) return this.escapeSlow((String)arrbl, n2);
            if (!object[c]) return this.escapeSlow((String)arrbl, n2);
            ++n2;
        } while (true);
    }

    @Override
    protected char[] escape(int n) {
        Object object = this.safeOctets;
        if (n < ((boolean[])object).length && object[n]) {
            return null;
        }
        if (n == 32 && this.plusForSpace) {
            return PLUS_SIGN;
        }
        if (n <= 127) {
            object = UPPER_HEX_DIGITS;
            char c = object[n & 15];
            return new char[]{'%', (char)object[n >>> 4], c};
        }
        if (n <= 2047) {
            object = UPPER_HEX_DIGITS;
            char c = object[n & 15];
            Object object2 = object[(n >>>= 4) & 3 | 8];
            Object object3 = object[(n >>>= 2) & 15];
            return new char[]{'%', (char)object[n >>> 4 | 12], (char)object3, '%', (char)object2, c};
        }
        if (n <= 65535) {
            object = UPPER_HEX_DIGITS;
            char c = object[n & 15];
            Object object4 = object[(n >>>= 4) & 3 | 8];
            Object object5 = object[(n >>>= 2) & 15];
            Object object6 = object[(n >>>= 4) & 3 | 8];
            return new char[]{'%', 'E', (char)object[n >>> 2], '%', (char)object6, (char)object5, '%', (char)object4, c};
        }
        if (n <= 1114111) {
            object = UPPER_HEX_DIGITS;
            char c = object[n & 15];
            Object object7 = object[(n >>>= 4) & 3 | 8];
            Object object8 = object[(n >>>= 2) & 15];
            Object object9 = object[(n >>>= 4) & 3 | 8];
            Object object10 = object[(n >>>= 2) & 15];
            Object object11 = object[(n >>>= 4) & 3 | 8];
            return new char[]{'%', 'F', (char)object[n >>> 2 & 7], '%', (char)object11, (char)object10, '%', (char)object9, (char)object8, '%', (char)object7, c};
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid unicode character value ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    protected int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        Preconditions.checkNotNull(charSequence);
        while (n < n2) {
            boolean[] arrbl;
            char c = charSequence.charAt(n);
            if (c >= (arrbl = this.safeOctets).length) return n;
            if (!arrbl[c]) {
                return n;
            }
            ++n;
        }
        return n;
    }
}

