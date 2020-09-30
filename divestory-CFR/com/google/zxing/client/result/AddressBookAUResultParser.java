/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.ArrayList;

public final class AddressBookAUResultParser
extends ResultParser {
    private static String[] matchMultipleValuePrefix(String string2, int n, String string3, boolean bl) {
        ArrayList<String> arrayList = null;
        for (int i = 1; i <= n; ++i) {
            ArrayList<String> arrayList2 = new StringBuilder();
            ((StringBuilder)((Object)arrayList2)).append(string2);
            ((StringBuilder)((Object)arrayList2)).append(i);
            ((StringBuilder)((Object)arrayList2)).append(':');
            String string4 = AddressBookAUResultParser.matchSinglePrefixedField(((StringBuilder)((Object)arrayList2)).toString(), string3, '\r', bl);
            if (string4 == null) break;
            arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<String>(n);
            }
            arrayList2.add(string4);
            arrayList = arrayList2;
        }
        if (arrayList != null) return arrayList.toArray(new String[arrayList.size()]);
        return null;
    }

    @Override
    public AddressBookParsedResult parse(Result arrstring) {
        String string2 = AddressBookAUResultParser.getMassagedText((Result)arrstring);
        boolean bl = string2.contains("MEMORY");
        arrstring = null;
        if (!bl) return null;
        if (!string2.contains("\r\n")) {
            return null;
        }
        String string3 = AddressBookAUResultParser.matchSinglePrefixedField("NAME1:", string2, '\r', true);
        String string4 = AddressBookAUResultParser.matchSinglePrefixedField("NAME2:", string2, '\r', true);
        String[] arrstring2 = AddressBookAUResultParser.matchMultipleValuePrefix("TEL", 3, string2, true);
        String[] arrstring3 = AddressBookAUResultParser.matchMultipleValuePrefix("MAIL", 3, string2, true);
        String string5 = AddressBookAUResultParser.matchSinglePrefixedField("MEMORY:", string2, '\r', false);
        if ((string2 = AddressBookAUResultParser.matchSinglePrefixedField("ADD:", string2, '\r', true)) == null) {
            return new AddressBookParsedResult(AddressBookAUResultParser.maybeWrap(string3), null, string4, arrstring2, null, arrstring3, null, null, string5, arrstring, null, null, null, null, null, null);
        }
        arrstring = new String[]{string2};
        return new AddressBookParsedResult(AddressBookAUResultParser.maybeWrap(string3), null, string4, arrstring2, null, arrstring3, null, null, string5, arrstring, null, null, null, null, null, null);
    }
}

