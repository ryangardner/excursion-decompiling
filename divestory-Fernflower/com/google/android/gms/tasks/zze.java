package com.google.android.gms.tasks;

final class zze implements Runnable {
   // $FF: synthetic field
   private final Task zza;
   // $FF: synthetic field
   private final zzc zzb;

   zze(zzc var1, Task var2) {
      this.zzb = var1;
      this.zza = var2;
   }

   public final void run() {
      if (this.zza.isCanceled()) {
         zzc.zza(this.zzb).zza();
      } else {
         Object var1;
         try {
            var1 = zzc.zzb(this.zzb).then(this.zza);
         } catch (RuntimeExecutionException var2) {
            if (var2.getCause() instanceof Exception) {
               zzc.zza(this.zzb).zza((Exception)var2.getCause());
               return;
            }

            zzc.zza(this.zzb).zza((Exception)var2);
            return;
         } catch (Exception var3) {
            zzc.zza(this.zzb).zza(var3);
            return;
         }

         zzc.zza(this.zzb).zza(var1);
      }
   }
}
