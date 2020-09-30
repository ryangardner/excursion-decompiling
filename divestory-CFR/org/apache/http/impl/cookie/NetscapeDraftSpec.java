/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.NetscapeDomainHandler;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class NetscapeDraftSpec
extends CookieSpecBase {
    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";
    private final String[] datepatterns;

    public NetscapeDraftSpec() {
        this(null);
    }

    public NetscapeDraftSpec(String[] arrstring) {
        this.datepatterns = arrstring != null ? (String[])arrstring.clone() : new String[]{EXPIRES_PATTERN};
        this.registerAttribHandler("path", new BasicPathHandler());
        this.registerAttribHandler("domain", new NetscapeDomainHandler());
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
            object = object.getValue();
            if (object != null) {
                charArrayBuffer.append("=");
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
        CharArrayBuffer charArrayBuffer;
        if (object == null) throw new IllegalArgumentException("Header may not be null");
        if (object2 == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (!object.getName().equalsIgnoreCase("Set-Cookie")) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unrecognized cookie header '");
            ((StringBuilder)object2).append(object.toString());
            ((StringBuilder)object2).append("'");
            throw new MalformedCookieException(((StringBuilder)object2).toString());
        }
        NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
        if (object instanceof FormattedHeader) {
            object = (FormattedHeader)object;
            charArrayBuffer = object.getBuffer();
            object = new ParserCursor(object.getValuePos(), charArrayBuffer.length());
            return this.parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, (ParserCursor)object)}, (CookieOrigin)object2);
        } else {
            if ((object = object.getValue()) == null) throw new MalformedCookieException("Header value is null");
            charArrayBuffer = new CharArrayBuffer(((String)object).length());
            charArrayBuffer.append((String)object);
            object = new ParserCursor(0, charArrayBuffer.length());
        }
        return this.parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, (ParserCursor)object)}, (CookieOrigin)object2);
    }

    public String toString() {
        return "netscape";
    }
}

