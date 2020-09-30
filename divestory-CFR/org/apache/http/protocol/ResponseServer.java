/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class ResponseServer
implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext object) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpResponse.containsHeader("Server")) return;
        object = (String)httpResponse.getParams().getParameter("http.origin-server");
        if (object == null) return;
        httpResponse.addHeader("Server", (String)object);
    }
}

