package com.google.android.gms.tasks;

final class zzn implements Runnable {
   // $FF: synthetic field
   private final Task zza;
   // $FF: synthetic field
   private final zzm zzb;

   zzn(zzm var1, Task var2) {
      this.zzb = var1;
      this.zza = var2;
   }

   public final void run() {
      Object var1 = zzm.zza(this.zzb);
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zzm.zzb(this.zzb) != null) {
               zzm.zzb(this.zzb).onSuccess(this.zza.getResult());
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
