package com.google.android.gms.common.util.concurrent;

import android.os.Process;

final class zza implements Runnable {
   private final Runnable zza;
   private final int zzb;

   public zza(Runnable var1, int var2) {
      this.zza = var1;
      this.zzb = 0;
   }

   public final void run() {
      Process.setThreadPriority(this.zzb);
      this.zza.run();
   }
}
