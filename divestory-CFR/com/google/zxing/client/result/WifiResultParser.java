/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.WifiParsedResult;

public final class WifiResultParser
extends ResultParser {
    @Override
    public WifiParsedResult parse(Result object) {
        String string2 = WifiResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("WIFI:")) {
            return null;
        }
        String string3 = WifiResultParser.matchSinglePrefixedField("S:", string2, ';', false);
        if (string3 == null) return null;
        if (string3.isEmpty()) {
            return null;
        }
        String string4 = WifiResultParser.matchSinglePrefixedField("P:", string2, ';', false);
        String string5 = WifiResultParser.matchSinglePrefixedField("T:", string2, ';', false);
        object = string5;
        if (string5 != null) return new WifiParsedResult((String)object, string3, string4, Boolean.parseBoolean(WifiResultParser.matchSinglePrefixedField("H:", string2, ';', false)));
        object = "nopass";
        return new WifiParsedResult((String)object, string3, string4, Boolean.parseBoolean(WifiResultParser.matchSinglePrefixedField("H:", string2, ';', false)));
    }
}

