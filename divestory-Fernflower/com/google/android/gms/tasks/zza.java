package com.google.android.gms.tasks;

final class zza extends CancellationToken {
   private final zzu<Void> zza = new zzu();

   public final boolean isCancellationRequested() {
      return this.zza.isComplete();
   }

   public final CancellationToken onCanceledRequested(OnTokenCanceledListener var1) {
      this.zza.addOnSuccessListener(new zzb(this, var1));
      return this;
   }

   public final void zza() {
      this.zza.zzb((Object)null);
   }
}
