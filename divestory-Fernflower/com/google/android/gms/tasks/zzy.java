package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

final class zzy implements Runnable {
   // $FF: synthetic field
   private final zzu zza;
   // $FF: synthetic field
   private final Callable zzb;

   zzy(zzu var1, Callable var2) {
      this.zza = var1;
      this.zzb = var2;
   }

   public final void run() {
      Exception var1;
      try {
         try {
            this.zza.zza(this.zzb.call());
            return;
         } catch (Exception var4) {
            var1 = var4;
         }
      } catch (Throwable var5) {
         this.zza.zza((Exception)(new RuntimeException(var5)));
         return;
      }

      this.zza.zza(var1);
   }
}
