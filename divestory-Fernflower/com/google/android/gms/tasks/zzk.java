package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;

final class zzk implements Runnable {
   // $FF: synthetic field
   private final Task zza;
   // $FF: synthetic field
   private final zzl zzb;

   zzk(zzl var1, Task var2) {
      this.zzb = var1;
      this.zza = var2;
   }

   public final void run() {
      Object var1 = zzl.zza(this.zzb);
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zzl.zzb(this.zzb) != null) {
               zzl.zzb(this.zzb).onFailure((Exception)Preconditions.checkNotNull(this.zza.getException()));
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }
}
