/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.internal;

public final class StringUtils {
    private StringUtils() {
    }

    private static boolean isPrintableChar(char c) {
        if (c < ' ') return false;
        if (c > '~') return false;
        return true;
    }

    public static boolean isPrintableString(String string2) {
        int n = 0;
        while (n < string2.length()) {
            if (!StringUtils.isPrintableChar(string2.charAt(n))) {
                return false;
            }
            ++n;
        }
        return true;
    }
}

