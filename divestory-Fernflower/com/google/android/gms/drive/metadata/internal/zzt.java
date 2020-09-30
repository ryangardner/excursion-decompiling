package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;

public class zzt extends com.google.android.gms.drive.metadata.zza<String> {
   public zzt(String var1, int var2) {
      super(var1, var2);
   }

   // $FF: synthetic method
   protected final void zza(Bundle var1, Object var2) {
      String var3 = (String)var2;
      var1.putString(this.getName(), var3);
   }

   // $FF: synthetic method
   protected final Object zzb(Bundle var1) {
      return var1.getString(this.getName());
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return var1.getString(this.getName(), var2, var3);
   }
}
