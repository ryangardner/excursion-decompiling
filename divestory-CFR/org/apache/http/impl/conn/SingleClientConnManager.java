/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.AbstractPooledConnAdapter;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;

public class SingleClientConnManager
implements ClientConnectionManager {
    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected final boolean alwaysShutDown;
    protected final ClientConnectionOperator connOperator;
    protected long connectionExpiresTime;
    protected volatile boolean isShutDown;
    protected long lastReleaseTime;
    private final Log log = LogFactory.getLog(this.getClass());
    protected ConnAdapter managedConn;
    protected final SchemeRegistry schemeRegistry;
    protected PoolEntry uniquePoolEntry;

    public SingleClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public SingleClientConnManager(SchemeRegistry schemeRegistry) {
        if (schemeRegistry == null) throw new IllegalArgumentException("Scheme registry must not be null.");
        this.schemeRegistry = schemeRegistry;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.uniquePoolEntry = new PoolEntry();
        this.managedConn = null;
        this.lastReleaseTime = -1L;
        this.alwaysShutDown = false;
        this.isShutDown = false;
    }

    @Deprecated
    public SingleClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        this(schemeRegistry);
    }

    protected final void assertStillUp() throws IllegalStateException {
        if (this.isShutDown) throw new IllegalStateException("Manager is shut down.");
    }

    @Override
    public void closeExpiredConnections() {
        synchronized (this) {
            if (System.currentTimeMillis() < this.connectionExpiresTime) return;
            this.closeIdleConnections(0L, TimeUnit.MILLISECONDS);
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void closeIdleConnections(long l, TimeUnit object) {
        synchronized (this) {
            this.assertStillUp();
            if (object == null) {
                object = new IllegalArgumentException("Time unit must not be null.");
                throw object;
            }
            if (this.managedConn != null) return;
            if (!this.uniquePoolEntry.connection.isOpen()) return;
            long l2 = System.currentTimeMillis();
            long l3 = ((TimeUnit)((Object)object)).toMillis(l);
            l = this.lastReleaseTime;
            if (l > l2 - l3) return;
            try {
                this.uniquePoolEntry.close();
            }
            catch (IOException iOException) {
                this.log.debug((Object)"Problem closing idle connection.", (Throwable)iOException);
            }
            return;
        }
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    protected void finalize() throws Throwable {
        try {
            this.shutdown();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public ManagedClientConnection getConnection(HttpRoute var1_1, Object var2_3) {
        // MONITORENTER : this
        if (var1_1 == null) ** GOTO lbl41
        this.assertStillUp();
        if (this.log.isDebugEnabled()) {
            var2_3 = this.log;
            var3_5 = new StringBuilder();
            var3_5.append("Get connection for route ");
            var3_5.append(var1_1);
            var2_3.debug((Object)var3_5.toString());
        }
        if (this.managedConn != null) {
            var1_1 = new IllegalStateException("Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
            throw var1_1;
        }
        this.closeExpiredConnections();
        var4_6 = this.uniquePoolEntry.connection.isOpen();
        var5_7 = true;
        var6_8 = false;
        if (var4_6) {
            var2_3 = this.uniquePoolEntry.tracker;
            var7_9 = var2_3 == null || !(var4_6 = var2_3.toRoute().equals(var1_1));
            var8_10 = false;
            var6_8 = var7_9;
            var7_9 = var8_10;
        } else {
            var7_9 = true;
        }
        if (var6_8) {
            try {
                this.uniquePoolEntry.shutdown();
                var7_9 = var5_7;
            }
            catch (IOException var2_4) {
                this.log.debug((Object)"Problem shutting down connection.", (Throwable)var2_4);
                var7_9 = var5_7;
            }
        }
        if (var7_9) {
            this.uniquePoolEntry = var2_3 = new PoolEntry();
        }
        this.managedConn = var2_3 = new ConnAdapter(this.uniquePoolEntry, (HttpRoute)var1_1);
        // MONITOREXIT : this
        return var2_3;
lbl41: // 1 sources:
        var1_1 = new IllegalArgumentException("Route may not be null.");
        throw var1_1;
    }

    @Override
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void releaseConnection(ManagedClientConnection var1_1, long var2_2, TimeUnit var4_3) {
        block21 : {
            block19 : {
                // MONITORENTER : this
                this.assertStillUp();
                if (!(var1_1 instanceof ConnAdapter)) {
                    var1_1 = new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
                    throw var1_1;
                }
                if (this.log.isDebugEnabled()) {
                    var5_4 = this.log;
                    var6_5 = new StringBuilder();
                    var6_5.append("Releasing connection ");
                    var6_5.append(var1_1);
                    var5_4.debug((Object)var6_5.toString());
                }
                var1_1 = (ConnAdapter)var1_1;
                var6_5 = var1_1.poolEntry;
                if (var6_5 == null) {
                    // MONITOREXIT : this
                    return;
                }
                var6_5 = var1_1.getManager();
                if (var6_5 != null && var6_5 != this) {
                    var1_1 = new IllegalArgumentException("Connection not obtained from this manager.");
                    throw var1_1;
                }
                if (!var1_1.isOpen() || !this.alwaysShutDown && var1_1.isMarkedReusable()) break block19;
                if (this.log.isDebugEnabled()) {
                    this.log.debug((Object)"Released connection open but not reusable.");
                }
                var1_1.shutdown();
            }
            var1_1.detach();
            this.managedConn = null;
            this.lastReleaseTime = System.currentTimeMillis();
            if (var2_2 > 0L) {
                var7_8 = var4_3.toMillis(var2_2);
                var2_2 = this.lastReleaseTime;
lbl35: // 2 sources:
                do {
                    this.connectionExpiresTime = var7_8 + var2_2;
                    return;
                    break;
                } while (true);
            }
            break block21;
            {
                block20 : {
                    catch (Throwable var6_6) {
                    }
                    catch (IOException var6_7) {}
                    {
                        if (!this.log.isDebugEnabled()) break block20;
                        this.log.debug((Object)"Exception shutting down released connection.", (Throwable)var6_7);
                    }
                }
                var1_1.detach();
                this.managedConn = null;
                this.lastReleaseTime = System.currentTimeMillis();
                if (var2_2 > 0L) {
                    var7_8 = var4_3.toMillis(var2_2);
                    var2_2 = this.lastReleaseTime;
                    ** continue;
                }
                break block21;
            }
            var1_1.detach();
            this.managedConn = null;
            this.lastReleaseTime = System.currentTimeMillis();
            if (var2_2 > 0L) {
                this.connectionExpiresTime = var4_3.toMillis(var2_2) + this.lastReleaseTime;
                throw var6_6;
            }
            this.connectionExpiresTime = Long.MAX_VALUE;
            throw var6_6;
        }
        this.connectionExpiresTime = Long.MAX_VALUE;
    }

    @Override
    public final ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object object) {
        return new ClientConnectionRequest(){

            @Override
            public void abortRequest() {
            }

            @Override
            public ManagedClientConnection getConnection(long l, TimeUnit timeUnit) {
                return SingleClientConnManager.this.getConnection(httpRoute, object);
            }
        };
    }

    @Deprecated
    protected void revokeConnection() {
        synchronized (this) {
            ConnAdapter connAdapter = this.managedConn;
            if (connAdapter == null) {
                return;
            }
            this.managedConn.detach();
            try {
                this.uniquePoolEntry.shutdown();
            }
            catch (IOException iOException) {
                this.log.debug((Object)"Problem while shutting down connection.", (Throwable)iOException);
            }
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void shutdown() {
        // MONITORENTER : this
        this.isShutDown = true;
        if (this.managedConn != null) {
            this.managedConn.detach();
        }
        if (this.uniquePoolEntry != null) {
            this.uniquePoolEntry.shutdown();
        }
        this.uniquePoolEntry = null;
        return;
        {
            catch (IOException var1_2) {}
            {
                this.log.debug((Object)"Problem while shutting down manager.", (Throwable)var1_2);
            }
        }
        ** finally { 
lbl13: // 1 sources:
        this.uniquePoolEntry = null;
        throw var1_1;
    }

    protected class ConnAdapter
    extends AbstractPooledConnAdapter {
        protected ConnAdapter(PoolEntry poolEntry, HttpRoute httpRoute) {
            super(SingleClientConnManager.this, poolEntry);
            this.markReusable();
            poolEntry.route = httpRoute;
        }
    }

    protected class PoolEntry
    extends AbstractPoolEntry {
        protected PoolEntry() {
            super(SingleClientConnManager.this.connOperator, null);
        }

        protected void close() throws IOException {
            this.shutdownEntry();
            if (!this.connection.isOpen()) return;
            this.connection.close();
        }

        protected void shutdown() throws IOException {
            this.shutdownEntry();
            if (!this.connection.isOpen()) return;
            this.connection.shutdown();
        }
    }

}

