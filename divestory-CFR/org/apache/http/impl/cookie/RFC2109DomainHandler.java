/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class RFC2109DomainHandler
implements CookieAttributeHandler {
    @Override
    public boolean match(Cookie object, CookieOrigin object2) {
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        object2 = ((CookieOrigin)object2).getHost();
        object = object.getDomain();
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (((String)object2).equals(object)) return true;
        boolean bl2 = bl;
        if (!((String)object).startsWith(".")) return bl2;
        bl2 = bl;
        if (!((String)object2).endsWith((String)object)) return bl2;
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
        object2 = ((CookieOrigin)object2).getHost();
        if ((object = object.getDomain()) == null) throw new CookieRestrictionViolationException("Cookie domain may not be null");
        if (((String)object).equals(object2)) return;
        if (((String)object).indexOf(46) == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Domain attribute \"");
            stringBuilder.append((String)object);
            stringBuilder.append("\" does not match the host \"");
            stringBuilder.append((String)object2);
            stringBuilder.append("\"");
            throw new CookieRestrictionViolationException(stringBuilder.toString());
        }
        if (!((String)object).startsWith(".")) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Domain attribute \"");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("\" violates RFC 2109: domain must start with a dot");
            throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
        }
        int n = ((String)object).indexOf(46, 1);
        if (n >= 0 && n != ((String)object).length() - 1) {
            if (!((String)(object2 = ((String)object2).toLowerCase(Locale.ENGLISH))).endsWith((String)object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal domain attribute \"");
                stringBuilder.append((String)object);
                stringBuilder.append("\". Domain of origin: \"");
                stringBuilder.append((String)object2);
                stringBuilder.append("\"");
                throw new CookieRestrictionViolationException(stringBuilder.toString());
            }
            if (((String)object2).substring(0, ((String)object2).length() - ((String)object).length()).indexOf(46) == -1) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Domain attribute \"");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("\" violates RFC 2109: host minus domain may not contain any dots");
            throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Domain attribute \"");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("\" violates RFC 2109: domain must contain an embedded dot");
        throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
    }
}

