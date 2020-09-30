/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class ProxySelectorRoutePlanner
implements HttpRoutePlanner {
    protected ProxySelector proxySelector;
    protected final SchemeRegistry schemeRegistry;

    public ProxySelectorRoutePlanner(SchemeRegistry schemeRegistry, ProxySelector proxySelector) {
        if (schemeRegistry == null) throw new IllegalArgumentException("SchemeRegistry must not be null.");
        this.schemeRegistry = schemeRegistry;
        this.proxySelector = proxySelector;
    }

    protected Proxy chooseProxy(List<Proxy> object, HttpHost object2, HttpRequest object3, HttpContext httpContext) {
        if (object == null) throw new IllegalArgumentException("Proxy list must not be empty.");
        if (object.isEmpty()) throw new IllegalArgumentException("Proxy list must not be empty.");
        object2 = null;
        for (int i = 0; object2 == null && i < object.size(); ++i) {
            object3 = (Proxy)object.get(i);
            int n = 1.$SwitchMap$java$net$Proxy$Type[((Proxy)object3).type().ordinal()];
            if (n != 1 && n != 2) continue;
            object2 = object3;
        }
        object = object2;
        if (object2 != null) return object;
        return Proxy.NO_PROXY;
    }

    protected HttpHost determineProxy(HttpHost object, HttpRequest object2, HttpContext object3) throws HttpException {
        ProxySelector proxySelector;
        ProxySelector proxySelector2 = proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector2 = ProxySelector.getDefault();
        }
        proxySelector = null;
        if (proxySelector2 == null) {
            return null;
        }
        try {
            URI uRI = new URI(((HttpHost)object).toURI());
            object2 = this.chooseProxy(proxySelector2.select(uRI), (HttpHost)object, (HttpRequest)object2, (HttpContext)object3);
            object = proxySelector;
            if (((Proxy)object2).type() != Proxy.Type.HTTP) return object;
        }
        catch (URISyntaxException uRISyntaxException) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Cannot convert host to URI: ");
            ((StringBuilder)object3).append(object);
            throw new HttpException(((StringBuilder)object3).toString(), uRISyntaxException);
        }
        if (((Proxy)object2).address() instanceof InetSocketAddress) {
            object = (InetSocketAddress)((Proxy)object2).address();
            return new HttpHost(this.getHost((InetSocketAddress)object), ((InetSocketAddress)object).getPort());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to handle non-Inet proxy address: ");
        ((StringBuilder)object).append(((Proxy)object2).address());
        throw new HttpException(((StringBuilder)object).toString());
    }

    @Override
    public HttpRoute determineRoute(HttpHost cloneable, HttpRequest object, HttpContext httpContext) throws HttpException {
        if (object == null) throw new IllegalStateException("Request must not be null.");
        Object object2 = ConnRouteParams.getForcedRoute(object.getParams());
        if (object2 != null) {
            return object2;
        }
        if (cloneable == null) throw new IllegalStateException("Target host must not be null.");
        object2 = ConnRouteParams.getLocalAddress(object.getParams());
        object = this.determineProxy((HttpHost)cloneable, (HttpRequest)object, httpContext);
        boolean bl = this.schemeRegistry.getScheme(cloneable.getSchemeName()).isLayered();
        if (object != null) return new HttpRoute((HttpHost)cloneable, (InetAddress)object2, (HttpHost)object, bl);
        return new HttpRoute((HttpHost)cloneable, (InetAddress)object2, bl);
    }

    protected String getHost(InetSocketAddress object) {
        if (!((InetSocketAddress)object).isUnresolved()) return ((InetSocketAddress)object).getAddress().getHostAddress();
        return ((InetSocketAddress)object).getHostName();
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }

}

