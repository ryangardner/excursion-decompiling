package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u00013B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010!\u001a\u00020\"J\r\u0010#\u001a\u00020\u000eH\u0000¢\u0006\u0002\b$J5\u0010%\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010&\u001a\u00020'2\b\b\u0002\u0010(\u001a\u00020\u000e2\u000e\b\u0004\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0*H\u0086\bJ\u0006\u0010+\u001a\u00020,J+\u0010-\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010&\u001a\u00020'2\u000e\b\u0004\u0010)\u001a\b\u0012\u0004\u0012\u00020'0*H\u0086\bJ\u0018\u0010-\u001a\u00020\"2\u0006\u0010.\u001a\u00020\b2\b\b\u0002\u0010&\u001a\u00020'J%\u0010/\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\b2\u0006\u0010&\u001a\u00020'2\u0006\u00100\u001a\u00020\u000eH\u0000¢\u0006\u0002\b1J\u0006\u0010\u001c\u001a\u00020\"J\b\u00102\u001a\u00020\u0005H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u0014X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0016R\u001a\u0010\u001c\u001a\u00020\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u00064"},
   d2 = {"Lokhttp3/internal/concurrent/TaskQueue;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "name", "", "(Lokhttp3/internal/concurrent/TaskRunner;Ljava/lang/String;)V", "activeTask", "Lokhttp3/internal/concurrent/Task;", "getActiveTask$okhttp", "()Lokhttp3/internal/concurrent/Task;", "setActiveTask$okhttp", "(Lokhttp3/internal/concurrent/Task;)V", "cancelActiveTask", "", "getCancelActiveTask$okhttp", "()Z", "setCancelActiveTask$okhttp", "(Z)V", "futureTasks", "", "getFutureTasks$okhttp", "()Ljava/util/List;", "getName$okhttp", "()Ljava/lang/String;", "scheduledTasks", "", "getScheduledTasks", "shutdown", "getShutdown$okhttp", "setShutdown$okhttp", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "cancelAll", "", "cancelAllAndDecide", "cancelAllAndDecide$okhttp", "execute", "delayNanos", "", "cancelable", "block", "Lkotlin/Function0;", "idleLatch", "Ljava/util/concurrent/CountDownLatch;", "schedule", "task", "scheduleAndDecide", "recurrence", "scheduleAndDecide$okhttp", "toString", "AwaitIdleTask", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TaskQueue {
   private Task activeTask;
   private boolean cancelActiveTask;
   private final List<Task> futureTasks;
   private final String name;
   private boolean shutdown;
   private final TaskRunner taskRunner;

   public TaskQueue(TaskRunner var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      Intrinsics.checkParameterIsNotNull(var2, "name");
      super();
      this.taskRunner = var1;
      this.name = var2;
      this.futureTasks = (List)(new ArrayList());
   }

   // $FF: synthetic method
   public static void execute$default(TaskQueue var0, String var1, long var2, boolean var4, Function0 var5, int var6, Object var7) {
      if ((var6 & 2) != 0) {
         var2 = 0L;
      }

      if ((var6 & 4) != 0) {
         var4 = true;
      }

      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var5, "block");
      var0.schedule((Task)(new Task(var1, var4) {
         public long runOnce() {
            var5.invoke();
            return -1L;
         }
      }), var2);
   }

   // $FF: synthetic method
   public static void schedule$default(TaskQueue var0, String var1, long var2, Function0 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0L;
      }

      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var4, "block");
      var0.schedule((Task)(new Task(var1) {
         public long runOnce() {
            return ((Number)var4.invoke()).longValue();
         }
      }), var2);
   }

   // $FF: synthetic method
   public static void schedule$default(TaskQueue var0, Task var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0L;
      }

      var0.schedule(var1, var2);
   }

   public final void cancelAll() {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Thread ");
         Thread var6 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var6, "Thread.currentThread()");
         var5.append(var6.getName());
         var5.append(" MUST NOT hold lock on ");
         var5.append(this);
         throw (Throwable)(new AssertionError(var5.toString()));
      } else {
         TaskRunner var2 = this.taskRunner;
         synchronized(var2){}

         try {
            if (this.cancelAllAndDecide$okhttp()) {
               this.taskRunner.kickCoordinator$okhttp(this);
            }

            Unit var1 = Unit.INSTANCE;
         } finally {
            ;
         }

      }
   }

   public final boolean cancelAllAndDecide$okhttp() {
      Task var1 = this.activeTask;
      if (var1 != null) {
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         if (var1.getCancelable()) {
            this.cancelActiveTask = true;
         }
      }

      boolean var2 = false;

      for(int var3 = this.futureTasks.size() - 1; var3 >= 0; --var3) {
         if (((Task)this.futureTasks.get(var3)).getCancelable()) {
            var1 = (Task)this.futureTasks.get(var3);
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var1, this, "canceled");
            }

            this.futureTasks.remove(var3);
            var2 = true;
         }
      }

      return var2;
   }

   public final void execute(final String var1, long var2, final boolean var4, final Function0<Unit> var5) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var5, "block");
      this.schedule((Task)(new Task(var1, var4) {
         public long runOnce() {
            var5.invoke();
            return -1L;
         }
      }), var2);
   }

   public final Task getActiveTask$okhttp() {
      return this.activeTask;
   }

   public final boolean getCancelActiveTask$okhttp() {
      return this.cancelActiveTask;
   }

   public final List<Task> getFutureTasks$okhttp() {
      return this.futureTasks;
   }

   public final String getName$okhttp() {
      return this.name;
   }

   public final List<Task> getScheduledTasks() {
      TaskRunner var1 = this.taskRunner;
      synchronized(var1){}

      List var2;
      try {
         var2 = CollectionsKt.toList((Iterable)this.futureTasks);
      } finally {
         ;
      }

      return var2;
   }

   public final boolean getShutdown$okhttp() {
      return this.shutdown;
   }

   public final TaskRunner getTaskRunner$okhttp() {
      return this.taskRunner;
   }

   public final CountDownLatch idleLatch() {
      TaskRunner var1 = this.taskRunner;
      synchronized(var1){}

      CountDownLatch var6;
      try {
         if (this.activeTask == null && this.futureTasks.isEmpty()) {
            var6 = new CountDownLatch(0);
            return var6;
         }

         Task var2 = this.activeTask;
         if (!(var2 instanceof TaskQueue.AwaitIdleTask)) {
            Iterator var3 = this.futureTasks.iterator();

            do {
               if (!var3.hasNext()) {
                  TaskQueue.AwaitIdleTask var7 = new TaskQueue.AwaitIdleTask();
                  if (this.scheduleAndDecide$okhttp((Task)var7, 0L, false)) {
                     this.taskRunner.kickCoordinator$okhttp(this);
                  }

                  var6 = var7.getLatch();
                  return var6;
               }

               var2 = (Task)var3.next();
            } while(!(var2 instanceof TaskQueue.AwaitIdleTask));

            var6 = ((TaskQueue.AwaitIdleTask)var2).getLatch();
            return var6;
         }

         var6 = ((TaskQueue.AwaitIdleTask)var2).getLatch();
      } finally {
         ;
      }

      return var6;
   }

   public final void schedule(final String var1, long var2, final Function0<Long> var4) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var4, "block");
      this.schedule((Task)(new Task(var1) {
         public long runOnce() {
            return ((Number)var4.invoke()).longValue();
         }
      }), var2);
   }

   public final void schedule(Task var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "task");
      TaskRunner var4 = this.taskRunner;
      synchronized(var4){}

      try {
         if (!this.shutdown) {
            if (this.scheduleAndDecide$okhttp(var1, var2, false)) {
               this.taskRunner.kickCoordinator$okhttp(this);
            }

            Unit var8 = Unit.INSTANCE;
            return;
         }

         if (!var1.getCancelable()) {
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var1, this, "schedule failed (queue is shutdown)");
            }

            RejectedExecutionException var7 = new RejectedExecutionException();
            throw (Throwable)var7;
         }

         if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
            TaskLoggerKt.access$log(var1, this, "schedule canceled (queue is shutdown)");
         }
      } finally {
         ;
      }

   }

   public final boolean scheduleAndDecide$okhttp(Task var1, long var2, boolean var4) {
      Intrinsics.checkParameterIsNotNull(var1, "task");
      var1.initQueue$okhttp(this);
      long var5 = this.taskRunner.getBackend().nanoTime();
      long var7 = var5 + var2;
      int var9 = this.futureTasks.indexOf(var1);
      boolean var10 = false;
      if (var9 != -1) {
         if (var1.getNextExecuteNanoTime$okhttp() <= var7) {
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var1, this, "already scheduled");
            }

            return false;
         }

         this.futureTasks.remove(var9);
      }

      var1.setNextExecuteNanoTime$okhttp(var7);
      if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
         StringBuilder var11;
         String var13;
         if (var4) {
            var11 = new StringBuilder();
            var11.append("run again after ");
            var11.append(TaskLoggerKt.formatDuration(var7 - var5));
            var13 = var11.toString();
         } else {
            var11 = new StringBuilder();
            var11.append("scheduled after ");
            var11.append(TaskLoggerKt.formatDuration(var7 - var5));
            var13 = var11.toString();
         }

         TaskLoggerKt.access$log(var1, this, var13);
      }

      Iterator var14 = this.futureTasks.iterator();
      var9 = 0;

      while(true) {
         if (!var14.hasNext()) {
            var9 = -1;
            break;
         }

         boolean var12;
         if (((Task)var14.next()).getNextExecuteNanoTime$okhttp() - var5 > var2) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12) {
            break;
         }

         ++var9;
      }

      int var15 = var9;
      if (var9 == -1) {
         var15 = this.futureTasks.size();
      }

      this.futureTasks.add(var15, var1);
      var4 = var10;
      if (var15 == 0) {
         var4 = true;
      }

      return var4;
   }

   public final void setActiveTask$okhttp(Task var1) {
      this.activeTask = var1;
   }

   public final void setCancelActiveTask$okhttp(boolean var1) {
      this.cancelActiveTask = var1;
   }

   public final void setShutdown$okhttp(boolean var1) {
      this.shutdown = var1;
   }

   public final void shutdown() {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Thread ");
         Thread var6 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var6, "Thread.currentThread()");
         var5.append(var6.getName());
         var5.append(" MUST NOT hold lock on ");
         var5.append(this);
         throw (Throwable)(new AssertionError(var5.toString()));
      } else {
         TaskRunner var2 = this.taskRunner;
         synchronized(var2){}

         try {
            this.shutdown = true;
            if (this.cancelAllAndDecide$okhttp()) {
               this.taskRunner.kickCoordinator$okhttp(this);
            }

            Unit var1 = Unit.INSTANCE;
         } finally {
            ;
         }

      }
   }

   public String toString() {
      return this.name;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"},
      d2 = {"Lokhttp3/internal/concurrent/TaskQueue$AwaitIdleTask;", "Lokhttp3/internal/concurrent/Task;", "()V", "latch", "Ljava/util/concurrent/CountDownLatch;", "getLatch", "()Ljava/util/concurrent/CountDownLatch;", "runOnce", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class AwaitIdleTask extends Task {
      private final CountDownLatch latch;

      public AwaitIdleTask() {
         StringBuilder var1 = new StringBuilder();
         var1.append(Util.okHttpName);
         var1.append(" awaitIdle");
         super(var1.toString(), false);
         this.latch = new CountDownLatch(1);
      }

      public final CountDownLatch getLatch() {
         return this.latch;
      }

      public long runOnce() {
         this.latch.countDown();
         return -1L;
      }
   }
}
