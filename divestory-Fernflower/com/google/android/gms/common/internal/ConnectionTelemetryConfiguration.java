package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class ConnectionTelemetryConfiguration extends AbstractSafeParcelable {
   public static final Creator<ConnectionTelemetryConfiguration> CREATOR = new zzd();
   private final RootTelemetryConfiguration zza;
   private final boolean zzb;
   private final boolean zzc;
   private final int[] zzd;
   private final int zze;

   ConnectionTelemetryConfiguration(RootTelemetryConfiguration var1, boolean var2, boolean var3, int[] var4, int var5) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
      this.zzd = var4;
      this.zze = var5;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zza, var2, false);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzb);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzc);
      SafeParcelWriter.writeIntArray(var1, 4, this.zzd, false);
      SafeParcelWriter.writeInt(var1, 5, this.zze);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
