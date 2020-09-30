package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzad extends AbstractSafeParcelable implements Result {
   public static final Creator<zzad> CREATOR;
   private static final zzad zzcr;
   private final Status zzbl;

   static {
      zzcr = new zzad(Status.RESULT_SUCCESS);
      CREATOR = new zzae();
   }

   public zzad(Status var1) {
      this.zzbl = var1;
   }

   public final Status getStatus() {
      return this.zzbl;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.getStatus(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
