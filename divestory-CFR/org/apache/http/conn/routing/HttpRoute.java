/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.LangUtils;

public final class HttpRoute
implements RouteInfo,
Cloneable {
    private static final HttpHost[] EMPTY_HTTP_HOST_ARRAY = new HttpHost[0];
    private final RouteInfo.LayerType layered;
    private final InetAddress localAddress;
    private final HttpHost[] proxyChain;
    private final boolean secure;
    private final HttpHost targetHost;
    private final RouteInfo.TunnelType tunnelled;

    private HttpRoute(InetAddress inetAddress, HttpHost httpHost, HttpHost[] arrhttpHost, boolean bl, RouteInfo.TunnelType enum_, RouteInfo.LayerType layerType) {
        if (httpHost == null) throw new IllegalArgumentException("Target host may not be null.");
        if (arrhttpHost == null) throw new IllegalArgumentException("Proxies may not be null.");
        if (enum_ == RouteInfo.TunnelType.TUNNELLED) {
            if (arrhttpHost.length == 0) throw new IllegalArgumentException("Proxy required if tunnelled.");
        }
        RouteInfo.TunnelType tunnelType = enum_;
        if (enum_ == null) {
            tunnelType = RouteInfo.TunnelType.PLAIN;
        }
        enum_ = layerType;
        if (layerType == null) {
            enum_ = RouteInfo.LayerType.PLAIN;
        }
        this.targetHost = httpHost;
        this.localAddress = inetAddress;
        this.proxyChain = arrhttpHost;
        this.secure = bl;
        this.tunnelled = tunnelType;
        this.layered = enum_;
    }

    public HttpRoute(HttpHost httpHost) {
        this(null, httpHost, EMPTY_HTTP_HOST_ARRAY, false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean bl) {
        HttpHost[] arrhttpHost = HttpRoute.toChain(httpHost2);
        RouteInfo.TunnelType tunnelType = bl ? RouteInfo.TunnelType.TUNNELLED : RouteInfo.TunnelType.PLAIN;
        RouteInfo.LayerType layerType = bl ? RouteInfo.LayerType.LAYERED : RouteInfo.LayerType.PLAIN;
        this(inetAddress, httpHost, arrhttpHost, bl, tunnelType, layerType);
        if (httpHost2 == null) throw new IllegalArgumentException("Proxy host may not be null.");
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean bl, RouteInfo.TunnelType tunnelType, RouteInfo.LayerType layerType) {
        this(inetAddress, httpHost, HttpRoute.toChain(httpHost2), bl, tunnelType, layerType);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, boolean bl) {
        this(inetAddress, httpHost, EMPTY_HTTP_HOST_ARRAY, bl, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost[] arrhttpHost, boolean bl, RouteInfo.TunnelType tunnelType, RouteInfo.LayerType layerType) {
        this(inetAddress, httpHost, HttpRoute.toChain(arrhttpHost), bl, tunnelType, layerType);
    }

    private static HttpHost[] toChain(HttpHost httpHost) {
        if (httpHost != null) return new HttpHost[]{httpHost};
        return EMPTY_HTTP_HOST_ARRAY;
    }

    private static HttpHost[] toChain(HttpHost[] arrhttpHost) {
        if (arrhttpHost == null) return EMPTY_HTTP_HOST_ARRAY;
        if (arrhttpHost.length < 1) {
            return EMPTY_HTTP_HOST_ARRAY;
        }
        int n = arrhttpHost.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                HttpHost[] arrhttpHost2 = new HttpHost[arrhttpHost.length];
                System.arraycopy(arrhttpHost, 0, arrhttpHost2, 0, arrhttpHost.length);
                return arrhttpHost2;
            }
            if (arrhttpHost[n2] == null) throw new IllegalArgumentException("Proxy chain may not contain null elements.");
            ++n2;
        } while (true);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof HttpRoute)) return false;
        object = (HttpRoute)object;
        if (this.secure != ((HttpRoute)object).secure) return false;
        if (this.tunnelled != ((HttpRoute)object).tunnelled) return false;
        if (this.layered != ((HttpRoute)object).layered) return false;
        if (!LangUtils.equals(this.targetHost, ((HttpRoute)object).targetHost)) return false;
        if (!LangUtils.equals(this.localAddress, ((HttpRoute)object).localAddress)) return false;
        if (!LangUtils.equals(this.proxyChain, ((HttpRoute)object).proxyChain)) return false;
        return bl;
    }

    @Override
    public final int getHopCount() {
        return this.proxyChain.length + 1;
    }

    @Override
    public final HttpHost getHopTarget(int n) {
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hop index must not be negative: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n2 = this.getHopCount();
        if (n >= n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hop index ");
            stringBuilder.append(n);
            stringBuilder.append(" exceeds route length ");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n >= n2 - 1) return this.targetHost;
        return this.proxyChain[n];
    }

    @Override
    public final RouteInfo.LayerType getLayerType() {
        return this.layered;
    }

    @Override
    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override
    public final HttpHost getProxyHost() {
        Object object = this.proxyChain;
        if (((HttpHost[])object).length != 0) return object[0];
        return null;
    }

    @Override
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override
    public final RouteInfo.TunnelType getTunnelType() {
        return this.tunnelled;
    }

    public final int hashCode() {
        HttpHost[] arrhttpHost;
        int n = LangUtils.hashCode(LangUtils.hashCode(17, this.targetHost), this.localAddress);
        int n2 = 0;
        while (n2 < (arrhttpHost = this.proxyChain).length) {
            n = LangUtils.hashCode(n, arrhttpHost[n2]);
            ++n2;
        }
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(n, this.secure), (Object)this.tunnelled), (Object)this.layered);
    }

    @Override
    public final boolean isLayered() {
        if (this.layered != RouteInfo.LayerType.LAYERED) return false;
        return true;
    }

    @Override
    public final boolean isSecure() {
        return this.secure;
    }

    @Override
    public final boolean isTunnelled() {
        if (this.tunnelled != RouteInfo.TunnelType.TUNNELLED) return false;
        return true;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getHopCount() * 30 + 50);
        stringBuilder.append("HttpRoute[");
        HttpHost[] arrhttpHost = this.localAddress;
        if (arrhttpHost != null) {
            stringBuilder.append(arrhttpHost);
            stringBuilder.append("->");
        }
        stringBuilder.append('{');
        if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
            stringBuilder.append('t');
        }
        if (this.layered == RouteInfo.LayerType.LAYERED) {
            stringBuilder.append('l');
        }
        if (this.secure) {
            stringBuilder.append('s');
        }
        stringBuilder.append("}->");
        arrhttpHost = this.proxyChain;
        int n = arrhttpHost.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                stringBuilder.append(this.targetHost);
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(arrhttpHost[n2]);
            stringBuilder.append("->");
            ++n2;
        } while (true);
    }
}

