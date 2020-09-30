/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class RequestUserAgent
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpRequest.containsHeader("User-Agent")) return;
        object = HttpProtocolParams.getUserAgent(httpRequest.getParams());
        if (object == null) return;
        httpRequest.addHeader("User-Agent", (String)object);
    }
}

