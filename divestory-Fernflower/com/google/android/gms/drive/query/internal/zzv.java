package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;

public final class zzv extends zza {
   public static final Creator<zzv> CREATOR = new zzw();
   private final FilterHolder zzmp;

   public zzv(Filter var1) {
      this(new FilterHolder(var1));
   }

   zzv(FilterHolder var1) {
      this.zzmp = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzmp, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <T> T zza(zzj<T> var1) {
      return var1.zza(this.zzmp.getFilter().zza(var1));
   }
}
