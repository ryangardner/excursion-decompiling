package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzgj extends AbstractSafeParcelable {
   public static final Creator<zzgj> CREATOR = new zzgk();
   private final int mode;
   private final DriveId zzdd;
   private final int zzip;

   public zzgj(DriveId var1, int var2, int var3) {
      this.zzdd = var1;
      this.mode = var2;
      this.zzip = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdd, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.mode);
      SafeParcelWriter.writeInt(var1, 4, this.zzip);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
