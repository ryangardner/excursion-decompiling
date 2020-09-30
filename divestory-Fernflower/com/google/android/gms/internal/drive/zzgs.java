package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzgs extends AbstractSafeParcelable {
   public static final Creator<zzgs> CREATOR = new zzgt();
   private final int zzda;
   private final com.google.android.gms.drive.events.zzt zzdc;
   private final DriveId zzk;

   public zzgs(DriveId var1, int var2) {
      this(var1, var2, (com.google.android.gms.drive.events.zzt)null);
   }

   zzgs(DriveId var1, int var2, com.google.android.gms.drive.events.zzt var3) {
      this.zzk = var1;
      this.zzda = var2;
      this.zzdc = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzk, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzda);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzdc, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
