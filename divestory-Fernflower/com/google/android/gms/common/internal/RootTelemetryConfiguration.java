package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class RootTelemetryConfiguration extends AbstractSafeParcelable {
   public static final Creator<RootTelemetryConfiguration> CREATOR = new zzv();
   private final int zza;
   private final boolean zzb;
   private final boolean zzc;
   private final int zzd;
   private final int zze;

   public RootTelemetryConfiguration(int var1, boolean var2, boolean var3, int var4, int var5) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
      this.zzd = var4;
      this.zze = var5;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zza);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzb);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzc);
      SafeParcelWriter.writeInt(var1, 4, this.zzd);
      SafeParcelWriter.writeInt(var1, 5, this.zze);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
