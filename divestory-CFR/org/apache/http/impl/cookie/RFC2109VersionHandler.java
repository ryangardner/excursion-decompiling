/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;

public class RFC2109VersionHandler
extends AbstractCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String charSequence) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (charSequence == null) throw new MalformedCookieException("Missing value for version attribute");
        if (((String)charSequence).trim().length() == 0) throw new MalformedCookieException("Blank value for version attribute");
        try {
            setCookie.setVersion(Integer.parseInt((String)charSequence));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid version: ");
            ((StringBuilder)charSequence).append(numberFormatException.getMessage());
            throw new MalformedCookieException(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookie.getVersion() < 0) throw new CookieRestrictionViolationException("Cookie version may not be negative");
    }
}

