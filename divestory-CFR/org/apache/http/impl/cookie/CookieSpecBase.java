/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieSpec;
import org.apache.http.impl.cookie.BasicClientCookie;

public abstract class CookieSpecBase
extends AbstractCookieSpec {
    protected static String getDefaultDomain(CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }

    protected static String getDefaultPath(CookieOrigin object) {
        String string2 = ((CookieOrigin)object).getPath();
        int n = string2.lastIndexOf(47);
        object = string2;
        if (n < 0) return object;
        int n2 = n;
        if (n != 0) return string2.substring(0, n2);
        n2 = 1;
        return string2.substring(0, n2);
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        Iterator<CookieAttributeHandler> iterator2 = this.getAttribHandlers().iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (iterator2.next().match(cookie, cookieOrigin));
        return false;
    }

    protected List<Cookie> parse(HeaderElement[] arrheaderElement, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ArrayList<Cookie> arrayList = new ArrayList<Cookie>(arrheaderElement.length);
        int n = arrheaderElement.length;
        int n2 = 0;
        while (n2 < n) {
            NameValuePair[] arrnameValuePair = arrheaderElement[n2];
            Object object = arrnameValuePair.getName();
            Object object2 = arrnameValuePair.getValue();
            if (object == null) throw new MalformedCookieException("Cookie name may not be empty");
            if (((String)object).length() == 0) throw new MalformedCookieException("Cookie name may not be empty");
            object2 = new BasicClientCookie((String)object, (String)object2);
            ((BasicClientCookie)object2).setPath(CookieSpecBase.getDefaultPath(cookieOrigin));
            ((BasicClientCookie)object2).setDomain(CookieSpecBase.getDefaultDomain(cookieOrigin));
            arrnameValuePair = arrnameValuePair.getParameters();
            for (int i = arrnameValuePair.length - 1; i >= 0; --i) {
                object = arrnameValuePair[i];
                Object object3 = object.getName().toLowerCase(Locale.ENGLISH);
                ((BasicClientCookie)object2).setAttribute((String)object3, object.getValue());
                object3 = this.findAttribHandler((String)object3);
                if (object3 == null) continue;
                object3.parse((SetCookie)object2, object.getValue());
            }
            arrayList.add((Cookie)object2);
            ++n2;
        }
        return arrayList;
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        Iterator<CookieAttributeHandler> iterator2 = this.getAttribHandlers().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().validate(cookie, cookieOrigin);
        }
    }
}

