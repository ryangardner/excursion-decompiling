package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzaa extends Metadata {
   private final MetadataBundle zzdt;

   public zzaa(MetadataBundle var1) {
      this.zzdt = var1;
   }

   // $FF: synthetic method
   public final Object freeze() {
      return new zzaa(this.zzdt.zzbf());
   }

   public final boolean isDataValid() {
      return this.zzdt != null;
   }

   public final String toString() {
      String var1 = String.valueOf(this.zzdt);
      StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 17);
      var2.append("Metadata [mImpl=");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public final <T> T zza(MetadataField<T> var1) {
      return this.zzdt.zza(var1);
   }
}
