package okhttp3.internal.concurrent;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0001H\u0002\u001a2\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\u0080\b¢\u0006\u0002\u0010\u000f\u001a'\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0080\b¨\u0006\u0012"},
   d2 = {"formatDuration", "", "ns", "", "log", "", "task", "Lokhttp3/internal/concurrent/Task;", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "message", "logElapsed", "T", "block", "Lkotlin/Function0;", "(Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "taskLog", "messageBlock", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class TaskLoggerKt {
   // $FF: synthetic method
   public static final void access$log(Task var0, TaskQueue var1, String var2) {
      log(var0, var1, var2);
   }

   public static final String formatDuration(long var0) {
      StringBuilder var2;
      String var4;
      if (var0 <= (long)-999500000) {
         var2 = new StringBuilder();
         var2.append((var0 - (long)500000000) / (long)1000000000);
         var2.append(" s ");
         var4 = var2.toString();
      } else if (var0 <= (long)-999500) {
         var2 = new StringBuilder();
         var2.append((var0 - (long)500000) / (long)1000000);
         var2.append(" ms");
         var4 = var2.toString();
      } else if (var0 <= 0L) {
         var2 = new StringBuilder();
         var2.append((var0 - (long)500) / (long)1000);
         var2.append(" µs");
         var4 = var2.toString();
      } else if (var0 < (long)999500) {
         var2 = new StringBuilder();
         var2.append((var0 + (long)500) / (long)1000);
         var2.append(" µs");
         var4 = var2.toString();
      } else if (var0 < (long)999500000) {
         var2 = new StringBuilder();
         var2.append((var0 + (long)500000) / (long)1000000);
         var2.append(" ms");
         var4 = var2.toString();
      } else {
         var2 = new StringBuilder();
         var2.append((var0 + (long)500000000) / (long)1000000000);
         var2.append(" s ");
         var4 = var2.toString();
      }

      StringCompanionObject var3 = StringCompanionObject.INSTANCE;
      var4 = String.format("%6s", Arrays.copyOf(new Object[]{var4}, 1));
      Intrinsics.checkExpressionValueIsNotNull(var4, "java.lang.String.format(format, *args)");
      return var4;
   }

   private static final void log(Task var0, TaskQueue var1, String var2) {
      Logger var3 = TaskRunner.Companion.getLogger();
      StringBuilder var4 = new StringBuilder();
      var4.append(var1.getName$okhttp());
      var4.append(' ');
      StringCompanionObject var5 = StringCompanionObject.INSTANCE;
      String var6 = String.format("%-22s", Arrays.copyOf(new Object[]{var2}, 1));
      Intrinsics.checkExpressionValueIsNotNull(var6, "java.lang.String.format(format, *args)");
      var4.append(var6);
      var4.append(": ");
      var4.append(var0.getName());
      var3.fine(var4.toString());
   }

   public static final <T> T logElapsed(Task var0, TaskQueue var1, Function0<? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "task");
      Intrinsics.checkParameterIsNotNull(var1, "queue");
      Intrinsics.checkParameterIsNotNull(var2, "block");
      boolean var3 = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
      long var4;
      if (var3) {
         var4 = var1.getTaskRunner$okhttp().getBackend().nanoTime();
         access$log(var0, var1, "starting");
      } else {
         var4 = -1L;
      }

      boolean var10 = false;

      Object var6;
      long var7;
      StringBuilder var12;
      try {
         var10 = true;
         var6 = var2.invoke();
         var10 = false;
      } finally {
         if (var10) {
            InlineMarker.finallyStart(1);
            if (var3) {
               var7 = var1.getTaskRunner$okhttp().getBackend().nanoTime();
               var12 = new StringBuilder();
               var12.append("failed a run in ");
               var12.append(formatDuration(var7 - var4));
               access$log(var0, var1, var12.toString());
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (var3) {
         var7 = var1.getTaskRunner$okhttp().getBackend().nanoTime();
         var12 = new StringBuilder();
         var12.append("finished run in ");
         var12.append(formatDuration(var7 - var4));
         access$log(var0, var1, var12.toString());
      }

      InlineMarker.finallyEnd(1);
      return var6;
   }

   public static final void taskLog(Task var0, TaskQueue var1, Function0<String> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "task");
      Intrinsics.checkParameterIsNotNull(var1, "queue");
      Intrinsics.checkParameterIsNotNull(var2, "messageBlock");
      if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
         access$log(var0, var1, (String)var2.invoke());
      }

   }
}
