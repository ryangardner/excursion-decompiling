/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Iterator;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.impl.cookie.RFC2965Spec;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BestMatchSpec
implements CookieSpec {
    private BrowserCompatSpec compat;
    private final String[] datepatterns;
    private RFC2109Spec obsoleteStrict;
    private final boolean oneHeader;
    private RFC2965Spec strict;

    public BestMatchSpec() {
        this(null, false);
    }

    public BestMatchSpec(String[] object, boolean bl) {
        object = object == null ? null : (String[])object.clone();
        this.datepatterns = object;
        this.oneHeader = bl;
    }

    private BrowserCompatSpec getCompat() {
        if (this.compat != null) return this.compat;
        this.compat = new BrowserCompatSpec(this.datepatterns);
        return this.compat;
    }

    private RFC2109Spec getObsoleteStrict() {
        if (this.obsoleteStrict != null) return this.obsoleteStrict;
        this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
        return this.obsoleteStrict;
    }

    private RFC2965Spec getStrict() {
        if (this.strict != null) return this.strict;
        this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
        return this.strict;
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        if (list == null) throw new IllegalArgumentException("List of cookies may not be null");
        int n = Integer.MAX_VALUE;
        boolean bl = true;
        Iterator<Cookie> iterator2 = list.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (n <= 0) return this.getCompat().formatCookies(list);
                if (!bl) return this.getObsoleteStrict().formatCookies(list);
                return this.getStrict().formatCookies(list);
            }
            Cookie cookie = iterator2.next();
            boolean bl2 = bl;
            if (!(cookie instanceof SetCookie2)) {
                bl2 = false;
            }
            bl = bl2;
            if (cookie.getVersion() >= n) continue;
            n = cookie.getVersion();
            bl = bl2;
        } while (true);
    }

    @Override
    public int getVersion() {
        return this.getStrict().getVersion();
    }

    @Override
    public Header getVersionHeader() {
        return this.getStrict().getVersionHeader();
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (cookie.getVersion() <= 0) return this.getCompat().match(cookie, cookieOrigin);
        if (!(cookie instanceof SetCookie2)) return this.getObsoleteStrict().match(cookie, cookieOrigin);
        return this.getStrict().match(cookie, cookieOrigin);
    }

    @Override
    public List<Cookie> parse(Header object, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Object object2;
        if (object == null) throw new IllegalArgumentException("Header may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        HeaderElement[] arrheaderElement = object.getElements();
        int n = arrheaderElement.length;
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < n; ++i) {
            object2 = arrheaderElement[i];
            if (object2.getParameterByName("version") != null) {
                bl2 = true;
            }
            if (object2.getParameterByName("expires") == null) continue;
            bl = true;
        }
        if (!bl && bl2) {
            if (!"Set-Cookie2".equals(object.getName())) return this.getObsoleteStrict().parse(arrheaderElement, cookieOrigin);
            return this.getStrict().parse(arrheaderElement, cookieOrigin);
        }
        arrheaderElement = NetscapeDraftHeaderParser.DEFAULT;
        if (object instanceof FormattedHeader) {
            object = (FormattedHeader)object;
            object2 = object.getBuffer();
            object = new ParserCursor(object.getValuePos(), ((CharArrayBuffer)object2).length());
        } else {
            if ((object = object.getValue()) == null) throw new MalformedCookieException("Header value is null");
            object2 = new CharArrayBuffer(((String)object).length());
            ((CharArrayBuffer)object2).append((String)object);
            object = new ParserCursor(0, ((CharArrayBuffer)object2).length());
        }
        object = arrheaderElement.parseHeader((CharArrayBuffer)object2, (ParserCursor)object);
        return this.getCompat().parse(new HeaderElement[]{object}, cookieOrigin);
    }

    public String toString() {
        return "best-match";
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (cookie.getVersion() <= 0) {
            this.getCompat().validate(cookie, cookieOrigin);
            return;
        }
        if (cookie instanceof SetCookie2) {
            this.getStrict().validate(cookie, cookieOrigin);
            return;
        }
        this.getObsoleteStrict().validate(cookie, cookieOrigin);
    }
}

