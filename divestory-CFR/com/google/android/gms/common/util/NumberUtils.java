/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

public class NumberUtils {
    private NumberUtils() {
    }

    public static long parseHexLong(String string2) {
        if (string2.length() <= 16) {
            if (string2.length() != 16) return Long.parseLong(string2, 16);
            long l = Long.parseLong(string2.substring(8), 16);
            return Long.parseLong(string2.substring(0, 8), 16) << 32 | l;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 37);
        stringBuilder.append("Invalid input: ");
        stringBuilder.append(string2);
        stringBuilder.append(" exceeds 16 characters");
        throw new NumberFormatException(stringBuilder.toString());
    }
}

