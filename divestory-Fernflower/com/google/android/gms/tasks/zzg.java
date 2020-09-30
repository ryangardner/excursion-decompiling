package com.google.android.gms.tasks;

final class zzg implements Runnable {
   // $FF: synthetic field
   private final zzh zza;

   zzg(zzh var1) {
      this.zza = var1;
   }

   public final void run() {
      Object var1 = zzh.zza(this.zza);
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zzh.zzb(this.zza) != null) {
               zzh.zzb(this.zza).onCanceled();
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
