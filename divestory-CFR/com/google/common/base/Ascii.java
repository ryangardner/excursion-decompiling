/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;

public final class Ascii {
    public static final byte ACK = 6;
    public static final byte BEL = 7;
    public static final byte BS = 8;
    public static final byte CAN = 24;
    private static final char CASE_MASK = ' ';
    public static final byte CR = 13;
    public static final byte DC1 = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte DC4 = 20;
    public static final byte DEL = 127;
    public static final byte DLE = 16;
    public static final byte EM = 25;
    public static final byte ENQ = 5;
    public static final byte EOT = 4;
    public static final byte ESC = 27;
    public static final byte ETB = 23;
    public static final byte ETX = 3;
    public static final byte FF = 12;
    public static final byte FS = 28;
    public static final byte GS = 29;
    public static final byte HT = 9;
    public static final byte LF = 10;
    public static final char MAX = '';
    public static final char MIN = '\u0000';
    public static final byte NAK = 21;
    public static final byte NL = 10;
    public static final byte NUL = 0;
    public static final byte RS = 30;
    public static final byte SI = 15;
    public static final byte SO = 14;
    public static final byte SOH = 1;
    public static final byte SP = 32;
    public static final byte SPACE = 32;
    public static final byte STX = 2;
    public static final byte SUB = 26;
    public static final byte SYN = 22;
    public static final byte US = 31;
    public static final byte VT = 11;
    public static final byte XOFF = 19;
    public static final byte XON = 17;

    private Ascii() {
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int n = charSequence.length();
        if (charSequence == charSequence2) {
            return true;
        }
        if (n != charSequence2.length()) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            char c;
            char c2 = charSequence.charAt(n2);
            if (c2 != (c = charSequence2.charAt(n2))) {
                int n3 = Ascii.getAlphaIndex(c2);
                if (n3 >= 26) return false;
                if (n3 != Ascii.getAlphaIndex(c)) return false;
            }
            ++n2;
        }
        return true;
    }

    private static int getAlphaIndex(char c) {
        return (char)((c | 32) - 97);
    }

    public static boolean isLowerCase(char c) {
        if (c < 'a') return false;
        if (c > 'z') return false;
        return true;
    }

    public static boolean isUpperCase(char c) {
        if (c < 'A') return false;
        if (c > 'Z') return false;
        return true;
    }

    public static char toLowerCase(char c) {
        char c2 = c;
        if (!Ascii.isUpperCase(c)) return c2;
        char c3 = (char)(c ^ 32);
        return c3;
    }

    public static String toLowerCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return Ascii.toLowerCase((String)charSequence);
        }
        int n = charSequence.length();
        char[] arrc = new char[n];
        int n2 = 0;
        while (n2 < n) {
            arrc[n2] = Ascii.toLowerCase(charSequence.charAt(n2));
            ++n2;
        }
        return String.valueOf(arrc);
    }

    public static String toLowerCase(String arrc) {
        int n = arrc.length();
        int n2 = 0;
        while (n2 < n) {
            if (Ascii.isUpperCase(arrc.charAt(n2))) {
                arrc = arrc.toCharArray();
                while (n2 < n) {
                    char c = arrc[n2];
                    if (Ascii.isUpperCase(c)) {
                        arrc[n2] = (char)(c ^ 32);
                    }
                    ++n2;
                }
                return String.valueOf(arrc);
            }
            ++n2;
        }
        return arrc;
    }

    public static char toUpperCase(char c) {
        char c2 = c;
        if (!Ascii.isLowerCase(c)) return c2;
        char c3 = (char)(c ^ 32);
        return c3;
    }

    public static String toUpperCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return Ascii.toUpperCase((String)charSequence);
        }
        int n = charSequence.length();
        char[] arrc = new char[n];
        int n2 = 0;
        while (n2 < n) {
            arrc[n2] = Ascii.toUpperCase(charSequence.charAt(n2));
            ++n2;
        }
        return String.valueOf(arrc);
    }

    public static String toUpperCase(String arrc) {
        int n = arrc.length();
        int n2 = 0;
        while (n2 < n) {
            if (Ascii.isLowerCase(arrc.charAt(n2))) {
                arrc = arrc.toCharArray();
                while (n2 < n) {
                    char c = arrc[n2];
                    if (Ascii.isLowerCase(c)) {
                        arrc[n2] = (char)(c ^ 32);
                    }
                    ++n2;
                }
                return String.valueOf(arrc);
            }
            ++n2;
        }
        return arrc;
    }

    public static String truncate(CharSequence charSequence, int n, String string2) {
        Preconditions.checkNotNull(charSequence);
        int n2 = n - string2.length();
        boolean bl = n2 >= 0;
        Preconditions.checkArgument(bl, "maxLength (%s) must be >= length of the truncation indicator (%s)", n, string2.length());
        CharSequence charSequence2 = charSequence;
        if (charSequence.length() <= n) {
            charSequence2 = charSequence = charSequence.toString();
            if (((String)charSequence).length() <= n) {
                return charSequence;
            }
        }
        charSequence = new StringBuilder(n);
        ((StringBuilder)charSequence).append(charSequence2, 0, n2);
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }
}

