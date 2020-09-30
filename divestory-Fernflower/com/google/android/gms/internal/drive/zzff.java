package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.List;

public final class zzff extends com.google.android.gms.drive.zzu {
   public static final Creator<zzff> CREATOR = new zzfg();
   private final DataHolder zzhr;
   private final List<DriveId> zzhs;
   private final com.google.android.gms.drive.zza zzht;
   private final boolean zzhu;

   public zzff(DataHolder var1, List<DriveId> var2, com.google.android.gms.drive.zza var3, boolean var4) {
      this.zzhr = var1;
      this.zzhs = var2;
      this.zzht = var3;
      this.zzhu = var4;
   }

   protected final void zza(Parcel var1, int var2) {
      var2 |= 1;
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzhr, var2, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.zzhs, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzht, var2, false);
      SafeParcelWriter.writeBoolean(var1, 5, this.zzhu);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
