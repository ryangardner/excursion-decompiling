/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpDateGenerator;

public class ResponseDate
implements HttpResponseInterceptor {
    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null.");
        if (httpResponse.getStatusLine().getStatusCode() < 200) return;
        if (httpResponse.containsHeader("Date")) return;
        httpResponse.setHeader("Date", DATE_GENERATOR.getCurrentDate());
    }
}

