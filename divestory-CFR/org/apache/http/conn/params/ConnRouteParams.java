/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

public class ConnRouteParams
implements ConnRoutePNames {
    public static final HttpHost NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
    public static final HttpRoute NO_ROUTE = new HttpRoute(NO_HOST);

    private ConnRouteParams() {
    }

    public static HttpHost getDefaultProxy(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("Parameters must not be null.");
        HttpHost httpHost = (HttpHost)object.getParameter("http.route.default-proxy");
        object = httpHost;
        if (httpHost == null) return object;
        object = httpHost;
        if (!NO_HOST.equals(httpHost)) return object;
        return null;
    }

    public static HttpRoute getForcedRoute(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("Parameters must not be null.");
        HttpRoute httpRoute = (HttpRoute)object.getParameter("http.route.forced-route");
        object = httpRoute;
        if (httpRoute == null) return object;
        object = httpRoute;
        if (!NO_ROUTE.equals(httpRoute)) return object;
        return null;
    }

    public static InetAddress getLocalAddress(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        return (InetAddress)httpParams.getParameter("http.route.local-address");
    }

    public static void setDefaultProxy(HttpParams httpParams, HttpHost httpHost) {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        httpParams.setParameter("http.route.default-proxy", httpHost);
    }

    public static void setForcedRoute(HttpParams httpParams, HttpRoute httpRoute) {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        httpParams.setParameter("http.route.forced-route", httpRoute);
    }

    public static void setLocalAddress(HttpParams httpParams, InetAddress inetAddress) {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        httpParams.setParameter("http.route.local-address", inetAddress);
    }
}

