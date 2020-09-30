package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.query.internal.FilterHolder;

public final class zzgm extends AbstractSafeParcelable {
   public static final Creator<zzgm> CREATOR = new zzgn();
   private final String zzba;
   private final String[] zzbb;
   private final DriveId zzbd;
   private final FilterHolder zzbe;

   public zzgm(String var1, String[] var2, DriveId var3, FilterHolder var4) {
      this.zzba = var1;
      this.zzbb = var2;
      this.zzbd = var3;
      this.zzbe = var4;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.zzba, false);
      SafeParcelWriter.writeStringArray(var1, 3, this.zzbb, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzbd, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzbe, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
