/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.net.URI;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.RequestLine;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

@Deprecated
class DefaultRedirectStrategyAdaptor
implements RedirectStrategy {
    private final RedirectHandler handler;

    @Deprecated
    public DefaultRedirectStrategyAdaptor(RedirectHandler redirectHandler) {
        this.handler = redirectHandler;
    }

    @Override
    public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse object, HttpContext httpContext) throws ProtocolException {
        object = this.handler.getLocationURI((HttpResponse)object, httpContext);
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) return new HttpGet((URI)object);
        return new HttpHead((URI)object);
    }

    @Override
    public boolean isRedirected(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        return this.handler.isRedirectRequested(httpResponse, httpContext);
    }
}

