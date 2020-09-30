/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicDomainHandler;

public class NetscapeDomainHandler
extends BasicDomainHandler {
    private static boolean isSpecialDomain(String string2) {
        if ((string2 = string2.toUpperCase(Locale.ENGLISH)).endsWith(".COM")) return true;
        if (string2.endsWith(".EDU")) return true;
        if (string2.endsWith(".NET")) return true;
        if (string2.endsWith(".GOV")) return true;
        if (string2.endsWith(".MIL")) return true;
        if (string2.endsWith(".ORG")) return true;
        if (string2.endsWith(".INT")) return true;
        return false;
    }

    @Override
    public boolean match(Cookie object, CookieOrigin object2) {
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        object2 = ((CookieOrigin)object2).getHost();
        if ((object = object.getDomain()) != null) return ((String)object2).endsWith((String)object);
        return false;
    }

    @Override
    public void validate(Cookie object, CookieOrigin object2) throws MalformedCookieException {
        super.validate((Cookie)object, (CookieOrigin)object2);
        object2 = ((CookieOrigin)object2).getHost();
        object = object.getDomain();
        if (!((String)object2).contains(".")) return;
        int n = new StringTokenizer((String)object, ".").countTokens();
        if (NetscapeDomainHandler.isSpecialDomain((String)object)) {
            if (n >= 2) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Domain attribute \"");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("\" violates the Netscape cookie specification for ");
            ((StringBuilder)object2).append("special domains");
            throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
        }
        if (n >= 3) {
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Domain attribute \"");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("\" violates the Netscape cookie specification");
        throw new CookieRestrictionViolationException(((StringBuilder)object2).toString());
    }
}

