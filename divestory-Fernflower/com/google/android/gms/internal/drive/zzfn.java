package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzfn extends AbstractSafeParcelable {
   public static final Creator<zzfn> CREATOR = new zzfo();
   DriveId zzdd;

   public zzfn(DriveId var1) {
      this.zzdd = var1;
   }

   public final DriveId getDriveId() {
      return this.zzdd;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdd, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
