package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@Deprecated
public final class zzx extends AbstractSafeParcelable {
   public static final Creator<zzx> CREATOR = new zzw();
   private final int zza;

   zzx(int var1) {
      this.zza = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zza);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
