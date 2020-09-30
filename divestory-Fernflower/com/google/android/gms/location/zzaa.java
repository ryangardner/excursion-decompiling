package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzaa implements Creator<LocationAvailability> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      zzaj[] var5 = null;
      int var6 = 1000;
      int var7 = 1;
      int var8 = 1;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         int var10 = SafeParcelReader.getFieldId(var9);
         if (var10 != 1) {
            if (var10 != 2) {
               if (var10 != 3) {
                  if (var10 != 4) {
                     if (var10 != 5) {
                        SafeParcelReader.skipUnknownField(var1, var9);
                     } else {
                        var5 = (zzaj[])SafeParcelReader.createTypedArray(var1, var9, zzaj.CREATOR);
                     }
                  } else {
                     var6 = SafeParcelReader.readInt(var1, var9);
                  }
               } else {
                  var3 = SafeParcelReader.readLong(var1, var9);
               }
            } else {
               var8 = SafeParcelReader.readInt(var1, var9);
            }
         } else {
            var7 = SafeParcelReader.readInt(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationAvailability(var6, var7, var8, var3, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationAvailability[var1];
   }
}
