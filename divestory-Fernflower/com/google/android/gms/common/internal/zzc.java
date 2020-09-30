package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzc extends AbstractSafeParcelable {
   public static final Creator<zzc> CREATOR = new zzb();
   Bundle zza;
   Feature[] zzb;
   private int zzc;

   public zzc() {
   }

   zzc(Bundle var1, Feature[] var2, int var3, ConnectionTelemetryConfiguration var4) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBundle(var1, 1, this.zza, false);
      SafeParcelWriter.writeTypedArray(var1, 2, this.zzb, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzc);
      SafeParcelWriter.writeParcelable(var1, 4, (Parcelable)null, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
