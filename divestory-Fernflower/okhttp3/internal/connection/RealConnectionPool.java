package okhttp3.internal.connection;

import java.lang.ref.Reference;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.platform.Platform;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u000e\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0012J\u0006\u0010\"\u001a\u00020\u0005J\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u0005J\u0018\u0010&\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\u000e\u0010'\u001a\u00020$2\u0006\u0010!\u001a\u00020\u0012R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lokhttp3/internal/connection/RealConnectionPool;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Lokhttp3/internal/concurrent/TaskRunner;IJLjava/util/concurrent/TimeUnit;)V", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/connection/RealConnectionPool$cleanupTask$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupTask$1;", "connections", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lokhttp3/internal/connection/RealConnection;", "keepAliveDurationNs", "callAcquirePooledConnection", "", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "routes", "", "Lokhttp3/Route;", "requireMultiplexed", "cleanup", "now", "connectionBecameIdle", "connection", "connectionCount", "evictAll", "", "idleConnectionCount", "pruneAndGetAllocationCount", "put", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealConnectionPool {
   public static final RealConnectionPool.Companion Companion = new RealConnectionPool.Companion((DefaultConstructorMarker)null);
   private final TaskQueue cleanupQueue;
   private final <undefinedtype> cleanupTask;
   private final ConcurrentLinkedQueue<RealConnection> connections;
   private final long keepAliveDurationNs;
   private final int maxIdleConnections;

   public RealConnectionPool(TaskRunner var1, int var2, long var3, TimeUnit var5) {
      Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      Intrinsics.checkParameterIsNotNull(var5, "timeUnit");
      super();
      this.maxIdleConnections = var2;
      this.keepAliveDurationNs = var5.toNanos(var3);
      this.cleanupQueue = var1.newQueue();
      StringBuilder var6 = new StringBuilder();
      var6.append(Util.okHttpName);
      var6.append(" ConnectionPool");
      this.cleanupTask = new Task(var6.toString()) {
         public long runOnce() {
            return RealConnectionPool.this.cleanup(System.nanoTime());
         }
      };
      this.connections = new ConcurrentLinkedQueue();
      boolean var7;
      if (var3 > 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         var6 = new StringBuilder();
         var6.append("keepAliveDuration <= 0: ");
         var6.append(var3);
         throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
      }
   }

   private final int pruneAndGetAllocationCount(RealConnection var1, long var2) {
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         StringBuilder var9 = new StringBuilder();
         var9.append("Thread ");
         Thread var10 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var10, "Thread.currentThread()");
         var9.append(var10.getName());
         var9.append(" MUST hold lock on ");
         var9.append(var1);
         throw (Throwable)(new AssertionError(var9.toString()));
      } else {
         List var5 = var1.getCalls();
         int var6 = 0;

         while(var6 < var5.size()) {
            Reference var4 = (Reference)var5.get(var6);
            if (var4.get() != null) {
               ++var6;
            } else {
               if (var4 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.connection.RealCall.CallReference");
               }

               RealCall.CallReference var8 = (RealCall.CallReference)var4;
               StringBuilder var7 = new StringBuilder();
               var7.append("A connection to ");
               var7.append(var1.route().address().url());
               var7.append(" was leaked. ");
               var7.append("Did you forget to close a response body?");
               String var11 = var7.toString();
               Platform.Companion.get().logCloseableLeak(var11, var8.getCallStackTrace());
               var5.remove(var6);
               var1.setNoNewExchanges(true);
               if (var5.isEmpty()) {
                  var1.setIdleAtNs$okhttp(var2 - this.keepAliveDurationNs);
                  return 0;
               }
            }
         }

         return var5.size();
      }
   }

   public final boolean callAcquirePooledConnection(Address var1, RealCall var2, List<Route> var3, boolean var4) {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      Intrinsics.checkParameterIsNotNull(var2, "call");
      Iterator var5 = this.connections.iterator();

      while(var5.hasNext()) {
         Throwable var10000;
         label246: {
            boolean var10001;
            label247: {
               RealConnection var6 = (RealConnection)var5.next();
               Intrinsics.checkExpressionValueIsNotNull(var6, "connection");
               synchronized(var6){}
               if (var4) {
                  label234:
                  try {
                     if (var6.isMultiplexed$okhttp()) {
                        break label234;
                     }
                     break label247;
                  } catch (Throwable var27) {
                     var10000 = var27;
                     var10001 = false;
                     break label246;
                  }
               }

               try {
                  if (!var6.isEligible$okhttp(var1, var3)) {
                     break label247;
                  }
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break label246;
               }

               try {
                  var2.acquireConnectionNoEvents(var6);
                  return true;
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break label246;
               }
            }

            label223:
            try {
               Unit var7 = Unit.INSTANCE;
               continue;
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label223;
            }
         }

         Throwable var28 = var10000;
         throw var28;
      }

      return false;
   }

   public final long cleanup(long var1) {
      RealConnection var3 = (RealConnection)null;
      Iterator var4 = this.connections.iterator();
      int var5 = 0;
      long var6 = Long.MIN_VALUE;
      int var8 = 0;

      long var10;
      Throwable var10000;
      boolean var10001;
      while(var4.hasNext()) {
         RealConnection var9 = (RealConnection)var4.next();
         Intrinsics.checkExpressionValueIsNotNull(var9, "connection");
         synchronized(var9){}

         label813: {
            label823: {
               try {
                  if (this.pruneAndGetAllocationCount(var9, var1) > 0) {
                     break label813;
                  }
               } catch (Throwable var69) {
                  var10000 = var69;
                  var10001 = false;
                  break label823;
               }

               ++var5;

               try {
                  var10 = var1 - var9.getIdleAtNs$okhttp();
               } catch (Throwable var68) {
                  var10000 = var68;
                  var10001 = false;
                  break label823;
               }

               if (var10 > var6) {
                  label800: {
                     try {
                        Unit var70 = Unit.INSTANCE;
                     } catch (Throwable var66) {
                        var10000 = var66;
                        var10001 = false;
                        break label800;
                     }

                     var3 = var9;
                     var6 = var10;
                  }
               } else {
                  label802:
                  try {
                     Unit var12 = Unit.INSTANCE;
                  } catch (Throwable var67) {
                     var10000 = var67;
                     var10001 = false;
                     break label802;
                  }
               }
               continue;
            }

            Throwable var71 = var10000;
            throw var71;
         }

         ++var8;
      }

      var10 = this.keepAliveDurationNs;
      if (var6 < var10 && var5 <= this.maxIdleConnections) {
         if (var5 > 0) {
            return var10 - var6;
         } else {
            return var8 > 0 ? var10 : -1L;
         }
      } else {
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         synchronized(var3){}

         label825: {
            boolean var13;
            try {
               var13 = ((Collection)var3.getCalls()).isEmpty();
            } catch (Throwable var65) {
               var10000 = var65;
               var10001 = false;
               break label825;
            }

            if (var13 ^ true) {
               return 0L;
            }

            try {
               var10 = var3.getIdleAtNs$okhttp();
            } catch (Throwable var64) {
               var10000 = var64;
               var10001 = false;
               break label825;
            }

            if (var10 + var6 != var1) {
               return 0L;
            }

            try {
               var3.setNoNewExchanges(true);
               this.connections.remove(var3);
            } catch (Throwable var63) {
               var10000 = var63;
               var10001 = false;
               break label825;
            }

            Util.closeQuietly(var3.socket());
            if (this.connections.isEmpty()) {
               this.cleanupQueue.cancelAll();
            }

            return 0L;
         }

         Throwable var72 = var10000;
         throw var72;
      }
   }

   public final boolean connectionBecameIdle(RealConnection var1) {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Thread ");
         Thread var3 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var2.append(var3.getName());
         var2.append(" MUST hold lock on ");
         var2.append(var1);
         throw (Throwable)(new AssertionError(var2.toString()));
      } else {
         boolean var4 = var1.getNoNewExchanges();
         boolean var5 = true;
         if (!var4 && this.maxIdleConnections != 0) {
            TaskQueue.schedule$default(this.cleanupQueue, (Task)this.cleanupTask, 0L, 2, (Object)null);
            var4 = false;
         } else {
            var1.setNoNewExchanges(true);
            this.connections.remove(var1);
            var4 = var5;
            if (this.connections.isEmpty()) {
               this.cleanupQueue.cancelAll();
               var4 = var5;
            }
         }

         return var4;
      }
   }

   public final int connectionCount() {
      return this.connections.size();
   }

   public final void evictAll() {
      Iterator var1 = this.connections.iterator();
      Intrinsics.checkExpressionValueIsNotNull(var1, "connections.iterator()");

      while(var1.hasNext()) {
         RealConnection var2 = (RealConnection)var1.next();
         Intrinsics.checkExpressionValueIsNotNull(var2, "connection");
         synchronized(var2){}
         boolean var5 = false;

         Socket var3;
         label66: {
            try {
               var5 = true;
               if (var2.getCalls().isEmpty()) {
                  var1.remove();
                  var2.setNoNewExchanges(true);
                  var3 = var2.socket();
                  var5 = false;
                  break label66;
               }

               var5 = false;
            } finally {
               if (var5) {
                  ;
               }
            }

            var3 = null;
         }

         if (var3 != null) {
            Util.closeQuietly(var3);
         }
      }

      if (this.connections.isEmpty()) {
         this.cleanupQueue.cancelAll();
      }

   }

   public final int idleConnectionCount() {
      Iterable var1 = (Iterable)this.connections;
      boolean var2 = var1 instanceof Collection;
      int var3 = 0;
      int var4 = 0;
      if (!var2 || !((Collection)var1).isEmpty()) {
         Iterator var5 = var1.iterator();

         while(true) {
            var3 = var4;
            if (!var5.hasNext()) {
               break;
            }

            RealConnection var8 = (RealConnection)var5.next();
            Intrinsics.checkExpressionValueIsNotNull(var8, "it");
            synchronized(var8){}

            try {
               var2 = var8.getCalls().isEmpty();
            } finally {
               ;
            }

            if (var2) {
               var3 = var4 + 1;
               var4 = var3;
               if (var3 < 0) {
                  CollectionsKt.throwCountOverflow();
                  var4 = var3;
               }
            }
         }
      }

      return var3;
   }

   public final void put(RealConnection var1) {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Thread ");
         Thread var3 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var2.append(var3.getName());
         var2.append(" MUST hold lock on ");
         var2.append(var1);
         throw (Throwable)(new AssertionError(var2.toString()));
      } else {
         this.connections.add(var1);
         TaskQueue.schedule$default(this.cleanupQueue, (Task)this.cleanupTask, 0L, 2, (Object)null);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", "()V", "get", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final RealConnectionPool get(ConnectionPool var1) {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         return var1.getDelegate$okhttp();
      }
   }
}
