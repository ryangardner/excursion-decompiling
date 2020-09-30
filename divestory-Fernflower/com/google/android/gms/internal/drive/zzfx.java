package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzfx extends AbstractSafeParcelable {
   public static final Creator<zzfx> CREATOR = new zzgc();
   private final boolean zzik;

   public zzfx(boolean var1) {
      this.zzik = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzik);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
