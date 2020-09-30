/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;

public final class ConnPerRouteBean
implements ConnPerRoute {
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private volatile int defaultMax;
    private final ConcurrentHashMap<HttpRoute, Integer> maxPerHostMap = new ConcurrentHashMap();

    public ConnPerRouteBean() {
        this(2);
    }

    public ConnPerRouteBean(int n) {
        this.setDefaultMaxPerRoute(n);
    }

    @Deprecated
    public int getDefaultMax() {
        return this.defaultMax;
    }

    public int getDefaultMaxPerRoute() {
        return this.defaultMax;
    }

    @Override
    public int getMaxForRoute(HttpRoute object) {
        if (object == null) throw new IllegalArgumentException("HTTP route may not be null.");
        if ((object = this.maxPerHostMap.get(object)) == null) return this.defaultMax;
        return (Integer)object;
    }

    public void setDefaultMaxPerRoute(int n) {
        if (n < 1) throw new IllegalArgumentException("The maximum must be greater than 0.");
        this.defaultMax = n;
    }

    public void setMaxForRoute(HttpRoute httpRoute, int n) {
        if (httpRoute == null) throw new IllegalArgumentException("HTTP route may not be null.");
        if (n < 1) throw new IllegalArgumentException("The maximum must be greater than 0.");
        this.maxPerHostMap.put(httpRoute, n);
    }

    public void setMaxForRoutes(Map<HttpRoute, Integer> map) {
        if (map == null) {
            return;
        }
        this.maxPerHostMap.clear();
        this.maxPerHostMap.putAll(map);
    }

    public String toString() {
        return this.maxPerHostMap.toString();
    }
}

