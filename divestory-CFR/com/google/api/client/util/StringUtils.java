/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private StringUtils() {
    }

    public static byte[] getBytesUtf8(String string2) {
        if (string2 != null) return string2.getBytes(StandardCharsets.UTF_8);
        return null;
    }

    public static String newStringUtf8(byte[] arrby) {
        if (arrby != null) return new String(arrby, StandardCharsets.UTF_8);
        return null;
    }
}

