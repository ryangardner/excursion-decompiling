/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.Serializable;
import java.util.Locale;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public final class HttpHost
implements Cloneable,
Serializable {
    public static final String DEFAULT_SCHEME_NAME = "http";
    private static final long serialVersionUID = -7529410654042457626L;
    protected final String hostname;
    protected final String lcHostname;
    protected final int port;
    protected final String schemeName;

    public HttpHost(String string2) {
        this(string2, -1, null);
    }

    public HttpHost(String string2, int n) {
        this(string2, n, null);
    }

    public HttpHost(String string2, int n, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Host name may not be null");
        this.hostname = string2;
        this.lcHostname = string2.toLowerCase(Locale.ENGLISH);
        this.schemeName = string3 != null ? string3.toLowerCase(Locale.ENGLISH) : DEFAULT_SCHEME_NAME;
        this.port = n;
    }

    public HttpHost(HttpHost httpHost) {
        this(httpHost.hostname, httpHost.port, httpHost.schemeName);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof HttpHost)) return false;
        object = (HttpHost)object;
        if (!this.lcHostname.equals(((HttpHost)object).lcHostname)) return false;
        if (this.port != ((HttpHost)object).port) return false;
        if (!this.schemeName.equals(((HttpHost)object).schemeName)) return false;
        return bl;
    }

    public String getHostName() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.lcHostname), this.port), this.schemeName);
    }

    public String toHostString() {
        if (this.port == -1) return this.hostname;
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(this.hostname.length() + 6);
        charArrayBuffer.append(this.hostname);
        charArrayBuffer.append(":");
        charArrayBuffer.append(Integer.toString(this.port));
        return charArrayBuffer.toString();
    }

    public String toString() {
        return this.toURI();
    }

    public String toURI() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        charArrayBuffer.append(this.schemeName);
        charArrayBuffer.append("://");
        charArrayBuffer.append(this.hostname);
        if (this.port == -1) return charArrayBuffer.toString();
        charArrayBuffer.append(':');
        charArrayBuffer.append(Integer.toString(this.port));
        return charArrayBuffer.toString();
    }
}

