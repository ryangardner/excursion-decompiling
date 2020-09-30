package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzt extends AbstractSafeParcelable {
   public static final Creator<zzt> CREATOR = new zzu();
   private final int zzct;

   public zzt(int var1) {
      this.zzct = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzct);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
