/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

public class RFC2965VersionAttributeHandler
implements CookieAttributeHandler {
    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        return true;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        int n;
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (string2 == null) throw new MalformedCookieException("Missing value for version attribute");
        try {
            n = Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            n = -1;
        }
        if (n < 0) throw new MalformedCookieException("Invalid cookie version.");
        setCookie.setVersion(n);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (!(cookie instanceof SetCookie2)) return;
        if (!(cookie instanceof ClientCookie)) return;
        if (!((ClientCookie)cookie).containsAttribute("version")) throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
    }
}

