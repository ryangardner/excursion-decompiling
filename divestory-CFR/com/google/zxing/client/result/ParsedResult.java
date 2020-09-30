/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResultType;

public abstract class ParsedResult {
    private final ParsedResultType type;

    protected ParsedResult(ParsedResultType parsedResultType) {
        this.type = parsedResultType;
    }

    public static void maybeAppend(String string2, StringBuilder stringBuilder) {
        if (string2 == null) return;
        if (string2.isEmpty()) return;
        if (stringBuilder.length() > 0) {
            stringBuilder.append('\n');
        }
        stringBuilder.append(string2);
    }

    public static void maybeAppend(String[] arrstring, StringBuilder stringBuilder) {
        if (arrstring == null) return;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            ParsedResult.maybeAppend(arrstring[n2], stringBuilder);
            ++n2;
        }
    }

    public abstract String getDisplayResult();

    public final ParsedResultType getType() {
        return this.type;
    }

    public final String toString() {
        return this.getDisplayResult();
    }
}

