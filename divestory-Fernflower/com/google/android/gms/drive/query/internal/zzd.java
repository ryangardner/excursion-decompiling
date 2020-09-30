package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzd extends zza {
   public static final Creator<zzd> CREATOR = new zze();
   private final MetadataBundle zzma;
   private final MetadataField<?> zzmb;

   public zzd(SearchableMetadataField<?> var1) {
      this(MetadataBundle.zza(var1, (Object)null));
   }

   zzd(MetadataBundle var1) {
      this.zzma = var1;
      this.zzmb = zzi.zza(var1);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzma, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <T> T zza(zzj<T> var1) {
      return var1.zze(this.zzmb);
   }
}
