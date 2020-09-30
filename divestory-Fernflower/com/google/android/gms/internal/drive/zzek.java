package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzek extends AbstractSafeParcelable {
   public static final Creator<zzek> CREATOR = new zzel();
   private final DriveId zzdd;
   private final boolean zzha;

   public zzek(DriveId var1, boolean var2) {
      this.zzdd = var1;
      this.zzha = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdd, var2, false);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzha);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
