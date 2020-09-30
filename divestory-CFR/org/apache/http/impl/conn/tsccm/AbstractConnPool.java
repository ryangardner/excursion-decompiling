/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.IdleConnectionHandler;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPoolEntryRef;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.impl.conn.tsccm.RefQueueHandler;

@Deprecated
public abstract class AbstractConnPool
implements RefQueueHandler {
    protected IdleConnectionHandler idleConnHandler = new IdleConnectionHandler();
    protected volatile boolean isShutDown;
    protected Set<BasicPoolEntryRef> issuedConnections;
    protected Set<BasicPoolEntry> leasedConnections = new HashSet<BasicPoolEntry>();
    private final Log log = LogFactory.getLog(this.getClass());
    protected int numConnections;
    protected final Lock poolLock = new ReentrantLock();
    protected ReferenceQueue<Object> refQueue;

    protected AbstractConnPool() {
    }

    protected void closeConnection(OperatedClientConnection operatedClientConnection) {
        if (operatedClientConnection == null) return;
        try {
            operatedClientConnection.close();
            return;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
        }
    }

    public void closeExpiredConnections() {
        this.poolLock.lock();
        try {
            this.idleConnHandler.closeExpiredConnections();
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        if (timeUnit == null) throw new IllegalArgumentException("Time unit must not be null.");
        this.poolLock.lock();
        try {
            this.idleConnHandler.closeIdleConnections(timeUnit.toMillis(l));
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    public abstract void deleteClosedConnections();

    public void enableConnectionGC() throws IllegalStateException {
    }

    public abstract void freeEntry(BasicPoolEntry var1, boolean var2, long var3, TimeUnit var5);

    public final BasicPoolEntry getEntry(HttpRoute httpRoute, Object object, long l, TimeUnit timeUnit) throws ConnectionPoolTimeoutException, InterruptedException {
        return this.requestPoolEntry(httpRoute, object).getPoolEntry(l, timeUnit);
    }

    protected abstract void handleLostEntry(HttpRoute var1);

    @Override
    public void handleReference(Reference<?> reference) {
    }

    public abstract PoolEntryRequest requestPoolEntry(HttpRoute var1, Object var2);

    public void shutdown() {
        this.poolLock.lock();
        boolean bl = this.isShutDown;
        if (bl) {
            this.poolLock.unlock();
            return;
        }
        Iterator<BasicPoolEntry> iterator2 = this.leasedConnections.iterator();
        while (iterator2.hasNext()) {
            BasicPoolEntry basicPoolEntry = iterator2.next();
            iterator2.remove();
            this.closeConnection(basicPoolEntry.getConnection());
        }
        this.idleConnHandler.removeAll();
        this.isShutDown = true;
        return;
    }
}

