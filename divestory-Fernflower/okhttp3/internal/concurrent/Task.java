package okhttp3.internal.concurrent;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0000¢\u0006\u0002\b\u0019J\b\u0010\u001a\u001a\u00020\fH&J\b\u0010\u001b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u001c"},
   d2 = {"Lokhttp3/internal/concurrent/Task;", "", "name", "", "cancelable", "", "(Ljava/lang/String;Z)V", "getCancelable", "()Z", "getName", "()Ljava/lang/String;", "nextExecuteNanoTime", "", "getNextExecuteNanoTime$okhttp", "()J", "setNextExecuteNanoTime$okhttp", "(J)V", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "getQueue$okhttp", "()Lokhttp3/internal/concurrent/TaskQueue;", "setQueue$okhttp", "(Lokhttp3/internal/concurrent/TaskQueue;)V", "initQueue", "", "initQueue$okhttp", "runOnce", "toString", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class Task {
   private final boolean cancelable;
   private final String name;
   private long nextExecuteNanoTime;
   private TaskQueue queue;

   public Task(String var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      super();
      this.name = var1;
      this.cancelable = var2;
      this.nextExecuteNanoTime = -1L;
   }

   // $FF: synthetic method
   public Task(String var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = true;
      }

      this(var1, var2);
   }

   public final boolean getCancelable() {
      return this.cancelable;
   }

   public final String getName() {
      return this.name;
   }

   public final long getNextExecuteNanoTime$okhttp() {
      return this.nextExecuteNanoTime;
   }

   public final TaskQueue getQueue$okhttp() {
      return this.queue;
   }

   public final void initQueue$okhttp(TaskQueue var1) {
      Intrinsics.checkParameterIsNotNull(var1, "queue");
      TaskQueue var2 = this.queue;
      if (var2 != var1) {
         boolean var3;
         if (var2 == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.queue = var1;
         } else {
            throw (Throwable)(new IllegalStateException("task is in multiple queues".toString()));
         }
      }
   }

   public abstract long runOnce();

   public final void setNextExecuteNanoTime$okhttp(long var1) {
      this.nextExecuteNanoTime = var1;
   }

   public final void setQueue$okhttp(TaskQueue var1) {
      this.queue = var1;
   }

   public String toString() {
      return this.name;
   }
}
