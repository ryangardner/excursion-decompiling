/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.RequestLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class RequestDefaultHeaders
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        object = (Collection)httpRequest.getParams().getParameter("http.default-headers");
        if (object == null) return;
        object = object.iterator();
        while (object.hasNext()) {
            httpRequest.addHeader((Header)object.next());
        }
    }
}

