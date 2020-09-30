package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzfj extends AbstractSafeParcelable {
   public static final Creator<zzfj> CREATOR = new zzfk();
   final zzei zzhw;

   public zzfj(zzei var1) {
      this.zzhw = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzhw, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final zzei zzas() {
      return this.zzhw;
   }
}
