package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzfv extends com.google.android.gms.drive.zzu {
   public static final Creator<zzfv> CREATOR = new zzfw();
   final DataHolder zzij;

   public zzfv(DataHolder var1) {
      this.zzij = var1;
   }

   protected final void zza(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzij, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final DataHolder zzav() {
      return this.zzij;
   }
}
