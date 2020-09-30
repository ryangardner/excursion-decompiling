/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpDateGenerator;

public class RequestDate
implements HttpRequestInterceptor {
    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null.");
        if (!(httpRequest instanceof HttpEntityEnclosingRequest)) return;
        if (httpRequest.containsHeader("Date")) return;
        httpRequest.setHeader("Date", DATE_GENERATOR.getCurrentDate());
    }
}

