/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.TelParsedResult;

public final class TelResultParser
extends ResultParser {
    @Override
    public TelParsedResult parse(Result object) {
        String string2 = TelResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("tel:") && !string2.startsWith("TEL:")) {
            return null;
        }
        if (string2.startsWith("TEL:")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("tel:");
            ((StringBuilder)object).append(string2.substring(4));
            object = ((StringBuilder)object).toString();
        } else {
            object = string2;
        }
        int n = string2.indexOf(63, 4);
        if (n < 0) {
            string2 = string2.substring(4);
            return new TelParsedResult(string2, (String)object, null);
        }
        string2 = string2.substring(4, n);
        return new TelParsedResult(string2, (String)object, null);
    }
}

