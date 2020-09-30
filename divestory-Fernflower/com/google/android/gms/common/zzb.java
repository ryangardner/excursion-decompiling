package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<Feature> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      int var4 = 0;
      long var5 = -1L;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var7);
               } else {
                  var5 = SafeParcelReader.readLong(var1, var7);
               }
            } else {
               var4 = SafeParcelReader.readInt(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.createString(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new Feature(var3, var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Feature[var1];
   }
}
