package com.google.android.gms.dynamite;

import android.content.Context;

final class zzc implements DynamiteModule.VersionPolicy {
   public final DynamiteModule.VersionPolicy.zza zza(Context var1, String var2, DynamiteModule.VersionPolicy.zzb var3) throws DynamiteModule.LoadingException {
      DynamiteModule.VersionPolicy.zza var4 = new DynamiteModule.VersionPolicy.zza();
      var4.zzb = var3.zza(var1, var2, false);
      if (var4.zzb == 0) {
         var4.zzc = 0;
      } else {
         var4.zzc = 1;
      }

      return var4;
   }
}
