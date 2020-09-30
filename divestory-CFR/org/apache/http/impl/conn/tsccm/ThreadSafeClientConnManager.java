/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPooledConnAdapter;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.params.HttpParams;

public class ThreadSafeClientConnManager
implements ClientConnectionManager {
    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;
    @Deprecated
    protected final AbstractConnPool connectionPool;
    private final Log log;
    protected final ConnPoolByRoute pool;
    protected final SchemeRegistry schemeRegistry;

    public ThreadSafeClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }

    public ThreadSafeClientConnManager(SchemeRegistry object, long l, TimeUnit timeUnit) {
        if (object == null) throw new IllegalArgumentException("Scheme registry may not be null");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = object;
        this.connPerRoute = new ConnPerRouteBean();
        this.connOperator = this.createConnectionOperator((SchemeRegistry)object);
        this.pool = object = this.createConnectionPool(l, timeUnit);
        this.connectionPool = object;
    }

    @Deprecated
    public ThreadSafeClientConnManager(HttpParams object, SchemeRegistry schemeRegistry) {
        if (schemeRegistry == null) throw new IllegalArgumentException("Scheme registry may not be null");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = schemeRegistry;
        this.connPerRoute = new ConnPerRouteBean();
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.pool = object = (ConnPoolByRoute)this.createConnectionPool((HttpParams)object);
        this.connectionPool = object;
    }

    @Override
    public void closeExpiredConnections() {
        this.log.debug((Object)"Closing expired connections");
        this.pool.closeExpiredConnections();
    }

    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Closing connections idle longer than ");
            stringBuilder.append(l);
            stringBuilder.append(" ");
            stringBuilder.append((Object)timeUnit);
            log.debug((Object)stringBuilder.toString());
        }
        this.pool.closeIdleConnections(l, timeUnit);
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    @Deprecated
    protected AbstractConnPool createConnectionPool(HttpParams httpParams) {
        return new ConnPoolByRoute(this.connOperator, httpParams);
    }

    protected ConnPoolByRoute createConnectionPool(long l, TimeUnit timeUnit) {
        return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, l, timeUnit);
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

    public int getConnectionsInPool() {
        return this.pool.getConnectionsInPool();
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        return this.pool.getConnectionsInPool(httpRoute);
    }

    public int getDefaultMaxPerRoute() {
        return this.connPerRoute.getDefaultMaxPerRoute();
    }

    public int getMaxForRoute(HttpRoute httpRoute) {
        return this.connPerRoute.getMaxForRoute(httpRoute);
    }

    public int getMaxTotal() {
        return this.pool.getMaxTotalConnections();
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
    public void releaseConnection(ManagedClientConnection var1_1, long var2_4, TimeUnit var4_5) {
        block20 : {
            if (var1_1 instanceof BasicPooledConnAdapter == false) throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
            var5_6 = (BasicPooledConnAdapter)var1_1;
            if (var5_6.getPoolEntry() != null) {
                if (var5_6.getManager() != this) throw new IllegalArgumentException("Connection not obtained from this manager.");
            }
            // MONITORENTER : var5_6
            var6_7 = (BasicPoolEntry)var5_6.getPoolEntry();
            if (var6_7 == null) {
                // MONITOREXIT : var5_6
                return;
            }
            if (!var5_6.isOpen() || var5_6.isMarkedReusable()) break block20;
            var5_6.shutdown();
        }
        var7_8 = var5_6.isMarkedReusable();
        if (this.log.isDebugEnabled()) {
            if (var7_8) {
                this.log.debug((Object)"Released connection is reusable.");
            } else {
                this.log.debug((Object)"Released connection is not reusable.");
            }
        }
        var5_6.detach();
        var1_1 = this.pool;
lbl22: // 2 sources:
        do {
            var1_1.freeEntry(var6_7, var7_8, var2_4, var4_5);
            return;
            break;
        } while (true);
        {
            block21 : {
                catch (Throwable var1_2) {
                }
                catch (IOException var1_3) {}
                {
                    if (!this.log.isDebugEnabled()) break block21;
                    this.log.debug((Object)"Exception shutting down released connection.", (Throwable)var1_3);
                }
            }
            var7_8 = var5_6.isMarkedReusable();
            if (this.log.isDebugEnabled()) {
                if (var7_8) {
                    this.log.debug((Object)"Released connection is reusable.");
                } else {
                    this.log.debug((Object)"Released connection is not reusable.");
                }
            }
            var5_6.detach();
            var1_1 = this.pool;
            ** continue;
        }
        var7_9 = var5_6.isMarkedReusable();
        if (this.log.isDebugEnabled()) {
            if (var7_9) {
                this.log.debug((Object)"Released connection is reusable.");
            } else {
                this.log.debug((Object)"Released connection is not reusable.");
            }
        }
        var5_6.detach();
        this.pool.freeEntry(var6_7, var7_9, var2_4, var4_5);
        throw var1_2;
    }

    @Override
    public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object object) {
        return new ClientConnectionRequest(this.pool.requestPoolEntry(httpRoute, object), httpRoute){
            final /* synthetic */ PoolEntryRequest val$poolRequest;
            final /* synthetic */ HttpRoute val$route;
            {
                this.val$poolRequest = poolEntryRequest;
                this.val$route = httpRoute;
            }

            @Override
            public void abortRequest() {
                this.val$poolRequest.abortRequest();
            }

            @Override
            public ManagedClientConnection getConnection(long l, TimeUnit object) throws InterruptedException, ConnectionPoolTimeoutException {
                if (this.val$route == null) throw new IllegalArgumentException("Route may not be null.");
                if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
                    Log log = ThreadSafeClientConnManager.this.log;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Get connection: ");
                    stringBuilder.append(this.val$route);
                    stringBuilder.append(", timeout = ");
                    stringBuilder.append(l);
                    log.debug((Object)stringBuilder.toString());
                }
                object = this.val$poolRequest.getPoolEntry(l, (TimeUnit)((Object)object));
                return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, (AbstractPoolEntry)object);
            }
        };
    }

    public void setDefaultMaxPerRoute(int n) {
        this.connPerRoute.setDefaultMaxPerRoute(n);
    }

    public void setMaxForRoute(HttpRoute httpRoute, int n) {
        this.connPerRoute.setMaxForRoute(httpRoute, n);
    }

    public void setMaxTotal(int n) {
        this.pool.setMaxTotalConnections(n);
    }

    @Override
    public void shutdown() {
        this.log.debug((Object)"Shutting down");
        this.pool.shutdown();
    }

}

