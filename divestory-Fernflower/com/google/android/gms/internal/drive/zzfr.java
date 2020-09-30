package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzfr extends AbstractSafeParcelable {
   public static final Creator<zzfr> CREATOR = new zzfs();
   private final ParcelFileDescriptor zzih;

   public zzfr(ParcelFileDescriptor var1) {
      this.zzih = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzih, var2 | 1, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
