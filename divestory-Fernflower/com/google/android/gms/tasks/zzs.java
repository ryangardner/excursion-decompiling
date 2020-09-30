package com.google.android.gms.tasks;

final class zzs implements OnTokenCanceledListener {
   // $FF: synthetic field
   private final TaskCompletionSource zza;

   zzs(TaskCompletionSource var1) {
      this.zza = var1;
   }

   public final void onCanceled() {
      TaskCompletionSource.zza(this.zza).zza();
   }
}
