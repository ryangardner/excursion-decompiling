/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import com.google.common.escape.Escaper;
import com.google.common.escape.Platform;

public abstract class CharEscaper
extends Escaper {
    private static final int DEST_PAD_MULTIPLIER = 2;

    protected CharEscaper() {
    }

    private static char[] growBuffer(char[] arrc, int n, int n2) {
        if (n2 < 0) throw new AssertionError((Object)"Cannot increase internal buffer any further");
        char[] arrc2 = new char[n2];
        if (n <= 0) return arrc2;
        System.arraycopy(arrc, 0, arrc2, 0, n);
        return arrc2;
    }

    @Override
    public String escape(String string2) {
        Preconditions.checkNotNull(string2);
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            if (this.escape(string2.charAt(n2)) != null) {
                return this.escapeSlow(string2, n2);
            }
            ++n2;
        }
        return string2;
    }

    protected abstract char[] escape(char var1);

    protected final String escapeSlow(String string2, int n) {
        char[] arrc;
        int n2 = string2.length();
        char[] arrc2 = Platform.charBufferFromThreadLocal();
        int n3 = arrc2.length;
        int n4 = 0;
        int n5 = 0;
        int n6 = n;
        n = n5;
        while (n6 < n2) {
            char[] arrc3 = this.escape(string2.charAt(n6));
            if (arrc3 == null) {
                n5 = n3;
            } else {
                int n7 = arrc3.length;
                int n8 = n6 - n4;
                int n9 = n + n8;
                int n10 = n9 + n7;
                arrc = arrc2;
                n5 = n3;
                if (n3 < n10) {
                    n5 = (n2 - n6) * 2 + n10;
                    arrc = CharEscaper.growBuffer(arrc2, n, n5);
                }
                n3 = n;
                if (n8 > 0) {
                    string2.getChars(n4, n6, arrc, n);
                    n3 = n9;
                }
                n = n3;
                if (n7 > 0) {
                    System.arraycopy(arrc3, 0, arrc, n3, n7);
                    n = n3 + n7;
                }
                n4 = n6 + 1;
                arrc2 = arrc;
            }
            ++n6;
            n3 = n5;
        }
        n6 = n2 - n4;
        arrc = arrc2;
        n5 = n;
        if (n6 <= 0) return new String(arrc, 0, n5);
        n5 = n6 + n;
        arrc = arrc2;
        if (n3 < n5) {
            arrc = CharEscaper.growBuffer(arrc2, n, n5);
        }
        string2.getChars(n4, n2, arrc, n);
        return new String(arrc, 0, n5);
    }
}

