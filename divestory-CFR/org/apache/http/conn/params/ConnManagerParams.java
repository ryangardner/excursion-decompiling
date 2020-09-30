/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.params;

import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

@Deprecated
public final class ConnManagerParams
implements ConnManagerPNames {
    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute(){

        @Override
        public int getMaxForRoute(HttpRoute httpRoute) {
            return 2;
        }
    };
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

    @Deprecated
    public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters must not be null.");
        ConnPerRoute connPerRoute = (ConnPerRoute)object.getParameter("http.conn-manager.max-per-route");
        object = connPerRoute;
        if (connPerRoute != null) return object;
        return DEFAULT_CONN_PER_ROUTE;
    }

    @Deprecated
    public static int getMaxTotalConnections(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters must not be null.");
        return httpParams.getIntParameter("http.conn-manager.max-total", 20);
    }

    @Deprecated
    public static long getTimeout(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        Long l = (Long)httpParams.getParameter("http.conn-manager.timeout");
        if (l == null) return httpParams.getIntParameter("http.connection.timeout", 0);
        return l;
    }

    @Deprecated
    public static void setMaxConnectionsPerRoute(HttpParams httpParams, ConnPerRoute connPerRoute) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters must not be null.");
        httpParams.setParameter("http.conn-manager.max-per-route", connPerRoute);
    }

    @Deprecated
    public static void setMaxTotalConnections(HttpParams httpParams, int n) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters must not be null.");
        httpParams.setIntParameter("http.conn-manager.max-total", n);
    }

    @Deprecated
    public static void setTimeout(HttpParams httpParams, long l) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setLongParameter("http.conn-manager.timeout", l);
    }

}

