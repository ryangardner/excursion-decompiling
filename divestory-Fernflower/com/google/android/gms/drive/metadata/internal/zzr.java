package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzr implements Creator<zzq> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      long var4 = 0L;
      int var6 = -1;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 2) {
            if (var8 != 3) {
               if (var8 != 4) {
                  SafeParcelReader.skipUnknownField(var1, var7);
               } else {
                  var6 = SafeParcelReader.readInt(var1, var7);
               }
            } else {
               var4 = SafeParcelReader.readLong(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.createString(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzq(var3, var4, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzq[var1];
   }
}
