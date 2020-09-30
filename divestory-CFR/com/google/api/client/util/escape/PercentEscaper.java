/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.escape;

import com.google.api.client.util.escape.UnicodeEscaper;

public class PercentEscaper
extends UnicodeEscaper {
    public static final String SAFECHARS_URLENCODER = "-_.*";
    public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=+";
    public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
    public static final String SAFEUSERINFOCHARS_URLENCODER = "-_.!~*'():$&,;=";
    public static final String SAFE_PLUS_RESERVED_CHARS_URLENCODER = "-_.!~*'()@:$&,;=+/?";
    private static final char[] UPPER_HEX_DIGITS;
    private static final char[] URI_ESCAPED_SPACE;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    static {
        URI_ESCAPED_SPACE = new char[]{'+'};
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }

    public PercentEscaper(String string2) {
        this(string2, false);
    }

    @Deprecated
    public PercentEscaper(String string2, boolean bl) {
        if (string2.matches(".*[0-9A-Za-z].*")) throw new IllegalArgumentException("Alphanumeric ASCII characters are always 'safe' and should not be escaped.");
        if (bl) {
            if (string2.contains(" ")) throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        if (string2.contains("%")) throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        this.plusForSpace = bl;
        this.safeOctets = PercentEscaper.createSafeOctets(string2);
    }

    private static boolean[] createSafeOctets(String arrbl) {
        int n;
        char[] arrc = arrbl.toCharArray();
        int n2 = arrc.length;
        int n3 = 0;
        int n4 = 122;
        for (n = 0; n < n2; ++n) {
            n4 = Math.max(arrc[n], n4);
        }
        arrbl = new boolean[n4 + 1];
        for (n4 = 48; n4 <= 57; ++n4) {
            arrbl[n4] = true;
        }
        for (n4 = 65; n4 <= 90; ++n4) {
            arrbl[n4] = true;
        }
        for (n4 = 97; n4 <= 122; ++n4) {
            arrbl[n4] = true;
        }
        n = arrc.length;
        n4 = n3;
        while (n4 < n) {
            arrbl[arrc[n4]] = true;
            ++n4;
        }
        return arrbl;
    }

    @Override
    public String escape(String arrbl) {
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
            return URI_ESCAPED_SPACE;
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

