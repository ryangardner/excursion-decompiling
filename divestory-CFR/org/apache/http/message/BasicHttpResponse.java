/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.message.HeaderGroup;

public class BasicHttpResponse
extends AbstractHttpMessage
implements HttpResponse {
    private HttpEntity entity;
    private Locale locale;
    private ReasonPhraseCatalog reasonCatalog;
    private StatusLine statusline;

    public BasicHttpResponse(ProtocolVersion protocolVersion, int n, String string2) {
        this(new BasicStatusLine(protocolVersion, n, string2), null, null);
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this(statusLine, null, null);
    }

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        if (statusLine == null) throw new IllegalArgumentException("Status line may not be null.");
        this.statusline = statusLine;
        this.reasonCatalog = reasonPhraseCatalog;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        this.locale = locale;
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.statusline.getProtocolVersion();
    }

    protected String getReason(int n) {
        ReasonPhraseCatalog reasonPhraseCatalog = this.reasonCatalog;
        if (reasonPhraseCatalog != null) return reasonPhraseCatalog.getReason(n, this.locale);
        return null;
    }

    @Override
    public StatusLine getStatusLine() {
        return this.statusline;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override
    public void setLocale(Locale locale) {
        if (locale == null) throw new IllegalArgumentException("Locale may not be null.");
        this.locale = locale;
        int n = this.statusline.getStatusCode();
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), n, this.getReason(n));
    }

    @Override
    public void setReasonPhrase(String string2) {
        if (string2 != null) {
            if (string2.indexOf(10) >= 0) throw new IllegalArgumentException("Line break in reason phrase.");
            if (string2.indexOf(13) >= 0) throw new IllegalArgumentException("Line break in reason phrase.");
        }
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), string2);
    }

    @Override
    public void setStatusCode(int n) {
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), n, this.getReason(n));
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n) {
        this.statusline = new BasicStatusLine(protocolVersion, n, this.getReason(n));
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n, String string2) {
        this.statusline = new BasicStatusLine(protocolVersion, n, string2);
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        if (statusLine == null) throw new IllegalArgumentException("Status line may not be null");
        this.statusline = statusLine;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.statusline);
        stringBuffer.append(" ");
        stringBuffer.append(this.headergroup);
        return stringBuffer.toString();
    }
}

