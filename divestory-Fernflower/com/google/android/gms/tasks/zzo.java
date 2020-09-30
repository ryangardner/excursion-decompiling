package com.google.android.gms.tasks;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

final class zzo implements Runnable {
   // $FF: synthetic field
   private final Task zza;
   // $FF: synthetic field
   private final zzp zzb;

   zzo(zzp var1, Task var2) {
      this.zzb = var1;
      this.zza = var2;
   }

   public final void run() {
      Task var1;
      try {
         var1 = zzp.zza(this.zzb).then(this.zza.getResult());
      } catch (RuntimeExecutionException var2) {
         if (var2.getCause() instanceof Exception) {
            this.zzb.onFailure((Exception)var2.getCause());
            return;
         }

         this.zzb.onFailure(var2);
         return;
      } catch (CancellationException var3) {
         this.zzb.onCanceled();
         return;
      } catch (Exception var4) {
         this.zzb.onFailure(var4);
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
