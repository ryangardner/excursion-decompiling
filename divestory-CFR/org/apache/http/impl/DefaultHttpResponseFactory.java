/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpResponseFactory
implements HttpResponseFactory {
    protected final ReasonPhraseCatalog reasonCatalog;

    public DefaultHttpResponseFactory() {
        this(EnglishReasonPhraseCatalog.INSTANCE);
    }

    public DefaultHttpResponseFactory(ReasonPhraseCatalog reasonPhraseCatalog) {
        if (reasonPhraseCatalog == null) throw new IllegalArgumentException("Reason phrase catalog must not be null.");
        this.reasonCatalog = reasonPhraseCatalog;
    }

    protected Locale determineLocale(HttpContext httpContext) {
        return Locale.getDefault();
    }

    @Override
    public HttpResponse newHttpResponse(ProtocolVersion protocolVersion, int n, HttpContext object) {
        if (protocolVersion == null) throw new IllegalArgumentException("HTTP version may not be null");
        object = this.determineLocale((HttpContext)object);
        return new BasicHttpResponse(new BasicStatusLine(protocolVersion, n, this.reasonCatalog.getReason(n, (Locale)object)), this.reasonCatalog, (Locale)object);
    }

    @Override
    public HttpResponse newHttpResponse(StatusLine statusLine, HttpContext object) {
        if (statusLine == null) throw new IllegalArgumentException("Status line may not be null");
        object = this.determineLocale((HttpContext)object);
        return new BasicHttpResponse(statusLine, this.reasonCatalog, (Locale)object);
    }
}

