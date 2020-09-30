/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class Strings {
    private static final Pattern zza = Pattern.compile("\\$\\{(.*?)\\}");

    private Strings() {
    }

    public static String emptyToNull(String string2) {
        String string3 = string2;
        if (!TextUtils.isEmpty((CharSequence)string2)) return string3;
        return null;
    }

    public static boolean isEmptyOrWhitespace(String string2) {
        if (string2 == null) return true;
        if (!string2.trim().isEmpty()) return false;
        return true;
    }
}

