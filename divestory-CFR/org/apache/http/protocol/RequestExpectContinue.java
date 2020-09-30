/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class RequestExpectContinue
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (!(httpRequest instanceof HttpEntityEnclosingRequest)) return;
        object = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
        if (object == null) return;
        if (object.getContentLength() == 0L) return;
        object = httpRequest.getRequestLine().getProtocolVersion();
        if (!HttpProtocolParams.useExpectContinue(httpRequest.getParams())) return;
        if (((ProtocolVersion)object).lessEquals(HttpVersion.HTTP_1_0)) return;
        httpRequest.addHeader("Expect", "100-continue");
    }
}

