/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VCardResultParser;
import java.util.List;

public final class VEventResultParser
extends ResultParser {
    private static String matchSingleVCardPrefixedField(CharSequence object, String string2, boolean bl) {
        if ((object = VCardResultParser.matchSingleVCardPrefixedField((CharSequence)object, string2, bl, false)) == null) return null;
        if (object.isEmpty()) return null;
        return (String)object.get(0);
    }

    private static String[] matchVCardPrefixedField(CharSequence arrstring, String object, boolean bl) {
        if ((object = VCardResultParser.matchVCardPrefixedField((CharSequence)arrstring, (String)object, bl, false)) == null) return null;
        if (object.isEmpty()) {
            return null;
        }
        int n = object.size();
        arrstring = new String[n];
        int n2 = 0;
        while (n2 < n) {
            arrstring[n2] = (String)((List)object.get(n2)).get(0);
            ++n2;
        }
        return arrstring;
    }

    private static String stripMailto(String string2) {
        String string3 = string2;
        if (string2 == null) return string3;
        if (string2.startsWith("mailto:")) return string2.substring(7);
        string3 = string2;
        if (!string2.startsWith("MAILTO:")) return string3;
        return string2.substring(7);
    }

    @Override
    public CalendarParsedResult parse(Result object) {
        double d;
        int n;
        String string2 = VEventResultParser.getMassagedText((Result)object);
        if (string2.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String string3 = VEventResultParser.matchSingleVCardPrefixedField("SUMMARY", string2, true);
        String string4 = VEventResultParser.matchSingleVCardPrefixedField("DTSTART", string2, true);
        if (string4 == null) {
            return null;
        }
        String string5 = VEventResultParser.matchSingleVCardPrefixedField("DTEND", string2, true);
        String string6 = VEventResultParser.matchSingleVCardPrefixedField("DURATION", string2, true);
        String string7 = VEventResultParser.matchSingleVCardPrefixedField("LOCATION", string2, true);
        object = VEventResultParser.stripMailto(VEventResultParser.matchSingleVCardPrefixedField("ORGANIZER", string2, true));
        String[] arrstring = VEventResultParser.matchVCardPrefixedField("ATTENDEE", string2, true);
        if (arrstring != null) {
            for (n = 0; n < arrstring.length; ++n) {
                arrstring[n] = VEventResultParser.stripMailto(arrstring[n]);
            }
        }
        String string8 = VEventResultParser.matchSingleVCardPrefixedField("DESCRIPTION", string2, true);
        string2 = VEventResultParser.matchSingleVCardPrefixedField("GEO", string2, true);
        double d2 = Double.NaN;
        if (string2 == null) {
            d = Double.NaN;
        } else {
            n = string2.indexOf(59);
            if (n < 0) {
                return null;
            }
            d2 = Double.parseDouble(string2.substring(0, n));
            d = Double.parseDouble(string2.substring(n + 1));
        }
        try {
            return new CalendarParsedResult(string3, string4, string5, string6, string7, (String)object, arrstring, string8, d2, d);
        }
        catch (IllegalArgumentException | NumberFormatException illegalArgumentException) {
            return null;
        }
    }
}

