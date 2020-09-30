/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.StringUtils;
import com.google.common.io.BaseEncoding;

public class Base64 {
    private Base64() {
    }

    public static byte[] decodeBase64(String string2) {
        if (string2 == null) {
            return null;
        }
        try {
            return BaseEncoding.base64().decode(string2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            if (!(illegalArgumentException.getCause() instanceof BaseEncoding.DecodingException)) throw illegalArgumentException;
            return BaseEncoding.base64Url().decode(string2.trim());
        }
    }

    public static byte[] decodeBase64(byte[] arrby) {
        return Base64.decodeBase64(StringUtils.newStringUtf8(arrby));
    }

    public static byte[] encodeBase64(byte[] arrby) {
        return StringUtils.getBytesUtf8(Base64.encodeBase64String(arrby));
    }

    public static String encodeBase64String(byte[] arrby) {
        if (arrby != null) return BaseEncoding.base64().encode(arrby);
        return null;
    }

    public static byte[] encodeBase64URLSafe(byte[] arrby) {
        return StringUtils.getBytesUtf8(Base64.encodeBase64URLSafeString(arrby));
    }

    public static String encodeBase64URLSafeString(byte[] arrby) {
        if (arrby != null) return BaseEncoding.base64Url().omitPadding().encode(arrby);
        return null;
    }
}

