/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;

public class BasicSecureHandler
extends AbstractCookieAttributeHandler {
    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (!cookie.isSecure()) return true;
        if (cookieOrigin.isSecure()) return true;
        return false;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        setCookie.setSecure(true);
    }
}

