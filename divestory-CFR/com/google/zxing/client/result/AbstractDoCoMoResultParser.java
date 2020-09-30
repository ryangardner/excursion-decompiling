/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ResultParser;

abstract class AbstractDoCoMoResultParser
extends ResultParser {
    AbstractDoCoMoResultParser() {
    }

    static String[] matchDoCoMoPrefixedField(String string2, String string3, boolean bl) {
        return AbstractDoCoMoResultParser.matchPrefixedField(string2, string3, ';', bl);
    }

    static String matchSingleDoCoMoPrefixedField(String string2, String string3, boolean bl) {
        return AbstractDoCoMoResultParser.matchSinglePrefixedField(string2, string3, ';', bl);
    }
}

