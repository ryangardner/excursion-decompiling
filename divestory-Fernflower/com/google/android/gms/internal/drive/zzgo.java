package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;

public final class zzgo extends AbstractSafeParcelable implements TransferPreferences {
   public static final Creator<zzgo> CREATOR = new zzgp();
   private final boolean zzbm;
   private final int zzbn;
   private final int zzgy;

   zzgo(int var1, int var2, boolean var3) {
      this.zzgy = var1;
      this.zzbn = var2;
      this.zzbm = var3;
   }

   public final int getBatteryUsagePreference() {
      return this.zzbn;
   }

   public final int getNetworkPreference() {
      return this.zzgy;
   }

   public final boolean isRoamingAllowed() {
      return this.zzbm;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzgy);
      SafeParcelWriter.writeInt(var1, 3, this.zzbn);
      SafeParcelWriter.writeBoolean(var1, 4, this.zzbm);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
