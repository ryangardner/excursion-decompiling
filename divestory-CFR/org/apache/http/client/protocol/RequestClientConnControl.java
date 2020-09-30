/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.RequestLine;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

public class RequestClientConnControl
implements HttpRequestInterceptor {
    private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            httpRequest.setHeader(PROXY_CONN_DIRECTIVE, "Keep-Alive");
            return;
        }
        if ((object = (HttpRoutedConnection)object.getAttribute("http.connection")) == null) {
            this.log.debug((Object)"HTTP connection not set in the context");
            return;
        }
        if ((((HttpRoute)(object = object.getRoute())).getHopCount() == 1 || ((HttpRoute)object).isTunnelled()) && !httpRequest.containsHeader("Connection")) {
            httpRequest.addHeader("Connection", "Keep-Alive");
        }
        if (((HttpRoute)object).getHopCount() != 2) return;
        if (((HttpRoute)object).isTunnelled()) return;
        if (httpRequest.containsHeader(PROXY_CONN_DIRECTIVE)) return;
        httpRequest.addHeader(PROXY_CONN_DIRECTIVE, "Keep-Alive");
    }
}

