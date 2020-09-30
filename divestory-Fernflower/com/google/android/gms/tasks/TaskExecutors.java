package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public final class TaskExecutors {
   public static final Executor MAIN_THREAD = new TaskExecutors.zza();
   static final Executor zza = new zzt();

   private TaskExecutors() {
   }

   private static final class zza implements Executor {
      private final Handler zza = new com.google.android.gms.internal.tasks.zzb(Looper.getMainLooper());

      public zza() {
      }

      public final void execute(Runnable var1) {
         this.zza.post(var1);
      }
   }
}
