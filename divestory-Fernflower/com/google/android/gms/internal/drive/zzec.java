package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzec extends AbstractSafeParcelable {
   public static final Creator<zzec> CREATOR = new zzed();
   final IBinder zzgs;

   zzec(IBinder var1) {
      this.zzgs = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeIBinder(var1, 2, this.zzgs, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
