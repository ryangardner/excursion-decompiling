package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzs implements Creator<zzr> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      com.google.android.gms.internal.drive.zzh var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 2) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = (com.google.android.gms.internal.drive.zzh)SafeParcelReader.createParcelable(var1, var4, com.google.android.gms.internal.drive.zzh.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzr(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzr[var1];
   }
}
