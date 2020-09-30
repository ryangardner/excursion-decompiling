package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

public final class zzem extends AbstractSafeParcelable {
   public static final Creator<zzem> CREATOR = new zzen();
   private final int responseCode;
   private final List<com.google.android.gms.drive.zzr> zzhb;

   public zzem(List<com.google.android.gms.drive.zzr> var1, int var2) {
      this.zzhb = var1;
      this.responseCode = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzhb, false);
      SafeParcelWriter.writeInt(var1, 3, this.responseCode);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
