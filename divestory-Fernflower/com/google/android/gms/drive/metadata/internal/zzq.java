package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzq extends AbstractSafeParcelable {
   public static final Creator<zzq> CREATOR = new zzr();
   final String zzad;
   final long zzae;
   final int zzaf;

   public zzq(String var1, long var2, int var4) {
      this.zzad = var1;
      this.zzae = var2;
      this.zzaf = var4;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.zzad, false);
      SafeParcelWriter.writeLong(var1, 3, this.zzae);
      SafeParcelWriter.writeInt(var1, 4, this.zzaf);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
