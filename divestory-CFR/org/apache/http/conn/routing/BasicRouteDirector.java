/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRouteDirector;
import org.apache.http.conn.routing.RouteInfo;

public class BasicRouteDirector
implements HttpRouteDirector {
    protected int directStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo2.getHopCount() > 1) {
            return -1;
        }
        if (!routeInfo.getTargetHost().equals(routeInfo2.getTargetHost())) {
            return -1;
        }
        if (routeInfo.isSecure() != routeInfo2.isSecure()) {
            return -1;
        }
        if (routeInfo.getLocalAddress() == null) return 0;
        if (routeInfo.getLocalAddress().equals(routeInfo2.getLocalAddress())) return 0;
        return -1;
    }

    protected int firstStep(RouteInfo routeInfo) {
        int n = routeInfo.getHopCount();
        int n2 = 1;
        if (n <= 1) return n2;
        return 2;
    }

    @Override
    public int nextStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo == null) throw new IllegalArgumentException("Planned route may not be null.");
        if (routeInfo2 == null) return this.firstStep(routeInfo);
        if (routeInfo2.getHopCount() < 1) return this.firstStep(routeInfo);
        if (routeInfo.getHopCount() <= 1) return this.directStep(routeInfo, routeInfo2);
        return this.proxiedStep(routeInfo, routeInfo2);
    }

    protected int proxiedStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        int n;
        if (routeInfo2.getHopCount() <= 1) {
            return -1;
        }
        if (!routeInfo.getTargetHost().equals(routeInfo2.getTargetHost())) {
            return -1;
        }
        int n2 = routeInfo.getHopCount();
        if (n2 < (n = routeInfo2.getHopCount())) {
            return -1;
        }
        for (int i = 0; i < n - 1; ++i) {
            if (routeInfo.getHopTarget(i).equals(routeInfo2.getHopTarget(i))) continue;
            return -1;
        }
        if (n2 > n) {
            return 4;
        }
        if (routeInfo2.isTunnelled()) {
            if (!routeInfo.isTunnelled()) return -1;
        }
        if (routeInfo2.isLayered() && !routeInfo.isLayered()) {
            return -1;
        }
        if (routeInfo.isTunnelled() && !routeInfo2.isTunnelled()) {
            return 3;
        }
        if (routeInfo.isLayered() && !routeInfo2.isLayered()) {
            return 5;
        }
        if (routeInfo.isSecure() == routeInfo2.isSecure()) return 0;
        return -1;
    }
}

