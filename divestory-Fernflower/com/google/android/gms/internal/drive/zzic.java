package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.SearchableMetadataField;

public final class zzic extends com.google.android.gms.drive.metadata.internal.zzb implements SearchableMetadataField<Boolean> {
   public zzic(String var1, int var2) {
      super(var1, 4100000);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return this.zze(var1, var2, var3);
   }

   protected final Boolean zze(DataHolder var1, int var2, int var3) {
      boolean var4;
      if (var1.getInteger(this.getName(), var2, var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
