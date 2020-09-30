/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.escape;

import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.PercentEscaper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class CharEscapers {
    private static final Escaper APPLICATION_X_WWW_FORM_URLENCODED = new PercentEscaper("-_.*", true);
    private static final Escaper URI_ESCAPER = new PercentEscaper("-_.*", false);
    private static final Escaper URI_PATH_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=+");
    private static final Escaper URI_QUERY_STRING_ESCAPER;
    private static final Escaper URI_RESERVED_ESCAPER;
    private static final Escaper URI_USERINFO_ESCAPER;

    static {
        URI_RESERVED_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=+/?");
        URI_USERINFO_ESCAPER = new PercentEscaper("-_.!~*'():$&,;=");
        URI_QUERY_STRING_ESCAPER = new PercentEscaper("-_.!~*'()@:$,;/?:");
    }

    private CharEscapers() {
    }

    public static String decodeUri(String string2) {
        try {
            return URLDecoder.decode(string2, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String decodeUriPath(String string2) {
        if (string2 == null) {
            return null;
        }
        try {
            return URLDecoder.decode(string2.replace("+", "%2B"), StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    @Deprecated
    public static String escapeUri(String string2) {
        return APPLICATION_X_WWW_FORM_URLENCODED.escape(string2);
    }

    public static String escapeUriConformant(String string2) {
        return URI_ESCAPER.escape(string2);
    }

    public static String escapeUriPath(String string2) {
        return URI_PATH_ESCAPER.escape(string2);
    }

    public static String escapeUriPathWithoutReserved(String string2) {
        return URI_RESERVED_ESCAPER.escape(string2);
    }

    public static String escapeUriQuery(String string2) {
        return URI_QUERY_STRING_ESCAPER.escape(string2);
    }

    public static String escapeUriUserInfo(String string2) {
        return URI_USERINFO_ESCAPER.escape(string2);
    }
}

