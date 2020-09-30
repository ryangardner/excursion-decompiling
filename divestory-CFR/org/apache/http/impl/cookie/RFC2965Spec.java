/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.impl.cookie.RFC2965CommentUrlAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DiscardAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DomainAttributeHandler;
import org.apache.http.impl.cookie.RFC2965PortAttributeHandler;
import org.apache.http.impl.cookie.RFC2965VersionAttributeHandler;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class RFC2965Spec
extends RFC2109Spec {
    public RFC2965Spec() {
        this(null, false);
    }

    public RFC2965Spec(String[] arrstring, boolean bl) {
        super(arrstring, bl);
        this.registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
        this.registerAttribHandler("port", new RFC2965PortAttributeHandler());
        this.registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
        this.registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
        this.registerAttribHandler("version", new RFC2965VersionAttributeHandler());
    }

    private static CookieOrigin adjustEffectiveHost(CookieOrigin cookieOrigin) {
        String string2;
        boolean bl;
        block2 : {
            string2 = cookieOrigin.getHost();
            boolean bl2 = false;
            for (int i = 0; i < string2.length(); ++i) {
                char c = string2.charAt(i);
                bl = bl2;
                if (c != '.') {
                    if (c != ':') continue;
                    bl = bl2;
                }
                break block2;
            }
            bl = true;
        }
        if (!bl) return cookieOrigin;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".local");
        return new CookieOrigin(stringBuilder.toString(), cookieOrigin.getPort(), cookieOrigin.getPath(), cookieOrigin.isSecure());
    }

    private List<Cookie> createCookies(HeaderElement[] arrheaderElement, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ArrayList<Cookie> arrayList = new ArrayList<Cookie>(arrheaderElement.length);
        int n = arrheaderElement.length;
        int n2 = 0;
        while (n2 < n) {
            Iterator<Map.Entry<String, Object>> iterator2 = arrheaderElement[n2];
            Object object = iterator2.getName();
            Object object2 = iterator2.getValue();
            if (object == null) throw new MalformedCookieException("Cookie name may not be empty");
            if (((String)object).length() == 0) throw new MalformedCookieException("Cookie name may not be empty");
            object2 = new BasicClientCookie2((String)object, (String)object2);
            ((BasicClientCookie)object2).setPath(RFC2965Spec.getDefaultPath(cookieOrigin));
            ((BasicClientCookie)object2).setDomain(RFC2965Spec.getDefaultDomain(cookieOrigin));
            ((BasicClientCookie2)object2).setPorts(new int[]{cookieOrigin.getPort()});
            Object object3 = iterator2.getParameters();
            iterator2 = new HashMap(((NameValuePair[])object3).length);
            for (int i = ((NameValuePair[])object3).length - 1; i >= 0; --i) {
                object = object3[i];
                iterator2.put(object.getName().toLowerCase(Locale.ENGLISH), object);
            }
            iterator2 = iterator2.entrySet().iterator();
            while (iterator2.hasNext()) {
                object = (NameValuePair)iterator2.next().getValue();
                object3 = object.getName().toLowerCase(Locale.ENGLISH);
                ((BasicClientCookie)object2).setAttribute((String)object3, object.getValue());
                if ((object3 = this.findAttribHandler((String)object3)) == null) continue;
                object3.parse((SetCookie)object2, object.getValue());
            }
            arrayList.add((Cookie)object2);
            ++n2;
        }
        return arrayList;
    }

    @Override
    protected void formatCookieAsVer(CharArrayBuffer charArrayBuffer, Cookie arrn, int n) {
        super.formatCookieAsVer(charArrayBuffer, (Cookie)arrn, n);
        if (!(arrn instanceof ClientCookie)) return;
        String string2 = ((ClientCookie)arrn).getAttribute("port");
        if (string2 == null) return;
        charArrayBuffer.append("; $Port");
        charArrayBuffer.append("=\"");
        if (string2.trim().length() > 0 && (arrn = arrn.getPorts()) != null) {
            int n2 = arrn.length;
            for (n = 0; n < n2; ++n) {
                if (n > 0) {
                    charArrayBuffer.append(",");
                }
                charArrayBuffer.append(Integer.toString(arrn[n]));
            }
        }
        charArrayBuffer.append("\"");
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Header getVersionHeader() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
        charArrayBuffer.append("Cookie2");
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(this.getVersion()));
        return new BufferedHeader(charArrayBuffer);
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        return super.match(cookie, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin object) throws MalformedCookieException {
        if (header == null) throw new IllegalArgumentException("Header may not be null");
        if (object == null) throw new IllegalArgumentException("Cookie origin may not be null");
        if (header.getName().equalsIgnoreCase("Set-Cookie2")) {
            object = RFC2965Spec.adjustEffectiveHost((CookieOrigin)object);
            return this.createCookies(header.getElements(), (CookieOrigin)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unrecognized cookie header '");
        ((StringBuilder)object).append(header.toString());
        ((StringBuilder)object).append("'");
        throw new MalformedCookieException(((StringBuilder)object).toString());
    }

    @Override
    protected List<Cookie> parse(HeaderElement[] arrheaderElement, CookieOrigin cookieOrigin) throws MalformedCookieException {
        return this.createCookies(arrheaderElement, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    @Override
    public String toString() {
        return "rfc2965";
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) throw new IllegalArgumentException("Cookie may not be null");
        if (cookieOrigin == null) throw new IllegalArgumentException("Cookie origin may not be null");
        super.validate(cookie, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }
}

