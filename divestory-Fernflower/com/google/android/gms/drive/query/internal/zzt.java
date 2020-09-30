package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzt extends zza {
   public static final Creator<zzt> CREATOR = new zzu();

   public final void writeToParcel(Parcel var1, int var2) {
      SafeParcelWriter.finishObjectHeader(var1, SafeParcelWriter.beginObjectHeader(var1));
   }

   public final <F> F zza(zzj<F> var1) {
      return var1.zzbk();
   }
}
