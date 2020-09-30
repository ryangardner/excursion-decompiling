/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class RFC2965DomainAttributeHandler
implements CookieAttributeHandler {
    public boolean domainMatch(String string2, String string3) {
        if (string2.equals(string3)) return true;
        if (!string3.startsWith(".")) return false;
        if (!string2.endsWith(string3)) return false;
        return true;
    }

    @Override
    public boolean match(Cookie object, CookieOrigin object2) {
        if (object == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        object2 = ((CookieOrigin)object2).getHost().toLowerCase(Locale.ENGLISH);
        object = object.getDomain();
        boolean bl = this.domainMatch((String)object2, (String)object);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (((String)object2).substring(0, ((String)object2).length() - ((String)object).length()).indexOf(46) != -1) return bl2;
        return true;
    }

    @Override
    public void parse(SetCookie setCookie, String charSequence) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (charSequence == null) throw new MalformedCookieException("Missing value for domain attribute");
        if (((String)charSequence).trim().length() == 0) throw new MalformedCookieException("Blank value for domain attribute");
        String string2 = ((String)charSequence).toLowerCase(Locale.ENGLISH);
        charSequence = string2;
        if (!string2.startsWith(".")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append('.');
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        setCookie.setDomain((String)charSequence);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin object) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (object == null) throw new IllegalArgumentException("Cookie origin may not be null");
        object = ((CookieOrigin)object).getHost().toLowerCase(Locale.ENGLISH);
        if (cookie.getDomain() == null) throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
        CharSequence charSequence = cookie.getDomain().toLowerCase(Locale.ENGLISH);
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("domain")) {
            if (!((String)charSequence).startsWith(".")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Domain attribute \"");
                ((StringBuilder)object).append(cookie.getDomain());
                ((StringBuilder)object).append("\" violates RFC 2109: domain must start with a dot");
                throw new CookieRestrictionViolationException(((StringBuilder)object).toString());
            }
            int n = ((String)charSequence).indexOf(46, 1);
            if (!(n >= 0 && n != ((String)charSequence).length() - 1 || ((String)charSequence).equals(".local"))) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Domain attribute \"");
                ((StringBuilder)object).append(cookie.getDomain());
                ((StringBuilder)object).append("\" violates RFC 2965: the value contains no embedded dots ");
                ((StringBuilder)object).append("and the value is not .local");
                throw new CookieRestrictionViolationException(((StringBuilder)object).toString());
            }
            if (!this.domainMatch((String)object, (String)charSequence)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Domain attribute \"");
                ((StringBuilder)object).append(cookie.getDomain());
                ((StringBuilder)object).append("\" violates RFC 2965: effective host name does not ");
                ((StringBuilder)object).append("domain-match domain attribute.");
                throw new CookieRestrictionViolationException(((StringBuilder)object).toString());
            }
            if (((String)object).substring(0, ((String)object).length() - ((String)charSequence).length()).indexOf(46) == -1) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Domain attribute \"");
            ((StringBuilder)object).append(cookie.getDomain());
            ((StringBuilder)object).append("\" violates RFC 2965: ");
            ((StringBuilder)object).append("effective host minus domain may not contain any dots");
            throw new CookieRestrictionViolationException(((StringBuilder)object).toString());
        }
        if (cookie.getDomain().equals(object)) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Illegal domain attribute: \"");
        ((StringBuilder)charSequence).append(cookie.getDomain());
        ((StringBuilder)charSequence).append("\".");
        ((StringBuilder)charSequence).append("Domain of origin: \"");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("\"");
        throw new CookieRestrictionViolationException(((StringBuilder)charSequence).toString());
    }
}

