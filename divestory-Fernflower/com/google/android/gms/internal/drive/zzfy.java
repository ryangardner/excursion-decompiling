package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzfy extends AbstractSafeParcelable {
   public static final Creator<zzfy> CREATOR = new zzfz();
   final MetadataBundle zzdn;

   public zzfy(MetadataBundle var1) {
      this.zzdn = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdn, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final MetadataBundle zzaw() {
      return this.zzdn;
   }
}
