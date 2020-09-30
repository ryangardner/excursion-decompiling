/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.apache.http.util.LangUtils;

public class RouteSpecificPool {
    protected final ConnPerRoute connPerRoute;
    protected final LinkedList<BasicPoolEntry> freeEntries;
    private final Log log = LogFactory.getLog(this.getClass());
    @Deprecated
    protected final int maxEntries;
    protected int numEntries;
    protected final HttpRoute route;
    protected final Queue<WaitingThread> waitingThreads;

    @Deprecated
    public RouteSpecificPool(HttpRoute httpRoute, int n) {
        this.route = httpRoute;
        this.maxEntries = n;
        this.connPerRoute = new ConnPerRoute(){

            @Override
            public int getMaxForRoute(HttpRoute httpRoute) {
                return RouteSpecificPool.this.maxEntries;
            }
        };
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList<WaitingThread>();
        this.numEntries = 0;
    }

    public RouteSpecificPool(HttpRoute httpRoute, ConnPerRoute connPerRoute) {
        this.route = httpRoute;
        this.connPerRoute = connPerRoute;
        this.maxEntries = connPerRoute.getMaxForRoute(httpRoute);
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList<WaitingThread>();
        this.numEntries = 0;
    }

    public BasicPoolEntry allocEntry(Object object) {
        Object object2;
        if (!this.freeEntries.isEmpty()) {
            object2 = this.freeEntries;
            object2 = ((LinkedList)object2).listIterator(((LinkedList)object2).size());
            while (object2.hasPrevious()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)object2.previous();
                if (basicPoolEntry.getState() != null && !LangUtils.equals(object, basicPoolEntry.getState())) continue;
                object2.remove();
                return basicPoolEntry;
            }
        }
        if (this.getCapacity() != 0) return null;
        if (this.freeEntries.isEmpty()) return null;
        object = this.freeEntries.remove();
        ((BasicPoolEntry)object).shutdownEntry();
        object2 = ((BasicPoolEntry)object).getConnection();
        try {
            object2.close();
            return object;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
        }
        return object;
    }

    public void createdEntry(BasicPoolEntry basicPoolEntry) {
        if (this.route.equals(basicPoolEntry.getPlannedRoute())) {
            ++this.numEntries;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entry not planned for this pool.\npool: ");
        stringBuilder.append(this.route);
        stringBuilder.append("\nplan: ");
        stringBuilder.append(basicPoolEntry.getPlannedRoute());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean deleteEntry(BasicPoolEntry basicPoolEntry) {
        boolean bl = this.freeEntries.remove(basicPoolEntry);
        if (!bl) return bl;
        --this.numEntries;
        return bl;
    }

    public void dropEntry() {
        int n = this.numEntries;
        if (n < 1) throw new IllegalStateException("There is no entry that could be dropped.");
        this.numEntries = n - 1;
    }

    public void freeEntry(BasicPoolEntry object) {
        int n = this.numEntries;
        if (n < 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No entry created for this pool. ");
            ((StringBuilder)object).append(this.route);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (n > this.freeEntries.size()) {
            this.freeEntries.add((BasicPoolEntry)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No entry allocated from this pool. ");
        ((StringBuilder)object).append(this.route);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public int getCapacity() {
        return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
    }

    public final int getEntryCount() {
        return this.numEntries;
    }

    public final int getMaxEntries() {
        return this.maxEntries;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }

    public boolean hasThread() {
        return this.waitingThreads.isEmpty() ^ true;
    }

    public boolean isUnused() {
        int n = this.numEntries;
        boolean bl = true;
        if (n >= 1) return false;
        if (!this.waitingThreads.isEmpty()) return false;
        return bl;
    }

    public WaitingThread nextThread() {
        return this.waitingThreads.peek();
    }

    public void queueThread(WaitingThread waitingThread) {
        if (waitingThread == null) throw new IllegalArgumentException("Waiting thread must not be null.");
        this.waitingThreads.add(waitingThread);
    }

    public void removeThread(WaitingThread waitingThread) {
        if (waitingThread == null) {
            return;
        }
        this.waitingThreads.remove(waitingThread);
    }

}

