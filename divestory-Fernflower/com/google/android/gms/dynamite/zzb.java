package com.google.android.gms.dynamite;

import android.content.Context;

final class zzb implements DynamiteModule.VersionPolicy.zzb {
   public final int zza(Context var1, String var2) {
      return DynamiteModule.getLocalVersion(var1, var2);
   }

   public final int zza(Context var1, String var2, boolean var3) throws DynamiteModule.LoadingException {
      return DynamiteModule.zza(var1, var2, var3);
   }
}
