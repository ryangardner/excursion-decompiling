/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;

public final class Base64Variants {
    public static final Base64Variant MIME = new Base64Variant("MIME", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", true, '=', 76);
    public static final Base64Variant MIME_NO_LINEFEEDS = new Base64Variant(MIME, "MIME-NO-LINEFEEDS", Integer.MAX_VALUE);
    public static final Base64Variant MODIFIED_FOR_URL;
    public static final Base64Variant PEM;
    static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    static {
        PEM = new Base64Variant(MIME, "PEM", true, '=', 64);
        StringBuilder stringBuilder = new StringBuilder(STD_BASE64_ALPHABET);
        stringBuilder.setCharAt(stringBuilder.indexOf("+"), '-');
        stringBuilder.setCharAt(stringBuilder.indexOf("/"), '_');
        MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", stringBuilder.toString(), false, '\u0000', Integer.MAX_VALUE);
    }

    public static Base64Variant getDefaultVariant() {
        return MIME_NO_LINEFEEDS;
    }

    public static Base64Variant valueOf(String string2) throws IllegalArgumentException {
        StringBuilder stringBuilder;
        if (Base64Variants.MIME._name.equals(string2)) {
            return MIME;
        }
        if (Base64Variants.MIME_NO_LINEFEEDS._name.equals(string2)) {
            return MIME_NO_LINEFEEDS;
        }
        if (Base64Variants.PEM._name.equals(string2)) {
            return PEM;
        }
        if (Base64Variants.MODIFIED_FOR_URL._name.equals(string2)) {
            return MODIFIED_FOR_URL;
        }
        if (string2 == null) {
            string2 = "<null>";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(string2);
            stringBuilder.append("'");
            string2 = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("No Base64Variant with name ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

