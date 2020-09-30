package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Query;

public final class zzgq extends AbstractSafeParcelable {
   public static final Creator<zzgq> CREATOR = new zzgr();
   private final Query zzir;

   public zzgq(Query var1) {
      this.zzir = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzir, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
