package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzk implements Creator<zzj> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 50L;
      long var5 = Long.MAX_VALUE;
      boolean var7 = true;
      float var8 = 0.0F;
      int var9 = Integer.MAX_VALUE;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         int var11 = SafeParcelReader.getFieldId(var10);
         if (var11 != 1) {
            if (var11 != 2) {
               if (var11 != 3) {
                  if (var11 != 4) {
                     if (var11 != 5) {
                        SafeParcelReader.skipUnknownField(var1, var10);
                     } else {
                        var9 = SafeParcelReader.readInt(var1, var10);
                     }
                  } else {
                     var5 = SafeParcelReader.readLong(var1, var10);
                  }
               } else {
                  var8 = SafeParcelReader.readFloat(var1, var10);
               }
            } else {
               var3 = SafeParcelReader.readLong(var1, var10);
            }
         } else {
            var7 = SafeParcelReader.readBoolean(var1, var10);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzj(var7, var3, var8, var5, var9);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzj[var1];
   }
}
