/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.StringTokenizer;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

public class RFC2965PortAttributeHandler
implements CookieAttributeHandler {
    private static int[] parsePortAttribute(String object) throws MalformedCookieException {
        Object object2 = new StringTokenizer((String)object, ",");
        object = new int[((StringTokenizer)object2).countTokens()];
        int n = 0;
        do {
            if (!((StringTokenizer)object2).hasMoreTokens()) return object;
            object[n] = Integer.parseInt(((StringTokenizer)object2).nextToken().trim());
            if (object[n] < 0) break;
            ++n;
        } while (true);
        try {
            object = new MalformedCookieException("Invalid Port attribute.");
            throw object;
        }
        catch (NumberFormatException numberFormatException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Invalid Port attribute: ");
            ((StringBuilder)object2).append(numberFormatException.getMessage());
            throw new MalformedCookieException(((StringBuilder)object2).toString());
        }
    }

    private static boolean portMatch(int n, int[] arrn) {
        int n2 = arrn.length;
        boolean bl = false;
        int n3 = 0;
        do {
            boolean bl2 = bl;
            if (n3 >= n2) return bl2;
            if (n == arrn[n3]) {
                return true;
            }
            ++n3;
        } while (true);
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        int n = cookieOrigin.getPort();
        if (!(cookie instanceof ClientCookie)) return true;
        if (!((ClientCookie)cookie).containsAttribute("port")) return true;
        if (cookie.getPorts() == null) {
            return false;
        }
        if (RFC2965PortAttributeHandler.portMatch(n, cookie.getPorts())) return true;
        return false;
    }

    @Override
    public void parse(SetCookie setCookie, String string2) throws MalformedCookieException {
        if (setCookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (!(setCookie instanceof SetCookie2)) return;
        setCookie = (SetCookie2)setCookie;
        if (string2 == null) return;
        if (string2.trim().length() <= 0) return;
        setCookie.setPorts(RFC2965PortAttributeHandler.parsePortAttribute(string2));
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        int n = cookieOrigin.getPort();
        if (!(cookie instanceof ClientCookie)) return;
        if (!((ClientCookie)cookie).containsAttribute("port")) return;
        if (!RFC2965PortAttributeHandler.portMatch(n, cookie.getPorts())) throw new CookieRestrictionViolationException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
    }
}

