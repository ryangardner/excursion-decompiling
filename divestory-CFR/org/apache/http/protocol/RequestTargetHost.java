/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.protocol.HttpContext;

public class RequestTargetHost
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") && protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
            return;
        }
        if (httpRequest.containsHeader("Host")) return;
        HttpHost httpHost = (HttpHost)object.getAttribute("http.target_host");
        Object object2 = httpHost;
        if (httpHost == null) {
            object2 = (HttpConnection)object.getAttribute("http.connection");
            object = httpHost;
            if (object2 instanceof HttpInetConnection) {
                object = (HttpInetConnection)object2;
                object2 = object.getRemoteAddress();
                int n = object.getRemotePort();
                object = httpHost;
                if (object2 != null) {
                    object = new HttpHost(((InetAddress)object2).getHostName(), n);
                }
            }
            object2 = object;
            if (object == null) {
                if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) throw new ProtocolException("Target host missing");
                return;
            }
        }
        httpRequest.addHeader("Host", ((HttpHost)object2).toHostString());
    }
}

