/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.util.Map;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerResolver;
import org.apache.http.protocol.UriPatternMatcher;

public class HttpRequestHandlerRegistry
implements HttpRequestHandlerResolver {
    private final UriPatternMatcher matcher = new UriPatternMatcher();

    @Override
    public HttpRequestHandler lookup(String string2) {
        return (HttpRequestHandler)this.matcher.lookup(string2);
    }

    protected boolean matchUriRequestPattern(String string2, String string3) {
        return this.matcher.matchUriRequestPattern(string2, string3);
    }

    public void register(String string2, HttpRequestHandler httpRequestHandler) {
        if (string2 == null) throw new IllegalArgumentException("URI request pattern may not be null");
        if (httpRequestHandler == null) throw new IllegalArgumentException("Request handler may not be null");
        this.matcher.register(string2, httpRequestHandler);
    }

    public void setHandlers(Map map) {
        this.matcher.setObjects(map);
    }

    public void unregister(String string2) {
        this.matcher.unregister(string2);
    }
}

