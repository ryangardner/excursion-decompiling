package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzft extends com.google.android.gms.drive.zzu {
   public static final Creator<zzft> CREATOR = new zzfu();
   final boolean zzea;
   final DataHolder zzii;

   public zzft(DataHolder var1, boolean var2) {
      this.zzii = var1;
      this.zzea = var2;
   }

   protected final void zza(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzii, var2, false);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzea);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final DataHolder zzau() {
      return this.zzii;
   }
}
