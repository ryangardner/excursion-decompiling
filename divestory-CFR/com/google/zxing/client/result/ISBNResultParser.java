/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class ISBNResultParser
extends ResultParser {
    @Override
    public ISBNParsedResult parse(Result object) {
        if (((Result)object).getBarcodeFormat() != BarcodeFormat.EAN_13) {
            return null;
        }
        if (((String)(object = ISBNResultParser.getMassagedText((Result)object))).length() != 13) {
            return null;
        }
        if (((String)object).startsWith("978")) return new ISBNParsedResult((String)object);
        if (((String)object).startsWith("979")) return new ISBNParsedResult((String)object);
        return null;
    }
}

