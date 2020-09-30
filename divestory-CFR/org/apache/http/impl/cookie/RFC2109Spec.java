/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookiePathComparator;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.RFC2109DomainHandler;
import org.apache.http.impl.cookie.RFC2109VersionHandler;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class RFC2109Spec
extends CookieSpecBase {
    private static final String[] DATE_PATTERNS;
    private static final CookiePathComparator PATH_COMPARATOR;
    private final String[] datepatterns;
    private final boolean oneHeader;

    static {
        PATH_COMPARATOR = new CookiePathComparator();
        DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    }

    public RFC2109Spec() {
        this(null, false);
    }

    public RFC2109Spec(String[] arrstring, boolean bl) {
        this.datepatterns = arrstring != null ? (String[])arrstring.clone() : DATE_PATTERNS;
        this.oneHeader = bl;
        this.registerAttribHandler("version", new RFC2109VersionHandler());
        this.registerAttribHandler("path", new BasicPathHandler());
        this.registerAttribHandler("domain", new RFC2109DomainHandler());
        this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
        this.registerAttribHandler("secure", new BasicSecureHandler());
        this.registerAttribHandler("comment", new BasicCommentHandler());
        this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
    }

    private List<Header> doFormatManyHeaders(List<Cookie> object) {
        ArrayList<Header> arrayList = new ArrayList<Header>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Cookie cookie = (Cookie)object.next();
            int n = cookie.getVersion();
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
            charArrayBuffer.append("Cookie: ");
            charArrayBuffer.append("$Version=");
            charArrayBuffer.append(Integer.toString(n));
            charArrayBuffer.append("; ");
            this.formatCookieAsVer(charArrayBuffer, cookie, n);
            arrayList.add(new BufferedHeader(charArrayBuffer));
        }
        return arrayList;
    }

    private List<Header> doFormatOneHeader(List<Cookie> arrayList) {
        Cookie cookie;
        Object object = arrayList.iterator();
        int n = Integer.MAX_VALUE;
        while (object.hasNext()) {
            cookie = object.next();
            if (cookie.getVersion() >= n) continue;
            n = cookie.getVersion();
        }
        object = new CharArrayBuffer(arrayList.size() * 40);
        ((CharArrayBuffer)object).append("Cookie");
        ((CharArrayBuffer)object).append(": ");
        ((CharArrayBuffer)object).append("$Version=");
        ((CharArrayBuffer)object).append(Integer.toString(n));
        arrayList = arrayList.iterator();
        do {
            if (!arrayList.hasNext()) {
                arrayList = new ArrayList<Header>(1);
                arrayList.add(new BufferedHeader((CharArrayBuffer)object));
                return arrayList;
            }
            cookie = (Cookie)arrayList.next();
            ((CharArrayBuffer)object).append("; ");
            this.formatCookieAsVer((CharArrayBuffer)object, cookie, n);
        } while (true);
    }

    protected void formatCookieAsVer(CharArrayBuffer charArrayBuffer, Cookie cookie, int n) {
        this.formatParamAsVer(charArrayBuffer, cookie.getName(), cookie.getValue(), n);
        if (cookie.getPath() != null && cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("path")) {
            charArrayBuffer.append("; ");
            this.formatParamAsVer(charArrayBuffer, "$Path", cookie.getPath(), n);
        }
        if (cookie.getDomain() == null) return;
        if (!(cookie instanceof ClientCookie)) return;
        if (!((ClientCookie)cookie).containsAttribute("domain")) return;
        charArrayBuffer.append("; ");
        this.formatParamAsVer(charArrayBuffer, "$Domain", cookie.getDomain(), n);
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        if (list == null) throw new IllegalArgumentException("List of cookies may not be null");
        if (list.isEmpty()) throw new IllegalArgumentException("List of cookies may not be empty");
        List<Cookie> list2 = list;
        if (list.size() > 1) {
            list2 = new ArrayList<Cookie>(list);
            Collections.sort(list2, PATH_COMPARATOR);
        }
        if (!this.oneHeader) return this.doFormatManyHeaders(list2);
        return this.doFormatOneHeader(list2);
    }

    protected void formatParamAsVer(CharArrayBuffer charArrayBuffer, String string2, String string3, int n) {
        charArrayBuffer.append(string2);
        charArrayBuffer.append("=");
        if (string3 == null) return;
        if (n > 0) {
            charArrayBuffer.append('\"');
            charArrayBuffer.append(string3);
            charArrayBuffer.append('\"');
            return;
        }
        charArrayBuffer.append(string3);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Header getVersionHeader() {
        return null;
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin object) throws MalformedCookieException {
        if (header == null) throw new IllegalArgumentException("Header may not be null");
        if (object == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (header.getName().equalsIgnoreCase("Set-Cookie")) {
            return this.parse(header.getElements(), (CookieOrigin)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unrecognized cookie header '");
        ((StringBuilder)object).append(header.toString());
        ((StringBuilder)object).append("'");
        throw new MalformedCookieException(((StringBuilder)object).toString());
    }

    public String toString() {
        return "rfc2109";
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        String string2 = cookie.getName();
        if (string2.indexOf(32) != -1) throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
        if (string2.startsWith("$")) throw new CookieRestrictionViolationException("Cookie name may not start with $");
        super.validate(cookie, cookieOrigin);
    }
}

