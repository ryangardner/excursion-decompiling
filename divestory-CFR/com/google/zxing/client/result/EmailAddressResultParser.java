/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Pattern;

public final class EmailAddressResultParser
extends ResultParser {
    private static final Pattern COMMA = Pattern.compile(",");

    @Override
    public EmailAddressParsedResult parse(Result object) {
        Object object2;
        String[] arrstring;
        Object object3 = EmailAddressResultParser.getMassagedText((Result)object);
        boolean bl = object3.startsWith("mailto:");
        String[] arrstring2 = null;
        if (!bl && !object3.startsWith("MAILTO:")) {
            if (EmailDoCoMoResultParser.isBasicallyValidEmailAddress((String)object3)) return new EmailAddressParsedResult((String)object3);
            return null;
        }
        String[] arrstring3 = object3.substring(7);
        int n = arrstring3.indexOf(63);
        object = arrstring3;
        if (n >= 0) {
            object = arrstring3.substring(0, n);
        }
        object = !(object = EmailAddressResultParser.urlDecode((String)object)).isEmpty() ? COMMA.split((CharSequence)object) : null;
        if ((object3 = EmailAddressResultParser.parseNameValuePairs((String)object3)) == null) {
            arrstring2 = object;
            object = object2 = null;
            object3 = arrstring3 = object;
            arrstring = arrstring2;
            return new EmailAddressParsedResult(arrstring, (String[])object2, (String[])object, (String)arrstring3, (String)object3);
        }
        arrstring3 = object;
        if (object == null) {
            object2 = (String[])object3.get("to");
            arrstring3 = object;
            if (object2 != null) {
                arrstring3 = COMMA.split((CharSequence)object2);
            }
        }
        object = (object = (String)object3.get("cc")) != null ? COMMA.split((CharSequence)object) : null;
        object2 = (String)object3.get("bcc");
        if (object2 != null) {
            arrstring2 = COMMA.split((CharSequence)object2);
        }
        object2 = (String)object3.get("subject");
        object3 = (String)object3.get("body");
        arrstring = arrstring3;
        arrstring3 = object2;
        object2 = object;
        object = arrstring2;
        return new EmailAddressParsedResult(arrstring, (String[])object2, (String[])object, (String)arrstring3, (String)object3);
    }
}

