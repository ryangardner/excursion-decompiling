/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.client.utils.Punycode;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class PublicSuffixFilter
implements CookieAttributeHandler {
    private Set<String> exceptions;
    private Set<String> suffixes;
    private final CookieAttributeHandler wrapped;

    public PublicSuffixFilter(CookieAttributeHandler cookieAttributeHandler) {
        this.wrapped = cookieAttributeHandler;
    }

    private boolean isForPublicSuffix(Cookie object) {
        Set<String> set;
        object = set = object.getDomain();
        if (((String)((Object)set)).startsWith(".")) {
            object = ((String)((Object)set)).substring(1);
        }
        object = Punycode.toUnicode((String)object);
        set = this.exceptions;
        if (set != null && set.contains(object)) {
            return false;
        }
        if (this.suffixes == null) {
            return false;
        }
        do {
            int n;
            if (this.suffixes.contains(object)) {
                return true;
            }
            set = object;
            if (((String)object).startsWith("*.")) {
                set = ((String)object).substring(2);
            }
            if ((n = ((String)((Object)set)).indexOf(46)) == -1) {
                return false;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("*");
            ((StringBuilder)object).append(((String)((Object)set)).substring(n));
            object = set = ((StringBuilder)object).toString();
        } while (((String)((Object)set)).length() > 0);
        return false;
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (!this.isForPublicSuffix(cookie)) return this.wrapped.match(cookie, cookieOrigin);
        return false;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        this.wrapped.parse(setCookie, string2);
    }

    public void setExceptions(Collection<String> collection) {
        this.exceptions = new HashSet<String>(collection);
    }

    public void setPublicSuffixes(Collection<String> collection) {
        this.suffixes = new HashSet<String>(collection);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        this.wrapped.validate(cookie, cookieOrigin);
    }
}

