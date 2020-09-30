package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

final class zzd<TResult, TContinuationResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TContinuationResult>, zzr<TResult> {
   private final Executor zza;
   private final Continuation<TResult, Task<TContinuationResult>> zzb;
   private final zzu<TContinuationResult> zzc;

   public zzd(Executor var1, Continuation<TResult, Task<TContinuationResult>> var2, zzu<TContinuationResult> var3) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
   }

   // $FF: synthetic method
   static Continuation zza(zzd var0) {
      return var0.zzb;
   }

   // $FF: synthetic method
   static zzu zzb(zzd var0) {
      return var0.zzc;
   }

   public final void onCanceled() {
      this.zzc.zza();
   }

   public final void onFailure(Exception var1) {
      this.zzc.zza(var1);
   }

   public final void onSuccess(TContinuationResult var1) {
      this.zzc.zza(var1);
   }

   public final void zza() {
      throw new UnsupportedOperationException();
   }

   public final void zza(Task<TResult> var1) {
      this.zza.execute(new zzf(this, var1));
   }
}
