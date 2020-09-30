package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;

public final class zzga extends AbstractSafeParcelable {
   public static final Creator<zzga> CREATOR = new zzgb();
   private final zzgo zzil;

   zzga(zzgo var1) {
      this.zzil = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzil, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final TransferPreferences zzax() {
      return this.zzil;
   }
}
