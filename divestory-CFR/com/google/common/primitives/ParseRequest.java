/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

final class ParseRequest {
    final int radix;
    final String rawValue;

    private ParseRequest(String string2, int n) {
        this.rawValue = string2;
        this.radix = n;
    }

    static ParseRequest fromString(String string2) {
        if (string2.length() == 0) throw new NumberFormatException("empty string");
        char c = string2.charAt(0);
        boolean bl = string2.startsWith("0x");
        int n = 16;
        if (!bl && !string2.startsWith("0X")) {
            if (c == '#') {
                string2 = string2.substring(1);
                return new ParseRequest(string2, n);
            }
            if (c == '0' && string2.length() > 1) {
                string2 = string2.substring(1);
                n = 8;
                return new ParseRequest(string2, n);
            }
            n = 10;
            return new ParseRequest(string2, n);
        }
        string2 = string2.substring(2);
        return new ParseRequest(string2, n);
    }
}

