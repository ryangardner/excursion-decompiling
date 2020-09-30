/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    static boolean isBasicallyValidEmailAddress(String string2) {
        if (string2 == null) return false;
        if (!ATEXT_ALPHANUMERIC.matcher(string2).matches()) return false;
        if (string2.indexOf(64) < 0) return false;
        return true;
    }

    @Override
    public EmailAddressParsedResult parse(Result arrstring) {
        String string2 = EmailDoCoMoResultParser.getMassagedText((Result)arrstring);
        if (!string2.startsWith("MATMSG:")) {
            return null;
        }
        arrstring = EmailDoCoMoResultParser.matchDoCoMoPrefixedField("TO:", string2, true);
        if (arrstring == null) {
            return null;
        }
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress(arrstring[n2])) {
                return null;
            }
            ++n2;
        }
        return new EmailAddressParsedResult(arrstring, null, null, EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SUB:", string2, false), EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BODY:", string2, false));
    }
}

