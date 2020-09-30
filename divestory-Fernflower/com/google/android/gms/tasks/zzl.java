package com.google.android.gms.tasks;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;

final class zzl<TResult> implements zzr<TResult> {
   private final Executor zza;
   private final Object zzb = new Object();
   @Nullable
   private OnFailureListener zzc;

   public zzl(Executor var1, OnFailureListener var2) {
      this.zza = var1;
      this.zzc = var2;
   }

   // $FF: synthetic method
   static Object zza(zzl var0) {
      return var0.zzb;
   }

   // $FF: synthetic method
   static OnFailureListener zzb(zzl var0) {
      return var0.zzc;
   }

   public final void zza() {
      // $FF: Couldn't be decompiled
   }

   public final void zza(Task<TResult> var1) {
      Throwable var10000;
      boolean var10001;
      label173: {
         if (!var1.isSuccessful() && !var1.isCanceled()) {
            Object var2 = this.zzb;
            synchronized(var2){}

            try {
               if (this.zzc == null) {
                  return;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label173;
            }

            try {
               ;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label173;
            }

            this.zza.execute(new zzk(this, var1));
         }

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
