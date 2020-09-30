package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

final class zzi {
   static MetadataField<?> zza(MetadataBundle var0) {
      Set var1 = var0.zzbg();
      if (var1.size() == 1) {
         return (MetadataField)var1.iterator().next();
      } else {
         throw new IllegalArgumentException("bundle should have exactly 1 populated field");
      }
   }
}
