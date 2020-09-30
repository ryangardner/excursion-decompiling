package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzj extends AbstractSafeParcelable {
   public static final Creator<zzj> CREATOR = new zzk();
   private final com.google.android.gms.drive.events.zze zzbv;
   final int zzda;
   private final com.google.android.gms.drive.events.zzx zzdb;
   private final com.google.android.gms.drive.events.zzt zzdc;
   final DriveId zzk;

   public zzj(int var1, DriveId var2) {
      this((DriveId)Preconditions.checkNotNull(var2), 1, (com.google.android.gms.drive.events.zze)null, (com.google.android.gms.drive.events.zzx)null, (com.google.android.gms.drive.events.zzt)null);
   }

   zzj(DriveId var1, int var2, com.google.android.gms.drive.events.zze var3, com.google.android.gms.drive.events.zzx var4, com.google.android.gms.drive.events.zzt var5) {
      this.zzk = var1;
      this.zzda = var2;
      this.zzbv = var3;
      this.zzdb = var4;
      this.zzdc = var5;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzk, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzda);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzbv, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzdb, var2, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.zzdc, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
