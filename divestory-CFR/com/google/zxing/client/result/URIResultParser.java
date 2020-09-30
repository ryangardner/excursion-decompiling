/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser
extends ResultParser {
    private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN;
    private static final Pattern URL_WITH_PROTOCOL_PATTERN;

    static {
        URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
        URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");
    }

    static boolean isBasicallyValidURI(String object) {
        boolean bl = ((String)object).contains(" ");
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        Matcher matcher = URL_WITH_PROTOCOL_PATTERN.matcher((CharSequence)object);
        if (matcher.find() && matcher.start() == 0) {
            return true;
        }
        object = URL_WITHOUT_PROTOCOL_PATTERN.matcher((CharSequence)object);
        bl = bl2;
        if (!((Matcher)object).find()) return bl;
        bl = bl2;
        if (((Matcher)object).start() != 0) return bl;
        return true;
    }

    @Override
    public URIParsedResult parse(Result object) {
        String string2 = URIResultParser.getMassagedText((Result)object);
        boolean bl = string2.startsWith("URL:");
        object = null;
        if (bl) return new URIParsedResult(string2.substring(4).trim(), null);
        if (string2.startsWith("URI:")) {
            return new URIParsedResult(string2.substring(4).trim(), null);
        }
        if (!URIResultParser.isBasicallyValidURI(string2 = string2.trim())) return object;
        return new URIParsedResult(string2, null);
    }
}

