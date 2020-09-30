package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzh extends AbstractSafeParcelable {
   public static final Creator<zzh> CREATOR = new zzi();
   private final long zzab;
   private final long zzac;

   public zzh(long var1, long var3) {
      this.zzab = var1;
      this.zzac = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeLong(var1, 2, this.zzab);
      SafeParcelWriter.writeLong(var1, 3, this.zzac);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
