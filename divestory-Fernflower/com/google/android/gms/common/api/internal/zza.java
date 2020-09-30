package com.google.android.gms.common.api.internal;

import android.os.Bundle;

final class zza implements Runnable {
   // $FF: synthetic field
   private final LifecycleCallback zza;
   // $FF: synthetic field
   private final String zzb;
   // $FF: synthetic field
   private final zzb zzc;

   zza(zzb var1, LifecycleCallback var2, String var3) {
      this.zzc = var1;
      this.zza = var2;
      this.zzb = var3;
   }

   public final void run() {
      if (com.google.android.gms.common.api.internal.zzb.zza(this.zzc) > 0) {
         LifecycleCallback var1 = this.zza;
         Bundle var2;
         if (com.google.android.gms.common.api.internal.zzb.zzb(this.zzc) != null) {
            var2 = com.google.android.gms.common.api.internal.zzb.zzb(this.zzc).getBundle(this.zzb);
         } else {
            var2 = null;
         }

         var1.onCreate(var2);
      }

      if (com.google.android.gms.common.api.internal.zzb.zza(this.zzc) >= 2) {
         this.zza.onStart();
      }

      if (com.google.android.gms.common.api.internal.zzb.zza(this.zzc) >= 3) {
         this.zza.onResume();
      }

      if (com.google.android.gms.common.api.internal.zzb.zza(this.zzc) >= 4) {
         this.zza.onStop();
      }

      if (com.google.android.gms.common.api.internal.zzb.zza(this.zzc) >= 5) {
         this.zza.onDestroy();
      }

   }
}
