/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

public final class JsonEncoding
extends Enum<JsonEncoding> {
    private static final /* synthetic */ JsonEncoding[] $VALUES;
    public static final /* enum */ JsonEncoding UTF16_BE;
    public static final /* enum */ JsonEncoding UTF16_LE;
    public static final /* enum */ JsonEncoding UTF32_BE;
    public static final /* enum */ JsonEncoding UTF32_LE;
    public static final /* enum */ JsonEncoding UTF8;
    private final boolean _bigEndian;
    private final int _bits;
    private final String _javaName;

    static {
        JsonEncoding jsonEncoding;
        UTF8 = new JsonEncoding("UTF-8", false, 8);
        UTF16_BE = new JsonEncoding("UTF-16BE", true, 16);
        UTF16_LE = new JsonEncoding("UTF-16LE", false, 16);
        UTF32_BE = new JsonEncoding("UTF-32BE", true, 32);
        UTF32_LE = jsonEncoding = new JsonEncoding("UTF-32LE", false, 32);
        $VALUES = new JsonEncoding[]{UTF8, UTF16_BE, UTF16_LE, UTF32_BE, jsonEncoding};
    }

    private JsonEncoding(String string3, boolean bl, int n2) {
        this._javaName = string3;
        this._bigEndian = bl;
        this._bits = n2;
    }

    public static JsonEncoding valueOf(String string2) {
        return Enum.valueOf(JsonEncoding.class, string2);
    }

    public static JsonEncoding[] values() {
        return (JsonEncoding[])$VALUES.clone();
    }

    public int bits() {
        return this._bits;
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }
}

