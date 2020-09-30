package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

final class zzf implements Runnable {
   // $FF: synthetic field
   private final Task zza;
   // $FF: synthetic field
   private final zzd zzb;

   zzf(zzd var1, Task var2) {
      this.zzb = var1;
      this.zza = var2;
   }

   public final void run() {
      Task var1;
      try {
         var1 = (Task)zzd.zza(this.zzb).then(this.zza);
      } catch (RuntimeExecutionException var2) {
         if (var2.getCause() instanceof Exception) {
            zzd.zzb(this.zzb).zza((Exception)var2.getCause());
            return;
         }

         zzd.zzb(this.zzb).zza((Exception)var2);
         return;
      } catch (Exception var3) {
         zzd.zzb(this.zzb).zza(var3);
         return;
      }

      if (var1 == null) {
         this.zzb.onFailure(new NullPointerException("Continuation returned null"));
      } else {
         var1.addOnSuccessListener((Executor)TaskExecutors.zza, this.zzb);
         var1.addOnFailureListener((Executor)TaskExecutors.zza, this.zzb);
         var1.addOnCanceledListener((Executor)TaskExecutors.zza, this.zzb);
      }
   }
}
