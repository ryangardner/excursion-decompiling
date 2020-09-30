/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.RequestWrapper;

public class RoutedRequest {
    protected final RequestWrapper request;
    protected final HttpRoute route;

    public RoutedRequest(RequestWrapper requestWrapper, HttpRoute httpRoute) {
        this.request = requestWrapper;
        this.route = httpRoute;
    }

    public final RequestWrapper getRequest() {
        return this.request;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }
}

