/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class SMTPResultParser
extends ResultParser {
    @Override
    public EmailAddressParsedResult parse(Result object) {
        Object object2;
        if (!((String)(object = SMTPResultParser.getMassagedText((Result)object))).startsWith("smtp:") && !((String)object).startsWith("SMTP:")) {
            return null;
        }
        String string2 = ((String)object).substring(5);
        int n = string2.indexOf(58);
        if (n >= 0) {
            object = string2.substring(n + 1);
            string2 = string2.substring(0, n);
            n = ((String)object).indexOf(58);
            if (n >= 0) {
                object2 = ((String)object).substring(n + 1);
                object = ((String)object).substring(0, n);
                return new EmailAddressParsedResult(new String[]{string2}, null, null, (String)object, (String)object2);
            } else {
                object2 = null;
            }
            return new EmailAddressParsedResult(new String[]{string2}, null, null, (String)object, (String)object2);
        } else {
            object2 = object = null;
        }
        return new EmailAddressParsedResult(new String[]{string2}, null, null, (String)object, (String)object2);
    }
}

