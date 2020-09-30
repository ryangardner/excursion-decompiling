/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import java.util.ArrayList;

public final class BizcardResultParser
extends AbstractDoCoMoResultParser {
    private static String buildName(String string2, String string3) {
        if (string2 == null) {
            return string3;
        }
        if (string3 == null) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(' ');
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    private static String[] buildPhoneNumbers(String string2, String string3, String string4) {
        int n;
        ArrayList<String> arrayList = new ArrayList<String>(3);
        if (string2 != null) {
            arrayList.add(string2);
        }
        if (string3 != null) {
            arrayList.add(string3);
        }
        if (string4 != null) {
            arrayList.add(string4);
        }
        if ((n = arrayList.size()) != 0) return arrayList.toArray(new String[n]);
        return null;
    }

    @Override
    public AddressBookParsedResult parse(Result arrstring) {
        String string2 = BizcardResultParser.getMassagedText((Result)arrstring);
        if (!string2.startsWith("BIZCARD:")) {
            return null;
        }
        String string3 = BizcardResultParser.buildName(BizcardResultParser.matchSingleDoCoMoPrefixedField("N:", string2, true), BizcardResultParser.matchSingleDoCoMoPrefixedField("X:", string2, true));
        String string4 = BizcardResultParser.matchSingleDoCoMoPrefixedField("T:", string2, true);
        String string5 = BizcardResultParser.matchSingleDoCoMoPrefixedField("C:", string2, true);
        arrstring = BizcardResultParser.matchDoCoMoPrefixedField("A:", string2, true);
        String string6 = BizcardResultParser.matchSingleDoCoMoPrefixedField("B:", string2, true);
        String string7 = BizcardResultParser.matchSingleDoCoMoPrefixedField("M:", string2, true);
        String string8 = BizcardResultParser.matchSingleDoCoMoPrefixedField("F:", string2, true);
        string2 = BizcardResultParser.matchSingleDoCoMoPrefixedField("E:", string2, true);
        return new AddressBookParsedResult(BizcardResultParser.maybeWrap(string3), null, null, BizcardResultParser.buildPhoneNumbers(string6, string7, string8), null, BizcardResultParser.maybeWrap(string2), null, null, null, arrstring, null, string5, null, string4, null, null);
    }
}

