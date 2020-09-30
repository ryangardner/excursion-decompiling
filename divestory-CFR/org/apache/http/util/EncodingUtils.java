/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.io.UnsupportedEncodingException;

public final class EncodingUtils {
    private EncodingUtils() {
    }

    public static byte[] getAsciiBytes(String arrby) {
        if (arrby == null) throw new IllegalArgumentException("Parameter may not be null");
        try {
            return arrby.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new Error("HttpClient requires ASCII support");
        }
    }

    public static String getAsciiString(byte[] arrby) {
        if (arrby == null) throw new IllegalArgumentException("Parameter may not be null");
        return EncodingUtils.getAsciiString(arrby, 0, arrby.length);
    }

    public static String getAsciiString(byte[] object, int n, int n2) {
        if (object == null) throw new IllegalArgumentException("Parameter may not be null");
        try {
            return new String((byte[])object, n, n2, "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new Error("HttpClient requires ASCII support");
        }
    }

    public static byte[] getBytes(String string2, String arrby) {
        if (string2 == null) throw new IllegalArgumentException("data may not be null");
        if (arrby == null) throw new IllegalArgumentException("charset may not be null or empty");
        if (arrby.length() == 0) throw new IllegalArgumentException("charset may not be null or empty");
        try {
            return string2.getBytes((String)arrby);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return string2.getBytes();
        }
    }

    public static String getString(byte[] arrby, int n, int n2, String string2) {
        if (arrby == null) throw new IllegalArgumentException("Parameter may not be null");
        if (string2 == null) throw new IllegalArgumentException("charset may not be null or empty");
        if (string2.length() == 0) throw new IllegalArgumentException("charset may not be null or empty");
        try {
            return new String(arrby, n, n2, string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(arrby, n, n2);
        }
    }

    public static String getString(byte[] arrby, String string2) {
        if (arrby == null) throw new IllegalArgumentException("Parameter may not be null");
        return EncodingUtils.getString(arrby, 0, arrby.length, string2);
    }
}

