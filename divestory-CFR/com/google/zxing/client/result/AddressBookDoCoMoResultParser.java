/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;

public final class AddressBookDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static String parseName(String string2) {
        int n = string2.indexOf(44);
        CharSequence charSequence = string2;
        if (n < 0) return charSequence;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2.substring(n + 1));
        ((StringBuilder)charSequence).append(' ');
        ((StringBuilder)charSequence).append(string2.substring(0, n));
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public AddressBookParsedResult parse(Result object) {
        String string2 = AddressBookDoCoMoResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("MECARD:")) {
            return null;
        }
        object = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("N:", string2, true);
        if (object == null) {
            return null;
        }
        String string3 = AddressBookDoCoMoResultParser.parseName(object[0]);
        String string4 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SOUND:", string2, true);
        String[] arrstring = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("TEL:", string2, true);
        String[] arrstring2 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("EMAIL:", string2, true);
        String string5 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("NOTE:", string2, false);
        String[] arrstring3 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("ADR:", string2, true);
        object = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BDAY:", string2, true);
        if (!AddressBookDoCoMoResultParser.isStringOfDigits((CharSequence)object, 8)) {
            object = null;
        }
        String[] arrstring4 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", string2, true);
        string2 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("ORG:", string2, true);
        return new AddressBookParsedResult(AddressBookDoCoMoResultParser.maybeWrap(string3), null, string4, arrstring, null, arrstring2, null, null, string5, arrstring3, null, string2, (String)object, null, arrstring4, null);
    }
}

