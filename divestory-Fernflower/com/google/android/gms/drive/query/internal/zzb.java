package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzb<T> extends zza {
   public static final zzc CREATOR = new zzc();
   private final zzx zzlz;
   private final MetadataBundle zzma;
   private final MetadataField<T> zzmb;

   public zzb(zzx var1, SearchableMetadataField<T> var2, T var3) {
      this(var1, MetadataBundle.zza(var2, var3));
   }

   zzb(zzx var1, MetadataBundle var2) {
      this.zzlz = var1;
      this.zzma = var2;
      this.zzmb = zzi.zza(var2);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzlz, var2, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzma, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <F> F zza(zzj<F> var1) {
      zzx var2 = this.zzlz;
      MetadataField var3 = this.zzmb;
      return var1.zza(var2, var3, this.zzma.zza(var3));
   }
}
