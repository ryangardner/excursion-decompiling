package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzl extends zza {
   public static final Creator<zzl> CREATOR = new zzm();
   private final String value;

   public zzl(String var1) {
      this.value = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.value, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final <F> F zza(zzj<F> var1) {
      return var1.zzi(this.value);
   }
}
