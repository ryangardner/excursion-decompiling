/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPoolEntryRef;

public class BasicPoolEntry
extends AbstractPoolEntry {
    private final long created;
    private long expiry;
    private long updated;
    private long validUntil;

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        this(clientConnectionOperator, httpRoute, -1L, TimeUnit.MILLISECONDS);
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, long l, TimeUnit timeUnit) {
        super(clientConnectionOperator, httpRoute);
        long l2;
        if (httpRoute == null) throw new IllegalArgumentException("HTTP route may not be null");
        this.created = l2 = System.currentTimeMillis();
        this.validUntil = l > 0L ? l2 + timeUnit.toMillis(l) : Long.MAX_VALUE;
        this.expiry = this.validUntil;
    }

    @Deprecated
    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, ReferenceQueue<Object> referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        if (httpRoute == null) throw new IllegalArgumentException("HTTP route may not be null");
        this.created = System.currentTimeMillis();
        this.validUntil = Long.MAX_VALUE;
        this.expiry = Long.MAX_VALUE;
    }

    protected final OperatedClientConnection getConnection() {
        return this.connection;
    }

    public long getCreated() {
        return this.created;
    }

    public long getExpiry() {
        return this.expiry;
    }

    protected final HttpRoute getPlannedRoute() {
        return this.route;
    }

    public long getUpdated() {
        return this.updated;
    }

    public long getValidUntil() {
        return this.validUntil;
    }

    @Deprecated
    protected final BasicPoolEntryRef getWeakRef() {
        return null;
    }

    public boolean isExpired(long l) {
        if (l < this.expiry) return false;
        return true;
    }

    @Override
    protected void shutdownEntry() {
        super.shutdownEntry();
    }

    public void updateExpiry(long l, TimeUnit timeUnit) {
        long l2;
        this.updated = l2 = System.currentTimeMillis();
        l = l > 0L ? l2 + timeUnit.toMillis(l) : Long.MAX_VALUE;
        this.expiry = Math.min(this.validUntil, l);
    }
}

