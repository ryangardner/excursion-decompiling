/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.impl.conn.AbstractClientConnAdapter;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractPooledConnAdapter
extends AbstractClientConnAdapter {
    protected volatile AbstractPoolEntry poolEntry;

    protected AbstractPooledConnAdapter(ClientConnectionManager clientConnectionManager, AbstractPoolEntry abstractPoolEntry) {
        super(clientConnectionManager, abstractPoolEntry.connection);
        this.poolEntry = abstractPoolEntry;
    }

    @Deprecated
    protected final void assertAttached() {
        if (this.poolEntry == null) throw new ConnectionShutdownException();
    }

    protected void assertValid(AbstractPoolEntry abstractPoolEntry) {
        if (this.isReleased()) throw new ConnectionShutdownException();
        if (abstractPoolEntry == null) throw new ConnectionShutdownException();
    }

    @Override
    public void close() throws IOException {
        Object object = this.getPoolEntry();
        if (object != null) {
            ((AbstractPoolEntry)object).shutdownEntry();
        }
        if ((object = this.getWrappedConnection()) == null) return;
        object.close();
    }

    @Override
    protected void detach() {
        synchronized (this) {
            this.poolEntry = null;
            super.detach();
            return;
        }
    }

    protected AbstractPoolEntry getPoolEntry() {
        return this.poolEntry;
    }

    @Override
    public HttpRoute getRoute() {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        if (abstractPoolEntry.tracker != null) return abstractPoolEntry.tracker.toRoute();
        return null;
    }

    @Override
    public Object getState() {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        return abstractPoolEntry.getState();
    }

    @Override
    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.layerProtocol(httpContext, httpParams);
    }

    @Override
    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.open(httpRoute, httpContext, httpParams);
    }

    @Override
    public void setState(Object object) {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.setState(object);
    }

    @Override
    public void shutdown() throws IOException {
        Object object = this.getPoolEntry();
        if (object != null) {
            ((AbstractPoolEntry)object).shutdownEntry();
        }
        if ((object = this.getWrappedConnection()) == null) return;
        object.shutdown();
    }

    @Override
    public void tunnelProxy(HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.tunnelProxy(httpHost, bl, httpParams);
    }

    @Override
    public void tunnelTarget(boolean bl, HttpParams httpParams) throws IOException {
        AbstractPoolEntry abstractPoolEntry = this.getPoolEntry();
        this.assertValid(abstractPoolEntry);
        abstractPoolEntry.tunnelTarget(bl, httpParams);
    }
}

