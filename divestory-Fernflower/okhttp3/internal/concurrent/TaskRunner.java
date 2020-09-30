package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 #2\u00020\u0001:\u0003\"#$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0018J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u001c\u001a\u00020\u0016J\u0015\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\tH\u0000¢\u0006\u0002\b\u001fJ\u0006\u0010 \u001a\u00020\tJ\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
   d2 = {"Lokhttp3/internal/concurrent/TaskRunner;", "", "backend", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "(Lokhttp3/internal/concurrent/TaskRunner$Backend;)V", "getBackend", "()Lokhttp3/internal/concurrent/TaskRunner$Backend;", "busyQueues", "", "Lokhttp3/internal/concurrent/TaskQueue;", "coordinatorWaiting", "", "coordinatorWakeUpAt", "", "nextQueueName", "", "readyQueues", "runnable", "Ljava/lang/Runnable;", "activeQueues", "", "afterRun", "", "task", "Lokhttp3/internal/concurrent/Task;", "delayNanos", "awaitTaskToRun", "beforeRun", "cancelAll", "kickCoordinator", "taskQueue", "kickCoordinator$okhttp", "newQueue", "runTask", "Backend", "Companion", "RealBackend", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TaskRunner {
   public static final TaskRunner.Companion Companion = new TaskRunner.Companion((DefaultConstructorMarker)null);
   public static final TaskRunner INSTANCE;
   private static final Logger logger;
   private final TaskRunner.Backend backend;
   private final List<TaskQueue> busyQueues;
   private boolean coordinatorWaiting;
   private long coordinatorWakeUpAt;
   private int nextQueueName;
   private final List<TaskQueue> readyQueues;
   private final Runnable runnable;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(Util.okHttpName);
      var0.append(" TaskRunner");
      INSTANCE = new TaskRunner((TaskRunner.Backend)(new TaskRunner.RealBackend(Util.threadFactory(var0.toString(), true))));
      Logger var1 = Logger.getLogger(TaskRunner.class.getName());
      Intrinsics.checkExpressionValueIsNotNull(var1, "Logger.getLogger(TaskRunner::class.java.name)");
      logger = var1;
   }

   public TaskRunner(TaskRunner.Backend var1) {
      Intrinsics.checkParameterIsNotNull(var1, "backend");
      super();
      this.backend = var1;
      this.nextQueueName = 10000;
      this.busyQueues = (List)(new ArrayList());
      this.readyQueues = (List)(new ArrayList());
      this.runnable = (Runnable)(new Runnable() {
         public void run() {
            while(true) {
               TaskRunner var1 = TaskRunner.this;
               synchronized(var1){}

               Task var2;
               try {
                  var2 = TaskRunner.this.awaitTaskToRun();
               } finally {
                  ;
               }

               if (var2 == null) {
                  return;
               }

               TaskQueue var31 = var2.getQueue$okhttp();
               if (var31 == null) {
                  Intrinsics.throwNpe();
               }

               long var3 = -1L;
               boolean var5 = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
               if (var5) {
                  var3 = var31.getTaskRunner$okhttp().getBackend().nanoTime();
                  TaskLoggerKt.access$log(var2, var31, "starting");
               }

               boolean var22 = false;

               long var7;
               label264: {
                  Throwable var10000;
                  label251: {
                     boolean var10001;
                     try {
                        var22 = true;
                        TaskRunner.this.runTask(var2);
                        var22 = false;
                     } finally {
                        if (var22) {
                           try {
                              TaskRunner.this.getBackend().execute((Runnable)this);
                           } catch (Throwable var28) {
                              var10000 = var28;
                              var10001 = false;
                              break label251;
                           }
                        }
                     }

                     label248:
                     try {
                        Unit var32 = Unit.INSTANCE;
                        break label264;
                     } catch (Throwable var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label248;
                     }
                  }

                  Throwable var6 = var10000;
                  if (var5) {
                     var7 = var31.getTaskRunner$okhttp().getBackend().nanoTime();
                     StringBuilder var9 = new StringBuilder();
                     var9.append("failed a run in ");
                     var9.append(TaskLoggerKt.formatDuration(var7 - var3));
                     TaskLoggerKt.access$log(var2, var31, var9.toString());
                  }

                  throw var6;
               }

               if (var5) {
                  var7 = var31.getTaskRunner$okhttp().getBackend().nanoTime();
                  StringBuilder var33 = new StringBuilder();
                  var33.append("finished run in ");
                  var33.append(TaskLoggerKt.formatDuration(var7 - var3));
                  TaskLoggerKt.access$log(var2, var31, var33.toString());
               }
            }
         }
      });
   }

   private final void afterRun(Task var1, long var2) {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var7 = new StringBuilder();
         var7.append("Thread ");
         Thread var8 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var8, "Thread.currentThread()");
         var7.append(var8.getName());
         var7.append(" MUST hold lock on ");
         var7.append(this);
         throw (Throwable)(new AssertionError(var7.toString()));
      } else {
         TaskQueue var4 = var1.getQueue$okhttp();
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         boolean var5;
         if (var4.getActiveTask$okhttp() == var1) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            boolean var6 = var4.getCancelActiveTask$okhttp();
            var4.setCancelActiveTask$okhttp(false);
            var4.setActiveTask$okhttp((Task)null);
            this.busyQueues.remove(var4);
            if (var2 != -1L && !var6 && !var4.getShutdown$okhttp()) {
               var4.scheduleAndDecide$okhttp(var1, var2, true);
            }

            if (((Collection)var4.getFutureTasks$okhttp()).isEmpty() ^ true) {
               this.readyQueues.add(var4);
            }

         } else {
            throw (Throwable)(new IllegalStateException("Check failed.".toString()));
         }
      }
   }

   private final void beforeRun(Task var1) {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Thread ");
         Thread var3 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var4.append(var3.getName());
         var4.append(" MUST hold lock on ");
         var4.append(this);
         throw (Throwable)(new AssertionError(var4.toString()));
      } else {
         var1.setNextExecuteNanoTime$okhttp(-1L);
         TaskQueue var2 = var1.getQueue$okhttp();
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         var2.getFutureTasks$okhttp().remove(var1);
         this.readyQueues.remove(var2);
         var2.setActiveTask$okhttp(var1);
         this.busyQueues.add(var2);
      }
   }

   private final void runTask(Task var1) {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var22 = new StringBuilder();
         var22.append("Thread ");
         Thread var21 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var21, "Thread.currentThread()");
         var22.append(var21.getName());
         var22.append(" MUST NOT hold lock on ");
         var22.append(this);
         throw (Throwable)(new AssertionError(var22.toString()));
      } else {
         Thread var3 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "currentThread");
         String var2 = var3.getName();
         var3.setName(var1.getName());
         boolean var16 = false;

         long var4;
         Unit var20;
         try {
            var16 = true;
            var4 = var1.runOnce();
            var16 = false;
         } finally {
            if (var16) {
               synchronized(this){}

               try {
                  this.afterRun(var1, -1L);
                  var20 = Unit.INSTANCE;
               } finally {
                  ;
               }

               var3.setName(var2);
            }
         }

         synchronized(this){}

         try {
            this.afterRun(var1, var4);
            var20 = Unit.INSTANCE;
         } finally {
            ;
         }

         var3.setName(var2);
      }
   }

   public final List<TaskQueue> activeQueues() {
      synchronized(this){}

      List var1;
      try {
         var1 = CollectionsKt.plus((Collection)this.busyQueues, (Iterable)this.readyQueues);
      } finally {
         ;
      }

      return var1;
   }

   public final Task awaitTaskToRun() {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var15 = new StringBuilder();
         var15.append("Thread ");
         Thread var16 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var16, "Thread.currentThread()");
         var15.append(var16.getName());
         var15.append(" MUST hold lock on ");
         var15.append(this);
         throw (Throwable)(new AssertionError(var15.toString()));
      } else {
         while(!this.readyQueues.isEmpty()) {
            long var3 = this.backend.nanoTime();
            long var5 = Long.MAX_VALUE;
            Task var2 = (Task)null;
            Iterator var7 = this.readyQueues.iterator();

            boolean var10;
            while(true) {
               if (!var7.hasNext()) {
                  var10 = false;
                  break;
               }

               Task var1 = (Task)((TaskQueue)var7.next()).getFutureTasks$okhttp().get(0);
               long var8 = Math.max(0L, var1.getNextExecuteNanoTime$okhttp() - var3);
               if (var8 > 0L) {
                  var5 = Math.min(var8, var5);
               } else {
                  if (var2 != null) {
                     var10 = true;
                     break;
                  }

                  var2 = var1;
               }
            }

            if (var2 != null) {
               this.beforeRun(var2);
               if (var10 || !this.coordinatorWaiting && ((Collection)this.readyQueues).isEmpty() ^ true) {
                  this.backend.execute(this.runnable);
               }

               return var2;
            }

            if (this.coordinatorWaiting) {
               if (var5 < this.coordinatorWakeUpAt - var3) {
                  this.backend.coordinatorNotify(this);
               }

               return null;
            }

            this.coordinatorWaiting = true;
            this.coordinatorWakeUpAt = var3 + var5;

            try {
               this.backend.coordinatorWait(this, var5);
            } catch (InterruptedException var13) {
               this.cancelAll();
            } finally {
               this.coordinatorWaiting = false;
            }
         }

         return null;
      }
   }

   public final void cancelAll() {
      int var1;
      for(var1 = this.busyQueues.size() - 1; var1 >= 0; --var1) {
         ((TaskQueue)this.busyQueues.get(var1)).cancelAllAndDecide$okhttp();
      }

      for(var1 = this.readyQueues.size() - 1; var1 >= 0; --var1) {
         TaskQueue var2 = (TaskQueue)this.readyQueues.get(var1);
         var2.cancelAllAndDecide$okhttp();
         if (var2.getFutureTasks$okhttp().isEmpty()) {
            this.readyQueues.remove(var1);
         }
      }

   }

   public final TaskRunner.Backend getBackend() {
      return this.backend;
   }

   public final void kickCoordinator$okhttp(TaskQueue var1) {
      Intrinsics.checkParameterIsNotNull(var1, "taskQueue");
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Thread ");
         Thread var2 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var3.append(var2.getName());
         var3.append(" MUST hold lock on ");
         var3.append(this);
         throw (Throwable)(new AssertionError(var3.toString()));
      } else {
         if (var1.getActiveTask$okhttp() == null) {
            if (((Collection)var1.getFutureTasks$okhttp()).isEmpty() ^ true) {
               Util.addIfAbsent(this.readyQueues, var1);
            } else {
               this.readyQueues.remove(var1);
            }
         }

         if (this.coordinatorWaiting) {
            this.backend.coordinatorNotify(this);
         } else {
            this.backend.execute(this.runnable);
         }

      }
   }

   public final TaskQueue newQueue() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.nextQueueName++;
      } finally {
         ;
      }

      StringBuilder var2 = new StringBuilder();
      var2.append('Q');
      var2.append(var1);
      return new TaskQueue(this, var2.toString());
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\tH&¨\u0006\u000e"},
      d2 = {"Lokhttp3/internal/concurrent/TaskRunner$Backend;", "", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public interface Backend {
      void beforeTask(TaskRunner var1);

      void coordinatorNotify(TaskRunner var1);

      void coordinatorWait(TaskRunner var1, long var2);

      void execute(Runnable var1);

      long nanoTime();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
      d2 = {"Lokhttp3/internal/concurrent/TaskRunner$Companion;", "", "()V", "INSTANCE", "Lokhttp3/internal/concurrent/TaskRunner;", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "okhttp"},
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

      public final Logger getLogger() {
         return TaskRunner.logger;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0006\u0010\u0013\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
      d2 = {"Lokhttp3/internal/concurrent/TaskRunner$RealBackend;", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "(Ljava/util/concurrent/ThreadFactory;)V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "shutdown", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class RealBackend implements TaskRunner.Backend {
      private final ThreadPoolExecutor executor;

      public RealBackend(ThreadFactory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "threadFactory");
         super();
         this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, (BlockingQueue)(new SynchronousQueue()), var1);
      }

      public void beforeTask(TaskRunner var1) {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      }

      public void coordinatorNotify(TaskRunner var1) {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
         ((Object)var1).notify();
      }

      public void coordinatorWait(TaskRunner var1, long var2) throws InterruptedException {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
         long var4 = var2 / 1000000L;
         if (var4 > 0L || var2 > 0L) {
            ((Object)var1).wait(var4, (int)(var2 - 1000000L * var4));
         }

      }

      public void execute(Runnable var1) {
         Intrinsics.checkParameterIsNotNull(var1, "runnable");
         this.executor.execute(var1);
      }

      public long nanoTime() {
         return System.nanoTime();
      }

      public final void shutdown() {
         this.executor.shutdown();
      }
   }
}
