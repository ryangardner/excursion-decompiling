/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public class NoConnectionReuseStrategy
implements ConnectionReuseStrategy {
    @Override
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        return false;
    }
}

