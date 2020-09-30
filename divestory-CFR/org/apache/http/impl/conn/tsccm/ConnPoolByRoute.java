/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.impl.conn.tsccm.RouteSpecificPool;
import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.apache.http.impl.conn.tsccm.WaitingThreadAborter;
import org.apache.http.params.HttpParams;

public class ConnPoolByRoute
extends AbstractConnPool {
    protected final ConnPerRoute connPerRoute;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected final Queue<BasicPoolEntry> freeConnections;
    protected final Set<BasicPoolEntry> leasedConnections;
    private final Log log = LogFactory.getLog(this.getClass());
    protected volatile int maxTotalConnections;
    protected volatile int numConnections;
    protected final ClientConnectionOperator operator;
    private final Lock poolLock;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    protected volatile boolean shutdown;
    protected final Queue<WaitingThread> waitingThreads;

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int n) {
        this(clientConnectionOperator, connPerRoute, n, -1L, TimeUnit.MILLISECONDS);
    }

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int n, long l, TimeUnit timeUnit) {
        if (clientConnectionOperator == null) throw new IllegalArgumentException("Connection operator may not be null");
        if (connPerRoute == null) throw new IllegalArgumentException("Connections per route may not be null");
        this.poolLock = ((AbstractConnPool)this).poolLock;
        this.leasedConnections = ((AbstractConnPool)this).leasedConnections;
        this.operator = clientConnectionOperator;
        this.connPerRoute = connPerRoute;
        this.maxTotalConnections = n;
        this.freeConnections = this.createFreeConnQueue();
        this.waitingThreads = this.createWaitingThreadQueue();
        this.routeToPool = this.createRouteToPoolMap();
        this.connTTL = l;
        this.connTTLTimeUnit = timeUnit;
    }

    @Deprecated
    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, HttpParams httpParams) {
        this(clientConnectionOperator, ConnManagerParams.getMaxConnectionsPerRoute(httpParams), ConnManagerParams.getMaxTotalConnections(httpParams));
    }

    private void closeConnection(BasicPoolEntry object) {
        if ((object = ((BasicPoolEntry)object).getConnection()) == null) return;
        try {
            object.close();
            return;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
        }
    }

    @Override
    public void closeExpiredConnections() {
        this.log.debug((Object)"Closing expired connections");
        long l = System.currentTimeMillis();
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)iterator2.next();
                if (!basicPoolEntry.isExpired(l)) continue;
                if (this.log.isDebugEnabled()) {
                    Log log = this.log;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Closing connection expired @ ");
                    Date date = new Date(basicPoolEntry.getExpiry());
                    stringBuilder.append(date);
                    log.debug((Object)stringBuilder.toString());
                }
                iterator2.remove();
                this.deleteEntry(basicPoolEntry);
            }
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public void closeIdleConnections(long l, TimeUnit object) {
        StringBuilder stringBuilder;
        Object object2;
        if (object == null) throw new IllegalArgumentException("Time unit must not be null.");
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        if (this.log.isDebugEnabled()) {
            object2 = this.log;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Closing connections idle longer than ");
            stringBuilder.append(l2);
            stringBuilder.append(" ");
            stringBuilder.append(object);
            object2.debug((Object)stringBuilder.toString());
        }
        l = System.currentTimeMillis();
        l2 = object.toMillis(l2);
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                object2 = (BasicPoolEntry)iterator2.next();
                if (((BasicPoolEntry)object2).getUpdated() > l - l2) continue;
                if (this.log.isDebugEnabled()) {
                    stringBuilder = this.log;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Closing connection last used @ ");
                    object = new Date(((BasicPoolEntry)object2).getUpdated());
                    stringBuilder2.append(object);
                    stringBuilder.debug((Object)stringBuilder2.toString());
                }
                iterator2.remove();
                this.deleteEntry((BasicPoolEntry)object2);
            }
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool routeSpecificPool, ClientConnectionOperator object) {
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Creating new connection [");
            stringBuilder.append(routeSpecificPool.getRoute());
            stringBuilder.append("]");
            log.debug((Object)stringBuilder.toString());
        }
        object = new BasicPoolEntry((ClientConnectionOperator)object, routeSpecificPool.getRoute(), this.connTTL, this.connTTLTimeUnit);
        this.poolLock.lock();
        try {
            routeSpecificPool.createdEntry((BasicPoolEntry)object);
            ++this.numConnections;
            this.leasedConnections.add((BasicPoolEntry)object);
            return object;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    protected Queue<BasicPoolEntry> createFreeConnQueue() {
        return new LinkedList<BasicPoolEntry>();
    }

    protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        return new HashMap<HttpRoute, RouteSpecificPool>();
    }

    protected Queue<WaitingThread> createWaitingThreadQueue() {
        return new LinkedList<WaitingThread>();
    }

    @Override
    public void deleteClosedConnections() {
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)iterator2.next();
                if (basicPoolEntry.getConnection().isOpen()) continue;
                iterator2.remove();
                this.deleteEntry(basicPoolEntry);
            }
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteEntry(BasicPoolEntry basicPoolEntry) {
        Object object;
        HttpRoute httpRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            object = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Deleting connection [");
            stringBuilder.append(httpRoute);
            stringBuilder.append("][");
            stringBuilder.append(basicPoolEntry.getState());
            stringBuilder.append("]");
            object.debug((Object)stringBuilder.toString());
        }
        this.poolLock.lock();
        try {
            this.closeConnection(basicPoolEntry);
            object = this.getRoutePool(httpRoute, true);
            ((RouteSpecificPool)object).deleteEntry(basicPoolEntry);
            --this.numConnections;
            if (!((RouteSpecificPool)object).isUnused()) return;
            this.routeToPool.remove(httpRoute);
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteLeastUsedEntry() {
        this.poolLock.lock();
        try {
            BasicPoolEntry basicPoolEntry = this.freeConnections.remove();
            if (basicPoolEntry != null) {
                this.deleteEntry(basicPoolEntry);
                return;
            }
            if (!this.log.isDebugEnabled()) return;
            this.log.debug((Object)"No free connection to delete");
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public void freeEntry(BasicPoolEntry basicPoolEntry, boolean bl, long l, TimeUnit timeUnit) {
        CharSequence charSequence;
        Object object;
        HttpRoute httpRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            object = this.log;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Releasing connection [");
            ((StringBuilder)charSequence).append(httpRoute);
            ((StringBuilder)charSequence).append("][");
            ((StringBuilder)charSequence).append(basicPoolEntry.getState());
            ((StringBuilder)charSequence).append("]");
            object.debug((Object)((StringBuilder)charSequence).toString());
        }
        this.poolLock.lock();
        try {
            if (this.shutdown) {
                this.closeConnection(basicPoolEntry);
                return;
            }
            this.leasedConnections.remove(basicPoolEntry);
            object = this.getRoutePool(httpRoute, true);
            if (bl) {
                if (this.log.isDebugEnabled()) {
                    if (l > 0L) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("for ");
                        ((StringBuilder)charSequence).append(l);
                        ((StringBuilder)charSequence).append(" ");
                        ((StringBuilder)charSequence).append((Object)timeUnit);
                        charSequence = ((StringBuilder)charSequence).toString();
                    } else {
                        charSequence = "indefinitely";
                    }
                    Log log = this.log;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Pooling connection [");
                    stringBuilder.append(httpRoute);
                    stringBuilder.append("][");
                    stringBuilder.append(basicPoolEntry.getState());
                    stringBuilder.append("]; keep alive ");
                    stringBuilder.append((String)charSequence);
                    log.debug((Object)stringBuilder.toString());
                }
                ((RouteSpecificPool)object).freeEntry(basicPoolEntry);
                basicPoolEntry.updateExpiry(l, timeUnit);
                this.freeConnections.add(basicPoolEntry);
            } else {
                ((RouteSpecificPool)object).dropEntry();
                --this.numConnections;
            }
            this.notifyWaitingThread((RouteSpecificPool)object);
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool() {
        this.poolLock.lock();
        try {
            int n = this.numConnections;
            return n;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool(HttpRoute object) {
        this.poolLock.lock();
        int n = 0;
        try {
            object = this.getRoutePool((HttpRoute)object, false);
            if (object == null) return n;
            n = ((RouteSpecificPool)object).getEntryCount();
            return n;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    /*
     * Exception decompiling
     */
    protected BasicPoolEntry getEntryBlocking(HttpRoute var1_1, Object var2_4, long var3_5, TimeUnit var5_6, WaitingThreadAborter var6_7) throws ConnectionPoolTimeoutException, InterruptedException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[UNCONDITIONALDOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool routeSpecificPool, Object object) {
        this.poolLock.lock();
        boolean bl = false;
        BasicPoolEntry basicPoolEntry = null;
        do {
            if (bl) {
                this.poolLock.unlock();
                return basicPoolEntry;
            }
            try {
                Log log;
                StringBuilder stringBuilder;
                basicPoolEntry = routeSpecificPool.allocEntry(object);
                if (basicPoolEntry != null) {
                    if (this.log.isDebugEnabled()) {
                        log = this.log;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Getting free connection [");
                        stringBuilder.append(routeSpecificPool.getRoute());
                        stringBuilder.append("][");
                        stringBuilder.append(object);
                        stringBuilder.append("]");
                        log.debug((Object)stringBuilder.toString());
                    }
                    this.freeConnections.remove(basicPoolEntry);
                    if (basicPoolEntry.isExpired(System.currentTimeMillis())) {
                        if (this.log.isDebugEnabled()) {
                            log = this.log;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Closing expired free connection [");
                            stringBuilder.append(routeSpecificPool.getRoute());
                            stringBuilder.append("][");
                            stringBuilder.append(object);
                            stringBuilder.append("]");
                            log.debug((Object)stringBuilder.toString());
                        }
                        this.closeConnection(basicPoolEntry);
                        routeSpecificPool.dropEntry();
                        --this.numConnections;
                        continue;
                    }
                    this.leasedConnections.add(basicPoolEntry);
                } else if (this.log.isDebugEnabled()) {
                    log = this.log;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("No free connections [");
                    stringBuilder.append(routeSpecificPool.getRoute());
                    stringBuilder.append("][");
                    stringBuilder.append(object);
                    stringBuilder.append("]");
                    log.debug((Object)stringBuilder.toString());
                }
                bl = true;
            }
            catch (Throwable throwable) {
                this.poolLock.unlock();
                throw throwable;
            }
        } while (true);
    }

    protected Lock getLock() {
        return this.poolLock;
    }

    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }

    protected RouteSpecificPool getRoutePool(HttpRoute httpRoute, boolean bl) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool;
            RouteSpecificPool routeSpecificPool2 = routeSpecificPool = this.routeToPool.get(httpRoute);
            if (routeSpecificPool != null) return routeSpecificPool2;
            routeSpecificPool2 = routeSpecificPool;
            if (!bl) return routeSpecificPool2;
            routeSpecificPool2 = this.newRouteSpecificPool(httpRoute);
            this.routeToPool.put(httpRoute, routeSpecificPool2);
            return routeSpecificPool2;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    protected void handleLostEntry(HttpRoute httpRoute) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, true);
            routeSpecificPool.dropEntry();
            if (routeSpecificPool.isUnused()) {
                this.routeToPool.remove(httpRoute);
            }
            --this.numConnections;
            this.notifyWaitingThread(routeSpecificPool);
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute httpRoute) {
        return new RouteSpecificPool(httpRoute, this.connPerRoute);
    }

    protected WaitingThread newWaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        return new WaitingThread(condition, routeSpecificPool);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    protected void notifyWaitingThread(RouteSpecificPool var1_1) {
        this.poolLock.lock();
        if (var1_1 == null) ** GOTO lbl-1000
        try {
            if (var1_1.hasThread()) {
                if (this.log.isDebugEnabled()) {
                    var2_3 = this.log;
                    var3_4 = new StringBuilder();
                    var3_4.append("Notifying thread waiting on pool [");
                    var3_4.append(var1_1.getRoute());
                    var3_4.append("]");
                    var2_3.debug((Object)var3_4.toString());
                }
                var1_1 = var1_1.nextThread();
            } else if (!this.waitingThreads.isEmpty()) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug((Object)"Notifying thread waiting on any pool");
                }
                var1_1 = this.waitingThreads.remove();
            } else {
                if (this.log.isDebugEnabled()) {
                    this.log.debug((Object)"Notifying no-one, there are no waiting threads");
                }
                var1_1 = null;
            }
            if (var1_1 == null) return;
            var1_1.wakeup();
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public PoolEntryRequest requestPoolEntry(HttpRoute httpRoute, Object object) {
        return new PoolEntryRequest(new WaitingThreadAborter(), httpRoute, object){
            final /* synthetic */ WaitingThreadAborter val$aborter;
            final /* synthetic */ HttpRoute val$route;
            final /* synthetic */ Object val$state;
            {
                this.val$aborter = waitingThreadAborter;
                this.val$route = httpRoute;
                this.val$state = object;
            }

            @Override
            public void abortRequest() {
                ConnPoolByRoute.this.poolLock.lock();
                try {
                    this.val$aborter.abort();
                    return;
                }
                finally {
                    ConnPoolByRoute.this.poolLock.unlock();
                }
            }

            @Override
            public BasicPoolEntry getPoolEntry(long l, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return ConnPoolByRoute.this.getEntryBlocking(this.val$route, this.val$state, l, timeUnit, this.val$aborter);
            }
        };
    }

    public void setMaxTotalConnections(int n) {
        this.poolLock.lock();
        try {
            this.maxTotalConnections = n;
            return;
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public void shutdown() {
        Object object;
        this.poolLock.lock();
        boolean bl = this.shutdown;
        if (bl) {
            this.poolLock.unlock();
            return;
        }
        this.shutdown = true;
        Object object2 = this.leasedConnections.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            object2.remove();
            this.closeConnection((BasicPoolEntry)object);
        }
        Iterator iterator2 = this.freeConnections.iterator();
        while (iterator2.hasNext()) {
            object = (BasicPoolEntry)iterator2.next();
            iterator2.remove();
            if (this.log.isDebugEnabled()) {
                Log log = this.log;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Closing connection [");
                ((StringBuilder)object2).append(((BasicPoolEntry)object).getPlannedRoute());
                ((StringBuilder)object2).append("][");
                ((StringBuilder)object2).append(((AbstractPoolEntry)object).getState());
                ((StringBuilder)object2).append("]");
                log.debug((Object)((StringBuilder)object2).toString());
            }
            this.closeConnection((BasicPoolEntry)object);
        }
        object2 = this.waitingThreads.iterator();
        while (object2.hasNext()) {
            object = (WaitingThread)((Object)object2.next());
            object2.remove();
            ((WaitingThread)object).wakeup();
        }
        this.routeToPool.clear();
        return;
    }

}

