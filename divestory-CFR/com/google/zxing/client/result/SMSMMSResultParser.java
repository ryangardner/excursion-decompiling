/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import java.util.ArrayList;
import java.util.Collection;

public final class SMSMMSResultParser
extends ResultParser {
    private static void addNumberVia(Collection<String> object, Collection<String> collection, String string2) {
        int n = string2.indexOf(59);
        Object var4_4 = null;
        if (n < 0) {
            object.add((String)string2);
            collection.add(null);
            return;
        }
        object.add((String)string2.substring(0, n));
        string2 = string2.substring(n + 1);
        object = var4_4;
        if (string2.startsWith("via=")) {
            object = string2.substring(4);
        }
        collection.add((String)object);
    }

    @Override
    public SMSParsedResult parse(Result object) {
        String string2 = SMSMMSResultParser.getMassagedText((Result)object);
        boolean bl = string2.startsWith("sms:");
        String string3 = null;
        if (!(bl || string2.startsWith("SMS:") || string2.startsWith("mms:") || string2.startsWith("MMS:"))) {
            return null;
        }
        object = SMSMMSResultParser.parseNameValuePairs(string2);
        int n = 0;
        if (object != null && !object.isEmpty()) {
            string3 = (String)object.get("subject");
            object = (String)object.get("body");
            n = 1;
        } else {
            object = null;
        }
        int n2 = string2.indexOf(63, 4);
        string2 = n2 >= 0 && n != 0 ? string2.substring(4, n2) : string2.substring(4);
        n = -1;
        ArrayList<String> arrayList = new ArrayList<String>(1);
        ArrayList<String> arrayList2 = new ArrayList<String>(1);
        do {
            int n3;
            if ((n2 = string2.indexOf(44, n3 = n + 1)) <= n) {
                SMSMMSResultParser.addNumberVia(arrayList, arrayList2, string2.substring(n3));
                return new SMSParsedResult(arrayList.toArray(new String[arrayList.size()]), arrayList2.toArray(new String[arrayList2.size()]), string3, (String)object);
            }
            SMSMMSResultParser.addNumberVia(arrayList, arrayList2, string2.substring(n3, n2));
            n = n2;
        } while (true);
    }
}

