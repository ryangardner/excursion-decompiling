/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public interface CookieAttributeHandler {
    public boolean match(Cookie var1, CookieOrigin var2);

    public void parse(SetCookie var1, String var2) throws MalformedCookieException;

    public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException;
}

