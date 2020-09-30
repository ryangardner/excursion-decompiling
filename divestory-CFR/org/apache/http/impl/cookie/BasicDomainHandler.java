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

public class BasicDomainHandler
implements CookieAttributeHandler {
    @Override
    public boolean match(Cookie object, CookieOrigin object2) {
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        String string2 = ((CookieOrigin)object2).getHost();
        object2 = object.getDomain();
        boolean bl = false;
        if (object2 == null) {
            return false;
        }
        if (string2.equals(object2)) {
            return true;
        }
        object = object2;
        if (!((String)object2).startsWith(".")) {
            object = new StringBuilder();
            ((StringBuilder)object).append('.');
            ((StringBuilder)object).append((String)object2);
            object = ((StringBuilder)object).toString();
        }
        if (string2.endsWith((String)object)) return true;
        if (!string2.equals(((String)object).substring(1))) return bl;
        return true;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (string2 == null) throw new MalformedCookieException("Missing value for domain attribute");
        if (string2.trim().length() == 0) throw new MalformedCookieException("Blank value for domain attribute");
        setCookie.setDomain(string2);
    }

    @Override
    public void validate(Cookie object, CookieOrigin object2) throws MalformedCookieException {
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        String string2 = ((CookieOrigin)object2).getHost();
        object2 = object.getDomain();
        if (object2 == null) throw new CookieRestrictionViolationException("Cookie domain may not be null");
        if (string2.contains(".")) {
            if (string2.endsWith((String)object2)) return;
            object = object2;
            if (((String)object2).startsWith(".")) {
                object = ((String)object2).substring(1, ((String)object2).length());
            }
            if (string2.equals(object)) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Illegal domain attribute \"");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("\". Domain of origin: \"");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("\"");
            throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
        }
        if (string2.equals(object2)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal domain attribute \"");
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append("\". Domain of origin: \"");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("\"");
        throw new CookieRestrictionViolationException(((StringBuilder)object).toString());
    }
}

