package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0000H\u0016J\b\u0010\t\u001a\u00020\u0000H\u0016J\u0016\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001f\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012H\u0086\bJ\b\u0010\u0013\u001a\u00020\u000fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokio/Timeout;", "", "()V", "deadlineNanoTime", "", "hasDeadline", "", "timeoutNanos", "clearDeadline", "clearTimeout", "deadline", "duration", "unit", "Ljava/util/concurrent/TimeUnit;", "intersectWith", "", "other", "block", "Lkotlin/Function0;", "throwIfReached", "timeout", "waitUntilNotified", "monitor", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public class Timeout {
   public static final Timeout.Companion Companion = new Timeout.Companion((DefaultConstructorMarker)null);
   public static final Timeout NONE = (Timeout)(new Timeout() {
      public Timeout deadlineNanoTime(long var1) {
         return (Timeout)this;
      }

      public void throwIfReached() {
      }

      public Timeout timeout(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         return (Timeout)this;
      }
   });
   private long deadlineNanoTime;
   private boolean hasDeadline;
   private long timeoutNanos;

   public Timeout clearDeadline() {
      this.hasDeadline = false;
      return this;
   }

   public Timeout clearTimeout() {
      this.timeoutNanos = 0L;
      return this;
   }

   public final Timeout deadline(long var1, TimeUnit var3) {
      Intrinsics.checkParameterIsNotNull(var3, "unit");
      boolean var4;
      if (var1 > 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         return this.deadlineNanoTime(System.nanoTime() + var3.toNanos(var1));
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("duration <= 0: ");
         var5.append(var1);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public long deadlineNanoTime() {
      if (this.hasDeadline) {
         return this.deadlineNanoTime;
      } else {
         throw (Throwable)(new IllegalStateException("No deadline".toString()));
      }
   }

   public Timeout deadlineNanoTime(long var1) {
      this.hasDeadline = true;
      this.deadlineNanoTime = var1;
      return this;
   }

   public boolean hasDeadline() {
      return this.hasDeadline;
   }

   public final void intersectWith(Timeout var1, Function0<Unit> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      Intrinsics.checkParameterIsNotNull(var2, "block");
      long var3 = this.timeoutNanos();
      this.timeout(Companion.minTimeout(var1.timeoutNanos(), this.timeoutNanos()), TimeUnit.NANOSECONDS);
      if (this.hasDeadline()) {
         long var5 = this.deadlineNanoTime();
         if (var1.hasDeadline()) {
            this.deadlineNanoTime(Math.min(this.deadlineNanoTime(), var1.deadlineNanoTime()));
         }

         try {
            var2.invoke();
         } finally {
            InlineMarker.finallyStart(1);
            this.timeout(var3, TimeUnit.NANOSECONDS);
            if (var1.hasDeadline()) {
               this.deadlineNanoTime(var5);
            }

            InlineMarker.finallyEnd(1);
         }
      } else {
         if (var1.hasDeadline()) {
            this.deadlineNanoTime(var1.deadlineNanoTime());
         }

         try {
            var2.invoke();
         } finally {
            InlineMarker.finallyStart(1);
            this.timeout(var3, TimeUnit.NANOSECONDS);
            if (var1.hasDeadline()) {
               this.clearDeadline();
            }

            InlineMarker.finallyEnd(1);
         }
      }

   }

   public void throwIfReached() throws IOException {
      if (!Thread.interrupted()) {
         if (this.hasDeadline && this.deadlineNanoTime - System.nanoTime() <= 0L) {
            throw (Throwable)(new InterruptedIOException("deadline reached"));
         }
      } else {
         Thread.currentThread().interrupt();
         throw (Throwable)(new InterruptedIOException("interrupted"));
      }
   }

   public Timeout timeout(long var1, TimeUnit var3) {
      Intrinsics.checkParameterIsNotNull(var3, "unit");
      boolean var4;
      if (var1 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         this.timeoutNanos = var3.toNanos(var1);
         return this;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("timeout < 0: ");
         var5.append(var1);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public long timeoutNanos() {
      return this.timeoutNanos;
   }

   public final void waitUntilNotified(Object var1) throws InterruptedIOException {
      Intrinsics.checkParameterIsNotNull(var1, "monitor");

      label78: {
         boolean var10001;
         boolean var2;
         long var3;
         try {
            var2 = this.hasDeadline();
            var3 = this.timeoutNanos();
         } catch (InterruptedException var17) {
            var10001 = false;
            break label78;
         }

         long var5 = 0L;
         if (!var2 && var3 == 0L) {
            try {
               var1.wait();
               return;
            } catch (InterruptedException var10) {
               var10001 = false;
            }
         } else {
            label81: {
               long var7;
               try {
                  var7 = System.nanoTime();
               } catch (InterruptedException var16) {
                  var10001 = false;
                  break label81;
               }

               if (var2 && var3 != 0L) {
                  try {
                     var3 = Math.min(var3, this.deadlineNanoTime() - var7);
                  } catch (InterruptedException var15) {
                     var10001 = false;
                     break label81;
                  }
               } else if (var2) {
                  try {
                     var3 = this.deadlineNanoTime() - var7;
                  } catch (InterruptedException var14) {
                     var10001 = false;
                     break label81;
                  }
               }

               if (var3 > 0L) {
                  try {
                     var5 = var3 / 1000000L;
                  } catch (InterruptedException var13) {
                     var10001 = false;
                     break label81;
                  }

                  Long.signum(var5);
                  int var9 = (int)(var3 - 1000000L * var5);

                  try {
                     var1.wait(var5, var9);
                     var5 = System.nanoTime() - var7;
                  } catch (InterruptedException var12) {
                     var10001 = false;
                     break label81;
                  }
               }

               if (var5 < var3) {
                  return;
               }

               try {
                  InterruptedIOException var18 = new InterruptedIOException("timeout");
                  throw (Throwable)var18;
               } catch (InterruptedException var11) {
                  var10001 = false;
               }
            }
         }
      }

      Thread.currentThread().interrupt();
      throw (Throwable)(new InterruptedIOException("interrupted"));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
      d2 = {"Lokio/Timeout$Companion;", "", "()V", "NONE", "Lokio/Timeout;", "minTimeout", "", "aNanos", "bNanos", "okio"},
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

      public final long minTimeout(long var1, long var3) {
         if (var1 == 0L || var3 != 0L && var1 >= var3) {
            var1 = var3;
         }

         return var1;
      }
   }
}
