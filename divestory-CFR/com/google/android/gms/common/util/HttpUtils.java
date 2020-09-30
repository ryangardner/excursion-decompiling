/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HttpUtils {
    private static final Pattern zza = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern zzb = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern zzc = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private HttpUtils() {
    }

    public static Map<String, String> parse(URI object, String string2) {
        Map<String, String> map = Collections.emptyMap();
        Object object2 = ((URI)object).getRawQuery();
        object = map;
        if (object2 == null) return object;
        object = map;
        if (((String)object2).length() <= 0) return object;
        map = new HashMap<String, String>();
        object2 = new Scanner((String)object2);
        ((Scanner)object2).useDelimiter("&");
        do {
            object = map;
            if (!((Scanner)object2).hasNext()) return object;
            String[] arrstring = ((Scanner)object2).next().split("=");
            if (arrstring.length == 0) throw new IllegalArgumentException("bad parameter");
            if (arrstring.length > 2) throw new IllegalArgumentException("bad parameter");
            String string3 = HttpUtils.zza(arrstring[0], string2);
            object = null;
            if (arrstring.length == 2) {
                object = HttpUtils.zza(arrstring[1], string2);
            }
            map.put(string3, (String)object);
        } while (true);
    }

    private static String zza(String string2, String string3) {
        if (string3 == null) {
            string3 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(string2, string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException(unsupportedEncodingException);
        }
    }
}

