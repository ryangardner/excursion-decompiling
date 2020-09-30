/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BrowserCompatSpec
extends CookieSpecBase {
    @Deprecated
    protected static final String[] DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
    private static final String[] DEFAULT_DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
    private final String[] datepatterns;

    public BrowserCompatSpec() {
        this(null);
    }

    public BrowserCompatSpec(String[] arrstring) {
        this.datepatterns = arrstring != null ? (String[])arrstring.clone() : DEFAULT_DATE_PATTERNS;
        this.registerAttribHandler("path", new BasicPathHandler());
        this.registerAttribHandler("domain", new BasicDomainHandler());
        this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
        this.registerAttribHandler("secure", new BasicSecureHandler());
        this.registerAttribHandler("comment", new BasicCommentHandler());
        this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        if (list == null) throw new IllegalArgumentException("List of cookies may not be null");
        if (list.isEmpty()) throw new IllegalArgumentException("List of cookies may not be empty");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 20);
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        int n = 0;
        do {
            if (n >= list.size()) {
                list = new ArrayList<Cookie>(1);
                list.add((Cookie)((Object)new BufferedHeader(charArrayBuffer)));
                return list;
            }
            Object object = list.get(n);
            if (n > 0) {
                charArrayBuffer.append("; ");
            }
            charArrayBuffer.append(object.getName());
            charArrayBuffer.append("=");
            object = object.getValue();
            if (object != null) {
                charArrayBuffer.append((String)object);
            }
            ++n;
        } while (true);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Header getVersionHeader() {
        return null;
    }

    @Override
    public List<Cookie> parse(Header object, CookieOrigin object2) throws MalformedCookieException {
        Object object3;
        if (object == null) throw new IllegalArgumentException("Header may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (!object.getName().equalsIgnoreCase("Set-Cookie")) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unrecognized cookie header '");
            ((StringBuilder)object2).append(object.toString());
            ((StringBuilder)object2).append("'");
            throw new MalformedCookieException(((StringBuilder)object2).toString());
        }
        Object object4 = object.getElements();
        int n = ((HeaderElement[])object4).length;
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < n; ++i) {
            object3 = object4[i];
            if (object3.getParameterByName("version") != null) {
                bl2 = true;
            }
            if (object3.getParameterByName("expires") == null) continue;
            bl = true;
        }
        if (!bl) {
            if (bl2) return this.parse((HeaderElement[])object4, (CookieOrigin)object2);
        }
        object3 = NetscapeDraftHeaderParser.DEFAULT;
        if (object instanceof FormattedHeader) {
            object = (FormattedHeader)object;
            object4 = object.getBuffer();
            object = new ParserCursor(object.getValuePos(), ((CharArrayBuffer)object4).length());
        } else {
            if ((object = object.getValue()) == null) throw new MalformedCookieException("Header value is null");
            object4 = new CharArrayBuffer(((String)object).length());
            ((CharArrayBuffer)object4).append((String)object);
            object = new ParserCursor(0, ((CharArrayBuffer)object4).length());
        }
        object4 = new HeaderElement[]{((NetscapeDraftHeaderParser)object3).parseHeader((CharArrayBuffer)object4, (ParserCursor)object)};
        return this.parse((HeaderElement[])object4, (CookieOrigin)object2);
    }

    public String toString() {
        return "compatibility";
    }
}

