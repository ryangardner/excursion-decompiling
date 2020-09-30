package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzhf extends AbstractSafeParcelable {
   public static final Creator<zzhf> CREATOR = new zzhg();
   private final DriveId zzdd;
   private final MetadataBundle zzde;

   public zzhf(DriveId var1, MetadataBundle var2) {
      this.zzdd = var1;
      this.zzde = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdd, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzde, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
