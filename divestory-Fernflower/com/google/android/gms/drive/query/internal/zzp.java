package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;

public final class zzp<T> extends zza {
   public static final zzq CREATOR = new zzq();
   private final MetadataBundle zzma;
   private final com.google.android.gms.drive.metadata.zzb<T> zzmn;

   public zzp(SearchableCollectionMetadataField<T> var1, T var2) {
      this(MetadataBundle.zza(var1, Collections.singleton(var2)));
   }

   zzp(MetadataBundle var1) {
      this.zzma = var1;
      this.zzmn = (com.google.android.gms.drive.metadata.zzb)zzi.zza(var1);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzma, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <F> F zza(zzj<F> var1) {
      com.google.android.gms.drive.metadata.zzb var2 = this.zzmn;
      return var1.zza(var2, ((Collection)this.zzma.zza((MetadataField)var2)).iterator().next());
   }
}
