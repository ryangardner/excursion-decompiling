package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.List;

public final class zzfl extends AbstractSafeParcelable {
   public static final Creator<zzfl> CREATOR = new zzfm();
   private static final List<com.google.android.gms.drive.zzh> zzhx = Collections.emptyList();
   private final int status;
   final long zzhy;
   final long zzhz;
   private final List<com.google.android.gms.drive.zzh> zzia;

   public zzfl(long var1, long var3, int var5, List<com.google.android.gms.drive.zzh> var6) {
      this.zzhy = var1;
      this.zzhz = var3;
      this.status = var5;
      this.zzia = var6;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeLong(var1, 2, this.zzhy);
      SafeParcelWriter.writeLong(var1, 3, this.zzhz);
      SafeParcelWriter.writeInt(var1, 4, this.status);
      SafeParcelWriter.writeTypedList(var1, 5, this.zzia, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
