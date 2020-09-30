/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
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

public class DefaultHttpRoutePlanner
implements HttpRoutePlanner {
    protected final SchemeRegistry schemeRegistry;

    public DefaultHttpRoutePlanner(SchemeRegistry schemeRegistry) {
        if (schemeRegistry == null) throw new IllegalArgumentException("SchemeRegistry must not be null.");
        this.schemeRegistry = schemeRegistry;
    }

    @Override
    public HttpRoute determineRoute(HttpHost cloneable, HttpRequest object, HttpContext object2) throws HttpException {
        if (object == null) throw new IllegalStateException("Request must not be null.");
        object2 = ConnRouteParams.getForcedRoute(object.getParams());
        if (object2 != null) {
            return object2;
        }
        if (cloneable == null) throw new IllegalStateException("Target host must not be null.");
        object2 = ConnRouteParams.getLocalAddress(object.getParams());
        HttpHost httpHost = ConnRouteParams.getDefaultProxy(object.getParams());
        try {
            object = this.schemeRegistry.getScheme(cloneable.getSchemeName());
        }
        catch (IllegalStateException illegalStateException) {
            throw new HttpException(illegalStateException.getMessage());
        }
        boolean bl = ((Scheme)object).isLayered();
        if (httpHost != null) return new HttpRoute((HttpHost)cloneable, (InetAddress)object2, httpHost, bl);
        return new HttpRoute((HttpHost)cloneable, (InetAddress)object2, bl);
    }
}

