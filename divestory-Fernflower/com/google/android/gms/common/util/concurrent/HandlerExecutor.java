package com.google.android.gms.common.util.concurrent;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.internal.common.zzi;
import java.util.concurrent.Executor;

public class HandlerExecutor implements Executor {
   private final Handler zza;

   public HandlerExecutor(Looper var1) {
      this.zza = new zzi(var1);
   }

   public void execute(Runnable var1) {
      this.zza.post(var1);
   }
}
