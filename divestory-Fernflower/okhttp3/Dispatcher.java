package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0019\u0010 \u001a\u00020\u001f2\n\u0010!\u001a\u00060\u001aR\u00020\u001bH\u0000¢\u0006\u0002\b\"J\u0015\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001bH\u0000¢\u0006\u0002\b$J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b%J\u0016\u0010&\u001a\b\u0018\u00010\u001aR\u00020\u001b2\u0006\u0010'\u001a\u00020(H\u0002J)\u0010)\u001a\u00020\u001f\"\u0004\b\u0000\u0010*2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H*0,2\u0006\u0010!\u001a\u0002H*H\u0002¢\u0006\u0002\u0010-J\u0015\u0010)\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001bH\u0000¢\u0006\u0002\b.J\u0019\u0010)\u001a\u00020\u001f2\n\u0010!\u001a\u00060\u001aR\u00020\u001bH\u0000¢\u0006\u0002\b.J\b\u0010/\u001a\u000200H\u0002J\f\u00101\u001a\b\u0012\u0004\u0012\u00020302J\u0006\u00104\u001a\u00020\u0010J\f\u00105\u001a\b\u0012\u0004\u0012\u00020302J\u0006\u00106\u001a\u00020\u0010R\u0011\u0010\u0002\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\b\u001a\u0004\u0018\u00010\t8F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R&\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001c\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"},
   d2 = {"Lokhttp3/Dispatcher;", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "(Ljava/util/concurrent/ExecutorService;)V", "()V", "()Ljava/util/concurrent/ExecutorService;", "executorServiceOrNull", "<set-?>", "Ljava/lang/Runnable;", "idleCallback", "getIdleCallback", "()Ljava/lang/Runnable;", "setIdleCallback", "(Ljava/lang/Runnable;)V", "maxRequests", "", "getMaxRequests", "()I", "setMaxRequests", "(I)V", "maxRequestsPerHost", "getMaxRequestsPerHost", "setMaxRequestsPerHost", "readyAsyncCalls", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealCall$AsyncCall;", "Lokhttp3/internal/connection/RealCall;", "runningAsyncCalls", "runningSyncCalls", "cancelAll", "", "enqueue", "call", "enqueue$okhttp", "executed", "executed$okhttp", "-deprecated_executorService", "findExistingCallWithHost", "host", "", "finished", "T", "calls", "Ljava/util/Deque;", "(Ljava/util/Deque;Ljava/lang/Object;)V", "finished$okhttp", "promoteAndExecute", "", "queuedCalls", "", "Lokhttp3/Call;", "queuedCallsCount", "runningCalls", "runningCallsCount", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Dispatcher {
   private ExecutorService executorServiceOrNull;
   private Runnable idleCallback;
   private int maxRequests;
   private int maxRequestsPerHost;
   private final ArrayDeque<RealCall.AsyncCall> readyAsyncCalls;
   private final ArrayDeque<RealCall.AsyncCall> runningAsyncCalls;
   private final ArrayDeque<RealCall> runningSyncCalls;

   public Dispatcher() {
      this.maxRequests = 64;
      this.maxRequestsPerHost = 5;
      this.readyAsyncCalls = new ArrayDeque();
      this.runningAsyncCalls = new ArrayDeque();
      this.runningSyncCalls = new ArrayDeque();
   }

   public Dispatcher(ExecutorService var1) {
      Intrinsics.checkParameterIsNotNull(var1, "executorService");
      this();
      this.executorServiceOrNull = var1;
   }

   private final RealCall.AsyncCall findExistingCallWithHost(String var1) {
      Iterator var2 = this.runningAsyncCalls.iterator();

      RealCall.AsyncCall var3;
      do {
         if (!var2.hasNext()) {
            Iterator var5 = this.readyAsyncCalls.iterator();

            RealCall.AsyncCall var4;
            do {
               if (!var5.hasNext()) {
                  return null;
               }

               var4 = (RealCall.AsyncCall)var5.next();
            } while(!Intrinsics.areEqual((Object)var4.getHost(), (Object)var1));

            return var4;
         }

         var3 = (RealCall.AsyncCall)var2.next();
      } while(!Intrinsics.areEqual((Object)var3.getHost(), (Object)var1));

      return var3;
   }

   private final <T> void finished(Deque<T> var1, T var2) {
      synchronized(this){}

      Runnable var5;
      try {
         if (!var1.remove(var2)) {
            AssertionError var6 = new AssertionError("Call wasn't in-flight!");
            throw (Throwable)var6;
         }

         var5 = this.idleCallback;
         Unit var7 = Unit.INSTANCE;
      } finally {
         ;
      }

      if (!this.promoteAndExecute() && var5 != null) {
         var5.run();
      }

   }

   private final boolean promoteAndExecute() {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var50 = new StringBuilder();
         var50.append("Thread ");
         Thread var52 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var52, "Thread.currentThread()");
         var50.append(var52.getName());
         var50.append(" MUST NOT hold lock on ");
         var50.append(this);
         throw (Throwable)(new AssertionError(var50.toString()));
      } else {
         List var1 = (List)(new ArrayList());
         synchronized(this){}

         int var4;
         int var5;
         boolean var6;
         label534: {
            Throwable var10000;
            label533: {
               Iterator var3;
               boolean var10001;
               try {
                  var3 = this.readyAsyncCalls.iterator();
                  Intrinsics.checkExpressionValueIsNotNull(var3, "readyAsyncCalls.iterator()");
               } catch (Throwable var46) {
                  var10000 = var46;
                  var10001 = false;
                  break label533;
               }

               while(true) {
                  RealCall.AsyncCall var2;
                  label542: {
                     try {
                        if (var3.hasNext()) {
                           var2 = (RealCall.AsyncCall)var3.next();
                           if (this.runningAsyncCalls.size() < this.maxRequests) {
                              break label542;
                           }
                        }
                     } catch (Throwable var48) {
                        var10000 = var48;
                        var10001 = false;
                        break;
                     }

                     try {
                        var4 = this.runningCallsCount();
                     } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break;
                     }

                     var5 = 0;
                     if (var4 > 0) {
                        var6 = true;
                     } else {
                        var6 = false;
                     }

                     try {
                        Unit var51 = Unit.INSTANCE;
                        break label534;
                     } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break;
                     }
                  }

                  label520:
                  try {
                     if (var2.getCallsPerHost().get() < this.maxRequestsPerHost) {
                        break label520;
                     }
                     continue;
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break;
                  }

                  try {
                     var3.remove();
                     var2.getCallsPerHost().incrementAndGet();
                     Intrinsics.checkExpressionValueIsNotNull(var2, "asyncCall");
                     var1.add(var2);
                     this.runningAsyncCalls.add(var2);
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var49 = var10000;
            throw var49;
         }

         for(var4 = var1.size(); var5 < var4; ++var5) {
            ((RealCall.AsyncCall)var1.get(var5)).executeOn(this.executorService());
         }

         return var6;
      }
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "executorService",
   imports = {}
)
   )
   public final ExecutorService _deprecated_executorService/* $FF was: -deprecated_executorService*/() {
      return this.executorService();
   }

   public final void cancelAll() {
      synchronized(this){}

      Throwable var10000;
      label427: {
         Iterator var1;
         boolean var10001;
         try {
            var1 = this.readyAsyncCalls.iterator();
         } catch (Throwable var42) {
            var10000 = var42;
            var10001 = false;
            break label427;
         }

         label426:
         while(true) {
            try {
               if (var1.hasNext()) {
                  ((RealCall.AsyncCall)var1.next()).getCall().cancel();
                  continue;
               }
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break;
            }

            try {
               var1 = this.runningAsyncCalls.iterator();
            } catch (Throwable var40) {
               var10000 = var40;
               var10001 = false;
               break;
            }

            while(true) {
               try {
                  if (!var1.hasNext()) {
                     break;
                  }

                  ((RealCall.AsyncCall)var1.next()).getCall().cancel();
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break label426;
               }
            }

            try {
               var1 = this.runningSyncCalls.iterator();
            } catch (Throwable var39) {
               var10000 = var39;
               var10001 = false;
               break;
            }

            while(true) {
               try {
                  if (!var1.hasNext()) {
                     return;
                  }

                  ((RealCall)var1.next()).cancel();
               } catch (Throwable var38) {
                  var10000 = var38;
                  var10001 = false;
                  break label426;
               }
            }
         }
      }

      Throwable var44 = var10000;
      throw var44;
   }

   public final void enqueue$okhttp(RealCall.AsyncCall var1) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      synchronized(this){}

      label121: {
         Throwable var10000;
         label120: {
            boolean var10001;
            label119: {
               RealCall.AsyncCall var2;
               try {
                  this.readyAsyncCalls.add(var1);
                  if (var1.getCall().getForWebSocket()) {
                     break label119;
                  }

                  var2 = this.findExistingCallWithHost(var1.getHost());
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label120;
               }

               if (var2 != null) {
                  try {
                     var1.reuseCallsPerHostFrom(var2);
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label120;
                  }
               }
            }

            label111:
            try {
               Unit var16 = Unit.INSTANCE;
               break label121;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label111;
            }
         }

         Throwable var15 = var10000;
         throw var15;
      }

      this.promoteAndExecute();
   }

   public final void executed$okhttp(RealCall var1) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "call");
         this.runningSyncCalls.add(var1);
      } finally {
         ;
      }

   }

   public final ExecutorService executorService() {
      synchronized(this){}

      Throwable var10000;
      label136: {
         boolean var10001;
         try {
            if (this.executorServiceOrNull == null) {
               TimeUnit var2 = TimeUnit.SECONDS;
               SynchronousQueue var3 = new SynchronousQueue();
               BlockingQueue var19 = (BlockingQueue)var3;
               StringBuilder var4 = new StringBuilder();
               var4.append(Util.okHttpName);
               var4.append(" Dispatcher");
               ThreadPoolExecutor var1 = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, var2, var19, Util.threadFactory(var4.toString(), false));
               this.executorServiceOrNull = (ExecutorService)var1;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label136;
         }

         ExecutorService var17;
         try {
            var17 = this.executorServiceOrNull;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label136;
         }

         if (var17 != null) {
            return var17;
         }

         label124:
         try {
            Intrinsics.throwNpe();
            return var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label124;
         }
      }

      Throwable var18 = var10000;
      throw var18;
   }

   public final void finished$okhttp(RealCall.AsyncCall var1) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      var1.getCallsPerHost().decrementAndGet();
      this.finished((Deque)this.runningAsyncCalls, var1);
   }

   public final void finished$okhttp(RealCall var1) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      this.finished((Deque)this.runningSyncCalls, var1);
   }

   public final Runnable getIdleCallback() {
      synchronized(this){}

      Runnable var1;
      try {
         var1 = this.idleCallback;
      } finally {
         ;
      }

      return var1;
   }

   public final int getMaxRequests() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.maxRequests;
      } finally {
         ;
      }

      return var1;
   }

   public final int getMaxRequestsPerHost() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.maxRequestsPerHost;
      } finally {
         ;
      }

      return var1;
   }

   public final List<Call> queuedCalls() {
      synchronized(this){}

      Throwable var10000;
      label132: {
         boolean var10001;
         Collection var18;
         Iterator var15;
         try {
            Iterable var1 = (Iterable)this.readyAsyncCalls;
            ArrayList var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
            var18 = (Collection)var2;
            var15 = var1.iterator();
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label132;
         }

         while(true) {
            try {
               if (var15.hasNext()) {
                  var18.add(((RealCall.AsyncCall)var15.next()).getCall());
                  continue;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break;
            }

            try {
               List var17 = Collections.unmodifiableList((List)var18);
               Intrinsics.checkExpressionValueIsNotNull(var17, "Collections.unmodifiable…yncCalls.map { it.call })");
               return var17;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var16 = var10000;
      throw var16;
   }

   public final int queuedCallsCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.readyAsyncCalls.size();
      } finally {
         ;
      }

      return var1;
   }

   public final List<Call> runningCalls() {
      synchronized(this){}

      Throwable var10000;
      label132: {
         Collection var1;
         boolean var10001;
         Iterator var18;
         Collection var19;
         try {
            var1 = (Collection)this.runningSyncCalls;
            Iterable var2 = (Iterable)this.runningAsyncCalls;
            ArrayList var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
            var19 = (Collection)var3;
            var18 = var2.iterator();
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label132;
         }

         while(true) {
            try {
               if (var18.hasNext()) {
                  var19.add(((RealCall.AsyncCall)var18.next()).getCall());
                  continue;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break;
            }

            try {
               List var17 = Collections.unmodifiableList(CollectionsKt.plus(var1, (Iterable)((List)var19)));
               Intrinsics.checkExpressionValueIsNotNull(var17, "Collections.unmodifiable…yncCalls.map { it.call })");
               return var17;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var16 = var10000;
      throw var16;
   }

   public final int runningCallsCount() {
      synchronized(this){}

      int var1;
      int var2;
      try {
         var1 = this.runningAsyncCalls.size();
         var2 = this.runningSyncCalls.size();
      } finally {
         ;
      }

      return var1 + var2;
   }

   public final void setIdleCallback(Runnable var1) {
      synchronized(this){}

      try {
         this.idleCallback = var1;
      } finally {
         ;
      }

   }

   public final void setMaxRequests(int var1) {
      boolean var2 = true;
      if (var1 < 1) {
         var2 = false;
      }

      if (var2) {
         synchronized(this){}

         try {
            this.maxRequests = var1;
            Unit var6 = Unit.INSTANCE;
         } finally {
            ;
         }

         this.promoteAndExecute();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("max < 1: ");
         var3.append(var1);
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public final void setMaxRequestsPerHost(int var1) {
      boolean var2 = true;
      if (var1 < 1) {
         var2 = false;
      }

      if (var2) {
         synchronized(this){}

         try {
            this.maxRequestsPerHost = var1;
            Unit var6 = Unit.INSTANCE;
         } finally {
            ;
         }

         this.promoteAndExecute();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("max < 1: ");
         var3.append(var1);
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }
}
