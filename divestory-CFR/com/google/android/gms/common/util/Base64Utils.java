/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.common.util;

import android.util.Base64;

public final class Base64Utils {
    public static byte[] decode(String string2) {
        if (string2 != null) return Base64.decode((String)string2, (int)0);
        return null;
    }

    public static byte[] decodeUrlSafe(String string2) {
        if (string2 != null) return Base64.decode((String)string2, (int)10);
        return null;
    }

    public static byte[] decodeUrlSafeNoPadding(String string2) {
        if (string2 != null) return Base64.decode((String)string2, (int)11);
        return null;
    }

    public static String encode(byte[] arrby) {
        if (arrby != null) return Base64.encodeToString((byte[])arrby, (int)0);
        return null;
    }

    public static String encodeUrlSafe(byte[] arrby) {
        if (arrby != null) return Base64.encodeToString((byte[])arrby, (int)10);
        return null;
    }

    public static String encodeUrlSafeNoPadding(byte[] arrby) {
        if (arrby != null) return Base64.encodeToString((byte[])arrby, (int)11);
        return null;
    }
}

