/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public abstract class AbstractHttpMessage
implements HttpMessage {
    protected HeaderGroup headergroup = new HeaderGroup();
    protected HttpParams params;

    protected AbstractHttpMessage() {
        this(null);
    }

    protected AbstractHttpMessage(HttpParams httpParams) {
        this.params = httpParams;
    }

    @Override
    public void addHeader(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Header name may not be null");
        this.headergroup.addHeader(new BasicHeader(string2, string3));
    }

    @Override
    public void addHeader(Header header) {
        this.headergroup.addHeader(header);
    }

    @Override
    public boolean containsHeader(String string2) {
        return this.headergroup.containsHeader(string2);
    }

    @Override
    public Header[] getAllHeaders() {
        return this.headergroup.getAllHeaders();
    }

    @Override
    public Header getFirstHeader(String string2) {
        return this.headergroup.getFirstHeader(string2);
    }

    @Override
    public Header[] getHeaders(String string2) {
        return this.headergroup.getHeaders(string2);
    }

    @Override
    public Header getLastHeader(String string2) {
        return this.headergroup.getLastHeader(string2);
    }

    @Override
    public HttpParams getParams() {
        if (this.params != null) return this.params;
        this.params = new BasicHttpParams();
        return this.params;
    }

    @Override
    public HeaderIterator headerIterator() {
        return this.headergroup.iterator();
    }

    @Override
    public HeaderIterator headerIterator(String string2) {
        return this.headergroup.iterator(string2);
    }

    @Override
    public void removeHeader(Header header) {
        this.headergroup.removeHeader(header);
    }

    @Override
    public void removeHeaders(String string2) {
        if (string2 == null) {
            return;
        }
        HeaderIterator headerIterator = this.headergroup.iterator();
        while (headerIterator.hasNext()) {
            if (!string2.equalsIgnoreCase(((Header)headerIterator.next()).getName())) continue;
            headerIterator.remove();
        }
    }

    @Override
    public void setHeader(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Header name may not be null");
        this.headergroup.updateHeader(new BasicHeader(string2, string3));
    }

    @Override
    public void setHeader(Header header) {
        this.headergroup.updateHeader(header);
    }

    @Override
    public void setHeaders(Header[] arrheader) {
        this.headergroup.setHeaders(arrheader);
    }

    @Override
    public void setParams(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.params = httpParams;
    }
}

