/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class BasicExpiresHandler
extends AbstractCookieAttributeHandler {
    private final String[] datepatterns;

    public BasicExpiresHandler(String[] arrstring) {
        if (arrstring == null) throw new IllegalArgumentException("Array of date patterns may not be null");
        this.datepatterns = arrstring;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (string2 == null) throw new MalformedCookieException("Missing value for expires attribute");
        try {
            setCookie.setExpiryDate(DateUtils.parseDate(string2, this.datepatterns));
            return;
        }
        catch (DateParseException dateParseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to parse expires attribute: ");
            stringBuilder.append(string2);
            throw new MalformedCookieException(stringBuilder.toString());
        }
    }
}

