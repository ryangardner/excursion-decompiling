package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.List;

public final class zzgw extends AbstractSafeParcelable {
   public static final Creator<zzgw> CREATOR = new zzgx();
   private final DriveId zzis;
   private final List<DriveId> zzit;

   public zzgw(DriveId var1, List<DriveId> var2) {
      this.zzis = var1;
      this.zzit = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzis, var2, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.zzit, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
