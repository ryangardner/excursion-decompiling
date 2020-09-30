package com.google.android.gms.tasks;

final class zzb implements OnSuccessListener<Void> {
   // $FF: synthetic field
   private final OnTokenCanceledListener zza;

   zzb(zza var1, OnTokenCanceledListener var2) {
      this.zza = var2;
   }

   // $FF: synthetic method
   public final void onSuccess(Object var1) {
      Void var2 = (Void)var1;
      this.zza.onCanceled();
   }
}
