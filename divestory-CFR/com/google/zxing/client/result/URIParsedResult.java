/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIParsedResult
extends ParsedResult {
    private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String title;
    private final String uri;

    public URIParsedResult(String string2, String string3) {
        super(ParsedResultType.URI);
        this.uri = URIParsedResult.massageURI(string2);
        this.title = string3;
    }

    private static boolean isColonFollowedByPortNumber(String string2, int n) {
        int n2;
        int n3 = n + 1;
        n = n2 = string2.indexOf(47, n3);
        if (n2 >= 0) return ResultParser.isSubstringOfDigits(string2, n3, n - n3);
        n = string2.length();
        return ResultParser.isSubstringOfDigits(string2, n3, n - n3);
    }

    private static String massageURI(String charSequence) {
        String string2 = ((String)charSequence).trim();
        int n = string2.indexOf(58);
        if (n >= 0) {
            charSequence = string2;
            if (!URIParsedResult.isColonFollowedByPortNumber(string2, n)) return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("http://");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        URIParsedResult.maybeAppend(this.title, stringBuilder);
        URIParsedResult.maybeAppend(this.uri, stringBuilder);
        return stringBuilder.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getURI() {
        return this.uri;
    }

    public boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }
}

