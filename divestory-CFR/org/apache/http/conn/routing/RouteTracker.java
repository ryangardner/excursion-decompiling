/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.LangUtils;

public final class RouteTracker
implements RouteInfo,
Cloneable {
    private boolean connected;
    private RouteInfo.LayerType layered;
    private final InetAddress localAddress;
    private HttpHost[] proxyChain;
    private boolean secure;
    private final HttpHost targetHost;
    private RouteInfo.TunnelType tunnelled;

    public RouteTracker(HttpHost httpHost, InetAddress inetAddress) {
        if (httpHost == null) throw new IllegalArgumentException("Target host may not be null.");
        this.targetHost = httpHost;
        this.localAddress = inetAddress;
        this.tunnelled = RouteInfo.TunnelType.PLAIN;
        this.layered = RouteInfo.LayerType.PLAIN;
    }

    public RouteTracker(HttpRoute httpRoute) {
        this(httpRoute.getTargetHost(), httpRoute.getLocalAddress());
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public final void connectProxy(HttpHost httpHost, boolean bl) {
        if (httpHost == null) throw new IllegalArgumentException("Proxy host may not be null.");
        if (this.connected) throw new IllegalStateException("Already connected.");
        this.connected = true;
        this.proxyChain = new HttpHost[]{httpHost};
        this.secure = bl;
    }

    public final void connectTarget(boolean bl) {
        if (this.connected) throw new IllegalStateException("Already connected.");
        this.connected = true;
        this.secure = bl;
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof RouteTracker)) {
            return false;
        }
        object = (RouteTracker)object;
        if (this.connected != ((RouteTracker)object).connected) return false;
        if (this.secure != ((RouteTracker)object).secure) return false;
        if (this.tunnelled != ((RouteTracker)object).tunnelled) return false;
        if (this.layered != ((RouteTracker)object).layered) return false;
        if (!LangUtils.equals(this.targetHost, ((RouteTracker)object).targetHost)) return false;
        if (!LangUtils.equals(this.localAddress, ((RouteTracker)object).localAddress)) return false;
        if (!LangUtils.equals(this.proxyChain, ((RouteTracker)object).proxyChain)) return false;
        return bl;
    }

    @Override
    public final int getHopCount() {
        boolean bl = this.connected;
        int n = 1;
        if (!bl) {
            return 0;
        }
        HttpHost[] arrhttpHost = this.proxyChain;
        if (arrhttpHost != null) return 1 + arrhttpHost.length;
        return n;
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
            stringBuilder.append(" exceeds tracked route length ");
            stringBuilder.append(n2);
            stringBuilder.append(".");
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
        if (object != null) return object[0];
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
        int n;
        int n2 = n = LangUtils.hashCode(LangUtils.hashCode(17, this.targetHost), this.localAddress);
        if (this.proxyChain == null) return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(n2, this.connected), this.secure), (Object)this.tunnelled), (Object)this.layered);
        int n3 = 0;
        do {
            HttpHost[] arrhttpHost = this.proxyChain;
            n2 = n;
            if (n3 >= arrhttpHost.length) return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(n2, this.connected), this.secure), (Object)this.tunnelled), (Object)this.layered);
            n = LangUtils.hashCode(n, arrhttpHost[n3]);
            ++n3;
        } while (true);
    }

    public final boolean isConnected() {
        return this.connected;
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

    public final void layerProtocol(boolean bl) {
        if (!this.connected) throw new IllegalStateException("No layered protocol unless connected.");
        this.layered = RouteInfo.LayerType.LAYERED;
        this.secure = bl;
    }

    public final HttpRoute toRoute() {
        if (this.connected) return new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
        return null;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getHopCount() * 30 + 50);
        stringBuilder.append("RouteTracker[");
        HttpHost[] arrhttpHost = this.localAddress;
        if (arrhttpHost != null) {
            stringBuilder.append(arrhttpHost);
            stringBuilder.append("->");
        }
        stringBuilder.append('{');
        if (this.connected) {
            stringBuilder.append('c');
        }
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
        if (this.proxyChain != null) {
            for (int i = 0; i < (arrhttpHost = this.proxyChain).length; ++i) {
                stringBuilder.append(arrhttpHost[i]);
                stringBuilder.append("->");
            }
        }
        stringBuilder.append(this.targetHost);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public final void tunnelProxy(HttpHost httpHost, boolean bl) {
        if (httpHost == null) throw new IllegalArgumentException("Proxy host may not be null.");
        if (!this.connected) throw new IllegalStateException("No tunnel unless connected.");
        HttpHost[] arrhttpHost = this.proxyChain;
        if (arrhttpHost == null) throw new IllegalStateException("No proxy tunnel without proxy.");
        int n = arrhttpHost.length + 1;
        HttpHost[] arrhttpHost2 = new HttpHost[n];
        System.arraycopy(arrhttpHost, 0, arrhttpHost2, 0, arrhttpHost.length);
        arrhttpHost2[n - 1] = httpHost;
        this.proxyChain = arrhttpHost2;
        this.secure = bl;
    }

    public final void tunnelTarget(boolean bl) {
        if (!this.connected) throw new IllegalStateException("No tunnel unless connected.");
        if (this.proxyChain == null) throw new IllegalStateException("No tunnel without proxy.");
        this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
        this.secure = bl;
    }
}

