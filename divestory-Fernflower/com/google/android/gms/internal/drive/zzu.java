package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzu extends AbstractSafeParcelable {
   public static final Creator<zzu> CREATOR = new zzv();
   private final String zzba;
   private final DriveId zzbd;
   private final MetadataBundle zzdn;
   private final Integer zzdo;
   private final int zzj;

   public zzu(MetadataBundle var1, int var2, String var3, DriveId var4, Integer var5) {
      this.zzdn = var1;
      this.zzj = var2;
      this.zzba = var3;
      this.zzbd = var4;
      this.zzdo = var5;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdn, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzj);
      SafeParcelWriter.writeString(var1, 4, this.zzba, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzbd, var2, false);
      SafeParcelWriter.writeIntegerObject(var1, 6, this.zzdo, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
