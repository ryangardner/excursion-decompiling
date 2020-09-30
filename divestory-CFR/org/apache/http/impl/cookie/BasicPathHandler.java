/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicPathHandler
implements CookieAttributeHandler {
    @Override
    public boolean match(Cookie object, CookieOrigin object2) {
        boolean bl;
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        String string2 = ((CookieOrigin)object2).getPath();
        object = object2 = object.getPath();
        if (object2 == null) {
            object = "/";
        }
        int n = ((String)object).length();
        boolean bl2 = false;
        object2 = object;
        if (n > 1) {
            object2 = object;
            if (((String)object).endsWith("/")) {
                object2 = ((String)object).substring(0, ((String)object).length() - 1);
            }
        }
        boolean bl3 = bl = string2.startsWith((String)object2);
        if (!bl) return bl3;
        bl3 = bl;
        if (string2.length() == ((String)object2).length()) return bl3;
        bl3 = bl;
        if (((String)object2).endsWith("/")) return bl3;
        bl3 = bl2;
        if (string2.charAt(((String)object2).length()) != '/') return bl3;
        return true;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        String string3;
        block3 : {
            block2 : {
                if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
                if (string2 == null) break block2;
                string3 = string2;
                if (string2.trim().length() != 0) break block3;
            }
            string3 = "/";
        }
        setCookie.setPath(string3);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (this.match(cookie, cookieOrigin)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal path attribute \"");
        stringBuilder.append(cookie.getPath());
        stringBuilder.append("\". Path of origin: \"");
        stringBuilder.append(cookieOrigin.getPath());
        stringBuilder.append("\"");
        throw new CookieRestrictionViolationException(stringBuilder.toString());
    }
}

