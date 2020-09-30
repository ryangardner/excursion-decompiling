/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;

public final class SMSTOMMSTOResultParser
extends ResultParser {
    @Override
    public SMSParsedResult parse(Result object) {
        String string2;
        if (!(((String)(object = SMSTOMMSTOResultParser.getMassagedText((Result)object))).startsWith("smsto:") || ((String)object).startsWith("SMSTO:") || ((String)object).startsWith("mmsto:") || ((String)object).startsWith("MMSTO:"))) {
            return null;
        }
        int n = ((String)(object = ((String)object).substring(6))).indexOf(58);
        if (n >= 0) {
            string2 = ((String)object).substring(n + 1);
            object = ((String)object).substring(0, n);
            return new SMSParsedResult((String)object, null, null, string2);
        }
        string2 = null;
        return new SMSParsedResult((String)object, null, null, string2);
    }
}

