package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import java.util.Collection;

final class zzht extends com.google.android.gms.drive.metadata.internal.zzb {
   zzht(String var1, Collection var2, Collection var3, int var4) {
      super(var1, var2, var3, 7000000);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return this.zze(var1, var2, var3);
   }

   protected final Boolean zze(DataHolder var1, int var2, int var3) {
      boolean var4;
      if (var1.getInteger("trashed", var2, var3) == 2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
