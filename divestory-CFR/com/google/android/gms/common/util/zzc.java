/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzc {
    private static final Pattern zza = Pattern.compile("\\\\u[0-9a-fA-F]{4}");

    public static String zza(String string2) {
        String string3 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) return string3;
        Matcher matcher = zza.matcher(string2);
        string3 = null;
        while (matcher.find()) {
            CharSequence charSequence = string3;
            if (string3 == null) {
                charSequence = new StringBuffer();
            }
            matcher.appendReplacement((StringBuffer)charSequence, new String(Character.toChars(Integer.parseInt(matcher.group().substring(2), 16))));
            string3 = charSequence;
        }
        if (string3 == null) {
            return string2;
        }
        matcher.appendTail((StringBuffer)((Object)string3));
        return ((StringBuffer)((Object)string3)).toString();
    }
}

