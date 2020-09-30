/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractPoolEntry {
    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected volatile HttpRoute route;
    protected volatile Object state;
    protected volatile RouteTracker tracker;

    protected AbstractPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        if (clientConnectionOperator == null) throw new IllegalArgumentException("Connection operator may not be null");
        this.connOperator = clientConnectionOperator;
        this.connection = clientConnectionOperator.createConnection();
        this.route = httpRoute;
        this.tracker = null;
    }

    public Object getState() {
        return this.state;
    }

    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (this.tracker == null) throw new IllegalStateException("Connection not open.");
        if (!this.tracker.isConnected()) throw new IllegalStateException("Connection not open.");
        if (!this.tracker.isTunnelled()) throw new IllegalStateException("Protocol layering without a tunnel not supported.");
        if (this.tracker.isLayered()) throw new IllegalStateException("Multiple protocol layering not supported.");
        HttpHost httpHost = this.tracker.getTargetHost();
        this.connOperator.updateSecureConnection(this.connection, httpHost, httpContext, httpParams);
        this.tracker.layerProtocol(this.connection.isSecure());
    }

    public void open(HttpRoute routeInfo, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (routeInfo == null) throw new IllegalArgumentException("Route must not be null.");
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (this.tracker != null) {
            if (this.tracker.isConnected()) throw new IllegalStateException("Connection already open.");
        }
        this.tracker = new RouteTracker((HttpRoute)routeInfo);
        HttpHost httpHost = ((HttpRoute)routeInfo).getProxyHost();
        ClientConnectionOperator clientConnectionOperator = this.connOperator;
        OperatedClientConnection operatedClientConnection = this.connection;
        HttpHost httpHost2 = httpHost != null ? httpHost : ((HttpRoute)routeInfo).getTargetHost();
        clientConnectionOperator.openConnection(operatedClientConnection, httpHost2, ((HttpRoute)routeInfo).getLocalAddress(), httpContext, httpParams);
        routeInfo = this.tracker;
        if (routeInfo == null) throw new IOException("Request aborted");
        if (httpHost == null) {
            ((RouteTracker)routeInfo).connectTarget(this.connection.isSecure());
            return;
        }
        ((RouteTracker)routeInfo).connectProxy(httpHost, this.connection.isSecure());
    }

    public void setState(Object object) {
        this.state = object;
    }

    protected void shutdownEntry() {
        this.tracker = null;
        this.state = null;
    }

    public void tunnelProxy(HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        if (httpHost == null) throw new IllegalArgumentException("Next proxy must not be null.");
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (this.tracker == null) throw new IllegalStateException("Connection not open.");
        if (!this.tracker.isConnected()) throw new IllegalStateException("Connection not open.");
        this.connection.update(null, httpHost, bl, httpParams);
        this.tracker.tunnelProxy(httpHost, bl);
    }

    public void tunnelTarget(boolean bl, HttpParams httpParams) throws IOException {
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (this.tracker == null) throw new IllegalStateException("Connection not open.");
        if (!this.tracker.isConnected()) throw new IllegalStateException("Connection not open.");
        if (this.tracker.isTunnelled()) throw new IllegalStateException("Connection is already tunnelled.");
        this.connection.update(null, this.tracker.getTargetHost(), bl, httpParams);
        this.tracker.tunnelTarget(bl);
    }
}

