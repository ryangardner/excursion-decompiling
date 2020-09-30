package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements zzr<TResult> {
   private final Executor zza;
   private final Continuation<TResult, TContinuationResult> zzb;
   private final zzu<TContinuationResult> zzc;

   public zzc(Executor var1, Continuation<TResult, TContinuationResult> var2, zzu<TContinuationResult> var3) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
   }

   // $FF: synthetic method
   static zzu zza(zzc var0) {
      return var0.zzc;
   }

   // $FF: synthetic method
   static Continuation zzb(zzc var0) {
      return var0.zzb;
   }

   public final void zza() {
      throw new UnsupportedOperationException();
   }

   public final void zza(Task<TResult> var1) {
      this.zza.execute(new zze(this, var1));
   }
}
