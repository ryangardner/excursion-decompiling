/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

public interface CookieSpec {
    public List<Header> formatCookies(List<Cookie> var1);

    public int getVersion();

    public Header getVersionHeader();

    public boolean match(Cookie var1, CookieOrigin var2);

    public List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException;

    public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException;
}

