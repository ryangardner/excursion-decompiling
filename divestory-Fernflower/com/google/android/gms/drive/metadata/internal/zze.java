package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import java.util.Date;

public class zze extends com.google.android.gms.drive.metadata.zzd<Date> {
   public zze(String var1, int var2) {
      super(var1, var2);
   }

   // $FF: synthetic method
   protected final void zza(Bundle var1, Object var2) {
      Date var3 = (Date)var2;
      var1.putLong(this.getName(), var3.getTime());
   }

   // $FF: synthetic method
   protected final Object zzb(Bundle var1) {
      return new Date(var1.getLong(this.getName()));
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return new Date(var1.getLong(this.getName(), var2, var3));
   }
}
