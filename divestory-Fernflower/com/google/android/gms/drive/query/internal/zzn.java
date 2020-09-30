package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzn<T> extends zza {
   public static final zzo CREATOR = new zzo();
   private final MetadataBundle zzma;
   private final MetadataField<T> zzmb;

   public zzn(SearchableMetadataField<T> var1, T var2) {
      this(MetadataBundle.zza(var1, var2));
   }

   zzn(MetadataBundle var1) {
      this.zzma = var1;
      this.zzmb = zzi.zza(var1);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzma, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <F> F zza(zzj<F> var1) {
      MetadataField var2 = this.zzmb;
      return var1.zzc(var2, this.zzma.zza(var2));
   }
}
