package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzi implements Creator<zzh> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = 0L;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 2) {
            if (var8 != 3) {
               SafeParcelReader.skipUnknownField(var1, var7);
            } else {
               var5 = SafeParcelReader.readLong(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.readLong(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzh(var3, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzh[var1];
   }
}
