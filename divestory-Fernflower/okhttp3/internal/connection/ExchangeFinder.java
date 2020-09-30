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
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ0\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$H\u0002J8\u0010%\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\u0006\u0010&\u001a\u00020$H\u0002J\u0006\u0010'\u001a\u00020$J\n\u0010(\u001a\u0004\u0018\u00010\u0010H\u0002J\u000e\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"},
   d2 = {"Lokhttp3/internal/connection/ExchangeFinder;", "", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;)V", "getAddress$okhttp", "()Lokhttp3/Address;", "connectionShutdownCount", "", "nextRouteToTry", "Lokhttp3/Route;", "otherFailureCount", "refusedStreamCount", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "findConnection", "Lokhttp3/internal/connection/RealConnection;", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "", "findHealthyConnection", "doExtensiveHealthChecks", "retryAfterFailure", "retryRoute", "sameHostAndPort", "url", "Lokhttp3/HttpUrl;", "trackFailure", "", "e", "Ljava/io/IOException;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
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

   public ExchangeFinder(RealConnectionPool var1, Address var2, RealCall var3, EventListener var4) {
      Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
      Intrinsics.checkParameterIsNotNull(var2, "address");
      Intrinsics.checkParameterIsNotNull(var3, "call");
      Intrinsics.checkParameterIsNotNull(var4, "eventListener");
      super();
      this.connectionPool = var1;
      this.address = var2;
      this.call = var3;
      this.eventListener = var4;
   }

   private final RealConnection findConnection(int var1, int var2, int var3, int var4, boolean var5) throws IOException {
      if (this.call.isCanceled()) {
         throw (Throwable)(new IOException("Canceled"));
      } else {
         RealConnection var6 = this.call.getConnection();
         boolean var7 = true;
         if (var6 != null) {
            Socket var8 = (Socket)null;
            synchronized(var6){}

            try {
               if (var6.getNoNewExchanges() || !this.sameHostAndPort(var6.route().address().url())) {
                  var8 = this.call.releaseConnectionNoEvents$okhttp();
               }

               Unit var9 = Unit.INSTANCE;
            } finally {
               ;
            }

            if (this.call.getConnection() != null) {
               boolean var22;
               if (var8 == null) {
                  var22 = var7;
               } else {
                  var22 = false;
               }

               if (var22) {
                  return var6;
               }

               throw (Throwable)(new IllegalStateException("Check failed.".toString()));
            }

            if (var8 != null) {
               Util.closeQuietly(var8);
            }

            this.eventListener.connectionReleased((Call)this.call, (Connection)var6);
         }

         this.refusedStreamCount = 0;
         this.connectionShutdownCount = 0;
         this.otherFailureCount = 0;
         RealConnection var29;
         if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List)null, false)) {
            var29 = this.call.getConnection();
            if (var29 == null) {
               Intrinsics.throwNpe();
            }

            this.eventListener.connectionAcquired((Call)this.call, (Connection)var29);
            return var29;
         } else {
            Route var25 = this.nextRouteToTry;
            List var23;
            if (var25 != null) {
               var23 = (List)null;
               if (var25 == null) {
                  Intrinsics.throwNpe();
               }

               this.nextRouteToTry = (Route)null;
            } else {
               label467: {
                  RouteSelector.Selection var26 = this.routeSelection;
                  if (var26 != null) {
                     if (var26 == null) {
                        Intrinsics.throwNpe();
                     }

                     if (var26.hasNext()) {
                        var23 = (List)null;
                        var26 = this.routeSelection;
                        if (var26 == null) {
                           Intrinsics.throwNpe();
                        }

                        var25 = var26.next();
                        break label467;
                     }
                  }

                  RouteSelector var24 = this.routeSelector;
                  RouteSelector var28 = var24;
                  if (var24 == null) {
                     var28 = new RouteSelector(this.address, this.call.getClient().getRouteDatabase(), (Call)this.call, this.eventListener);
                     this.routeSelector = var28;
                  }

                  var26 = var28.next();
                  this.routeSelection = var26;
                  var23 = var26.getRoutes();
                  if (this.call.isCanceled()) {
                     throw (Throwable)(new IOException("Canceled"));
                  }

                  if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, var23, false)) {
                     var29 = this.call.getConnection();
                     if (var29 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.eventListener.connectionAcquired((Call)this.call, (Connection)var29);
                     return var29;
                  }

                  var25 = var26.next();
               }
            }

            RealConnection var27 = new RealConnection(this.connectionPool, var25);
            this.call.setConnectionToCancel(var27);

            try {
               var27.connect(var1, var2, var3, var4, var5, (Call)this.call, this.eventListener);
            } finally {
               this.call.setConnectionToCancel((RealConnection)null);
            }

            this.call.getClient().getRouteDatabase().connected(var27.route());
            if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, var23, true)) {
               var6 = this.call.getConnection();
               if (var6 == null) {
                  Intrinsics.throwNpe();
               }

               this.nextRouteToTry = var25;
               Util.closeQuietly(var27.socket());
               this.eventListener.connectionAcquired((Call)this.call, (Connection)var6);
               return var6;
            } else {
               synchronized(var27){}

               try {
                  this.connectionPool.put(var27);
                  this.call.acquireConnectionNoEvents(var27);
                  Unit var30 = Unit.INSTANCE;
               } finally {
                  ;
               }

               this.eventListener.connectionAcquired((Call)this.call, (Connection)var27);
               return var27;
            }
         }
      }
   }

   private final RealConnection findHealthyConnection(int var1, int var2, int var3, int var4, boolean var5, boolean var6) throws IOException {
      while(true) {
         RealConnection var7 = this.findConnection(var1, var2, var3, var4, var5);
         if (var7.isHealthy(var6)) {
            return var7;
         }

         var7.noNewExchanges$okhttp();
         if (this.nextRouteToTry == null) {
            RouteSelector.Selection var10 = this.routeSelection;
            boolean var8 = true;
            boolean var9;
            if (var10 != null) {
               var9 = var10.hasNext();
            } else {
               var9 = true;
            }

            if (!var9) {
               RouteSelector var11 = this.routeSelector;
               var9 = var8;
               if (var11 != null) {
                  var9 = var11.hasNext();
               }

               if (!var9) {
                  throw (Throwable)(new IOException("exhausted all routes"));
               }
            }
         }
      }
   }

   private final Route retryRoute() {
      if (this.refusedStreamCount <= 1 && this.connectionShutdownCount <= 1 && this.otherFailureCount <= 0) {
         RealConnection var1 = this.call.getConnection();
         if (var1 != null) {
            synchronized(var1){}

            Throwable var10000;
            label181: {
               boolean var10001;
               int var2;
               try {
                  var2 = var1.getRouteFailureCount$okhttp();
               } catch (Throwable var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label181;
               }

               if (var2 != 0) {
                  return null;
               }

               boolean var3;
               try {
                  var3 = Util.canReuseConnectionFor(var1.route().address().url(), this.address.url());
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label181;
               }

               if (!var3) {
                  return null;
               }

               Route var17;
               try {
                  var17 = var1.route();
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label181;
               }

               return var17;
            }

            Throwable var4 = var10000;
            throw var4;
         }
      }

      return null;
   }

   public final ExchangeCodec find(OkHttpClient var1, RealInterceptorChain var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "chain");

      try {
         ExchangeCodec var5 = this.findHealthyConnection(var2.getConnectTimeoutMillis$okhttp(), var2.getReadTimeoutMillis$okhttp(), var2.getWriteTimeoutMillis$okhttp(), var1.pingIntervalMillis(), var1.retryOnConnectionFailure(), Intrinsics.areEqual((Object)var2.getRequest$okhttp().method(), (Object)"GET") ^ true).newCodec$okhttp(var1, var2);
         return var5;
      } catch (RouteException var3) {
         this.trackFailure(var3.getLastConnectException());
         throw (Throwable)var3;
      } catch (IOException var4) {
         this.trackFailure(var4);
         throw (Throwable)(new RouteException(var4));
      }
   }

   public final Address getAddress$okhttp() {
      return this.address;
   }

   public final boolean retryAfterFailure() {
      if (this.refusedStreamCount == 0 && this.connectionShutdownCount == 0 && this.otherFailureCount == 0) {
         return false;
      } else if (this.nextRouteToTry != null) {
         return true;
      } else {
         Route var1 = this.retryRoute();
         if (var1 != null) {
            this.nextRouteToTry = var1;
            return true;
         } else {
            RouteSelector.Selection var2 = this.routeSelection;
            if (var2 != null && var2.hasNext()) {
               return true;
            } else {
               RouteSelector var3 = this.routeSelector;
               return var3 != null ? var3.hasNext() : true;
            }
         }
      }
   }

   public final boolean sameHostAndPort(HttpUrl var1) {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      HttpUrl var2 = this.address.url();
      boolean var3;
      if (var1.port() == var2.port() && Intrinsics.areEqual((Object)var1.host(), (Object)var2.host())) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final void trackFailure(IOException var1) {
      Intrinsics.checkParameterIsNotNull(var1, "e");
      this.nextRouteToTry = (Route)null;
      if (var1 instanceof StreamResetException && ((StreamResetException)var1).errorCode == ErrorCode.REFUSED_STREAM) {
         ++this.refusedStreamCount;
      } else if (var1 instanceof ConnectionShutdownException) {
         ++this.connectionShutdownCount;
      } else {
         ++this.otherFailureCount;
      }

   }
}
