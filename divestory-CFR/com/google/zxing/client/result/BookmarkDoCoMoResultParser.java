/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.google.zxing.client.result.URIResultParser;

public final class BookmarkDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    @Override
    public URIParsedResult parse(Result object) {
        Object object2 = ((Result)object).getText();
        boolean bl = object2.startsWith("MEBKM:");
        object = null;
        if (!bl) {
            return null;
        }
        String string2 = BookmarkDoCoMoResultParser.matchSingleDoCoMoPrefixedField("TITLE:", (String)object2, true);
        if ((object2 = BookmarkDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", (String)object2, true)) == null) {
            return null;
        }
        if (!URIResultParser.isBasicallyValidURI((String)(object2 = object2[0]))) return object;
        return new URIParsedResult((String)object2, string2);
    }
}

