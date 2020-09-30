/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

@Metadata(bv={1, 0, 3}, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ0\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$H\u0002J8\u0010%\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\u0006\u0010&\u001a\u00020$H\u0002J\u0006\u0010'\u001a\u00020$J\n\u0010(\u001a\u0004\u0018\u00010\u0010H\u0002J\u000e\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2={"Lokhttp3/internal/connection/ExchangeFinder;", "", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;)V", "getAddress$okhttp", "()Lokhttp3/Address;", "connectionShutdownCount", "", "nextRouteToTry", "Lokhttp3/Route;", "otherFailureCount", "refusedStreamCount", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "findConnection", "Lokhttp3/internal/connection/RealConnection;", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "", "findHealthyConnection", "doExtensiveHealthChecks", "retryAfterFailure", "retryRoute", "sameHostAndPort", "url", "Lokhttp3/HttpUrl;", "trackFailure", "", "e", "Ljava/io/IOException;", "okhttp"}, k=1, mv={1, 1, 16})
public final class ExchangeFinder {
    private final Address address;
    private final RealCall call;
    private final RealConnectionPool connectionPool;
    private int connectionShutdownCount;
    private final EventListener eventListener;
    private Route nextRouteToTry;
    private int otherFailureCount;
    private int refusedStreamCount;
    private RouteSelector.Selection routeSelection;
    private RouteSelector routeSelector;

    public ExchangeFinder(RealConnectionPool realConnectionPool, Address address, RealCall realCall, EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(realConnectionPool, "connectionPool");
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(realCall, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        this.connectionPool = realConnectionPool;
        this.address = address;
        this.call = realCall;
        this.eventListener = eventListener;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private final RealConnection findConnection(int var1_1, int var2_2, int var3_3, int var4_4, boolean var5_5) throws IOException {
        block21 : {
            block22 : {
                block24 : {
                    block23 : {
                        if (this.call.isCanceled() != false) throw (Throwable)new IOException("Canceled");
                        var6_6 = this.call.getConnection();
                        var7_7 = 1;
                        if (var6_6 != null) {
                            var8_8 = null;
                            // MONITORENTER : var6_6
                            if (var6_6.getNoNewExchanges() || !this.sameHostAndPort(var6_6.route().address().url())) {
                                var8_8 = this.call.releaseConnectionNoEvents$okhttp();
                            }
                            var9_10 = Unit.INSTANCE;
                            // MONITOREXIT : var6_6
                            if (this.call.getConnection() != null) {
                                var1_1 = var8_8 == null ? var7_7 : 0;
                                if (var1_1 == 0) throw (Throwable)new IllegalStateException("Check failed.".toString());
                                return var6_6;
                            }
                            if (var8_8 != null) {
                                Util.closeQuietly((Socket)var8_8);
                            }
                            this.eventListener.connectionReleased(this.call, (Connection)var6_6);
                        }
                        this.refusedStreamCount = 0;
                        this.connectionShutdownCount = 0;
                        this.otherFailureCount = 0;
                        if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, null, false)) {
                            var8_8 = this.call.getConnection();
                            if (var8_8 == null) {
                                Intrinsics.throwNpe();
                            }
                            this.eventListener.connectionAcquired(this.call, (Connection)var8_8);
                            return var8_8;
                        }
                        var8_8 = this.nextRouteToTry;
                        if (var8_8 == null) break block23;
                        var6_6 = null;
                        if (var8_8 == null) {
                            Intrinsics.throwNpe();
                        }
                        this.nextRouteToTry = null;
                        break block24;
                    }
                    var8_8 = this.routeSelection;
                    if (var8_8 == null) ** GOTO lbl-1000
                    if (var8_8 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (var8_8.hasNext()) {
                        var6_6 = null;
                        var8_8 = this.routeSelection;
                        if (var8_8 == null) {
                            Intrinsics.throwNpe();
                        }
                        var8_8 = var8_8.next();
                    } else lbl-1000: // 2 sources:
                    {
                        var8_8 = var6_6 = this.routeSelector;
                        if (var6_6 == null) {
                            this.routeSelector = var8_8 = new RouteSelector(this.address, this.call.getClient().getRouteDatabase(), this.call, this.eventListener);
                        }
                        this.routeSelection = var8_8 = var8_8.next();
                        var6_6 = var8_8.getRoutes();
                        if (this.call.isCanceled() != false) throw (Throwable)new IOException("Canceled");
                        if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List<Route>)var6_6, false)) {
                            var8_8 = this.call.getConnection();
                            if (var8_8 == null) {
                                Intrinsics.throwNpe();
                            }
                            this.eventListener.connectionAcquired(this.call, (Connection)var8_8);
                            return var8_8;
                        }
                        var8_8 = var8_8.next();
                    }
                }
                var9_10 = new RealConnection(this.connectionPool, (Route)var8_8);
                this.call.setConnectionToCancel((RealConnection)var9_10);
                var9_10.connect(var1_1, var2_2, var3_3, var4_4, var5_5, this.call, this.eventListener);
                this.call.getClient().getRouteDatabase().connected(var9_10.route());
                if (!this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List<Route>)var6_6, true)) break block21;
                var6_6 = this.call.getConnection();
                if (var6_6 != null) break block22;
                Intrinsics.throwNpe();
            }
            this.nextRouteToTry = var8_8;
            Util.closeQuietly(var9_10.socket());
            this.eventListener.connectionAcquired(this.call, (Connection)var6_6);
            return var6_6;
        }
        // MONITORENTER : var9_10
        this.connectionPool.put((RealConnection)var9_10);
        this.call.acquireConnectionNoEvents((RealConnection)var9_10);
        var8_8 = Unit.INSTANCE;
        // MONITOREXIT : var9_10
        this.eventListener.connectionAcquired(this.call, (Connection)var9_10);
        return var9_10;
        finally {
            this.call.setConnectionToCancel(null);
        }
    }

    private final RealConnection findHealthyConnection(int n, int n2, int n3, int n4, boolean bl, boolean bl2) throws IOException {
        Object object;
        while (!((RealConnection)(object = this.findConnection(n, n2, n3, n4, bl))).isHealthy(bl2)) {
            ((RealConnection)object).noNewExchanges$okhttp();
            if (this.nextRouteToTry != null) continue;
            object = this.routeSelection;
            boolean bl3 = true;
            boolean bl4 = object != null ? ((RouteSelector.Selection)object).hasNext() : true;
            if (bl4) continue;
            object = this.routeSelector;
            bl4 = bl3;
            if (object != null) {
                bl4 = ((RouteSelector)object).hasNext();
            }
            if (!bl4) throw (Throwable)new IOException("exhausted all routes");
        }
        return object;
    }

    private final Route retryRoute() {
        if (this.refusedStreamCount > 1) return null;
        if (this.connectionShutdownCount > 1) return null;
        if (this.otherFailureCount > 0) {
            return null;
        }
        RealConnection realConnection = this.call.getConnection();
        if (realConnection == null) return null;
        synchronized (realConnection) {
            int n = realConnection.getRouteFailureCount$okhttp();
            if (n != 0) {
                return null;
            }
            boolean bl = Util.canReuseConnectionFor(realConnection.route().address().url(), this.address.url());
            if (!bl) {
                return null;
            }
            Route route = realConnection.route();
            return route;
        }
    }

    public final ExchangeCodec find(OkHttpClient object, RealInterceptorChain realInterceptorChain) {
        Intrinsics.checkParameterIsNotNull(object, "client");
        Intrinsics.checkParameterIsNotNull(realInterceptorChain, "chain");
        try {
            return this.findHealthyConnection(realInterceptorChain.getConnectTimeoutMillis$okhttp(), realInterceptorChain.getReadTimeoutMillis$okhttp(), realInterceptorChain.getWriteTimeoutMillis$okhttp(), ((OkHttpClient)object).pingIntervalMillis(), ((OkHttpClient)object).retryOnConnectionFailure(), Intrinsics.areEqual(realInterceptorChain.getRequest$okhttp().method(), "GET") ^ true).newCodec$okhttp((OkHttpClient)object, realInterceptorChain);
        }
        catch (IOException iOException) {
            this.trackFailure(iOException);
            throw (Throwable)new RouteException(iOException);
        }
        catch (RouteException routeException) {
            this.trackFailure(routeException.getLastConnectException());
            throw (Throwable)routeException;
        }
    }

    public final Address getAddress$okhttp() {
        return this.address;
    }

    public final boolean retryAfterFailure() {
        if (this.refusedStreamCount == 0 && this.connectionShutdownCount == 0 && this.otherFailureCount == 0) {
            return false;
        }
        if (this.nextRouteToTry != null) {
            return true;
        }
        Object object = this.retryRoute();
        if (object != null) {
            this.nextRouteToTry = object;
            return true;
        }
        object = this.routeSelection;
        if (object != null && ((RouteSelector.Selection)object).hasNext()) {
            return true;
        }
        object = this.routeSelector;
        if (object == null) return true;
        return ((RouteSelector)object).hasNext();
    }

    public final boolean sameHostAndPort(HttpUrl httpUrl) {
        Intrinsics.checkParameterIsNotNull(httpUrl, "url");
        HttpUrl httpUrl2 = this.address.url();
        if (httpUrl.port() != httpUrl2.port()) return false;
        if (!Intrinsics.areEqual(httpUrl.host(), httpUrl2.host())) return false;
        return true;
    }

    public final void trackFailure(IOException iOException) {
        Intrinsics.checkParameterIsNotNull(iOException, "e");
        this.nextRouteToTry = null;
        if (iOException instanceof StreamResetException && ((StreamResetException)iOException).errorCode == ErrorCode.REFUSED_STREAM) {
            ++this.refusedStreamCount;
            return;
        }
        if (iOException instanceof ConnectionShutdownException) {
            ++this.connectionShutdownCount;
            return;
        }
        ++this.otherFailureCount;
    }
}

