package com.google.android.gms.tasks;

public class TaskCompletionSource<TResult> {
   private final zzu<TResult> zza = new zzu();

   public TaskCompletionSource() {
   }

   public TaskCompletionSource(CancellationToken var1) {
      var1.onCanceledRequested(new zzs(this));
   }

   // $FF: synthetic method
   static zzu zza(TaskCompletionSource var0) {
      return var0.zza;
   }

   public Task<TResult> getTask() {
      return this.zza;
   }

   public void setException(Exception var1) {
      this.zza.zza(var1);
   }

   public void setResult(TResult var1) {
      this.zza.zza(var1);
   }

   public boolean trySetException(Exception var1) {
      return this.zza.zzb(var1);
   }

   public boolean trySetResult(TResult var1) {
      return this.zza.zzb(var1);
   }
}
