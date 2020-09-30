package com.google.android.gms.internal.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

final class zzg implements zzf {
   private zzg() {
   }

   // $FF: synthetic method
   zzg(zzh var1) {
      this();
   }

   public final ScheduledExecutorService zza(int var1, int var2) {
      return Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1));
   }
}
