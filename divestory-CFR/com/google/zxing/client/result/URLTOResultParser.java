/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;

public final class URLTOResultParser
extends ResultParser {
    @Override
    public URIParsedResult parse(Result object) {
        String string2 = URLTOResultParser.getMassagedText((Result)object);
        boolean bl = string2.startsWith("urlto:");
        object = null;
        if (!bl && !string2.startsWith("URLTO:")) {
            return null;
        }
        int n = string2.indexOf(58, 6);
        if (n < 0) {
            return null;
        }
        if (n <= 6) {
            return new URIParsedResult(string2.substring(n + 1), (String)object);
        }
        object = string2.substring(6, n);
        return new URIParsedResult(string2.substring(n + 1), (String)object);
    }
}

