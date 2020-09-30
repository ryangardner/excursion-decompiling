package com.google.android.gms.tasks;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzi<TResult> implements zzr<TResult> {
   private final Executor zza;
   private final Object zzb = new Object();
   @Nullable
   private OnCompleteListener<TResult> zzc;

   public zzi(Executor var1, OnCompleteListener<TResult> var2) {
      this.zza = var1;
      this.zzc = var2;
   }

   // $FF: synthetic method
   static Object zza(zzi var0) {
      return var0.zzb;
   }

   // $FF: synthetic method
   static OnCompleteListener zzb(zzi var0) {
      return var0.zzc;
   }

   public final void zza() {
      // $FF: Couldn't be decompiled
   }

   public final void zza(Task<TResult> var1) {
      Object var2 = this.zzb;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (this.zzc == null) {
               return;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         try {
            ;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label137;
         }

         this.zza.execute(new zzj(this, var1));
         return;
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }
}
