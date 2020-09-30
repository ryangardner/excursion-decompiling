package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzex extends AbstractSafeParcelable {
   public static final Creator<zzex> CREATOR = new zzey();
   private final DriveId zzhc;

   public zzex(DriveId var1) {
      this.zzhc = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzhc, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
