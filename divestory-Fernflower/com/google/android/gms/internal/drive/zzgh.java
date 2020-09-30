package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzgh extends AbstractSafeParcelable {
   public static final Creator<zzgh> CREATOR = new zzgi();
   private final boolean zzea;

   public zzgh(boolean var1) {
      this.zzea = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzea);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
