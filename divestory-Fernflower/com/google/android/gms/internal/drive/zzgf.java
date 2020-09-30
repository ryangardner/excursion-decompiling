package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzgf extends AbstractSafeParcelable {
   public static final Creator<zzgf> CREATOR = new zzgg();
   private final ParcelFileDescriptor zzin;
   private final IBinder zzio;
   private final String zzm;

   zzgf(ParcelFileDescriptor var1, IBinder var2, String var3) {
      this.zzin = var1;
      this.zzio = var2;
      this.zzm = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzin, var2 | 1, false);
      SafeParcelWriter.writeIBinder(var1, 3, this.zzio, false);
      SafeParcelWriter.writeString(var1, 4, this.zzm, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
