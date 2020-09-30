/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeoResultParser
extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override
    public GeoParsedResult parse(Result object) {
        String string2;
        double d;
        double d2;
        double d3;
        Matcher matcher;
        block7 : {
            matcher = GEO_URL_PATTERN.matcher((CharSequence)(object = GeoResultParser.getMassagedText((Result)object)));
            if (!matcher.matches()) {
                return null;
            }
            string2 = matcher.group(4);
            try {
                d3 = Double.parseDouble(matcher.group(1));
                if (d3 > 90.0) return null;
            }
            catch (NumberFormatException numberFormatException) {
                return null;
            }
            if (d3 < -90.0) {
                return null;
            }
            d = Double.parseDouble(matcher.group(2));
            if (d > 180.0) return null;
            if (d < -180.0) {
                return null;
            }
            object = matcher.group(3);
            d2 = 0.0;
            if (object != null) break block7;
            return new GeoParsedResult(d3, d, d2, string2);
        }
        d2 = Double.parseDouble(matcher.group(3));
        if (!(d2 < 0.0)) return new GeoParsedResult(d3, d, d2, string2);
        return null;
    }
}

