package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzfm implements Creator<zzfl> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = var3;
      ArrayList var7 = null;
      int var8 = 0;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         int var10 = SafeParcelReader.getFieldId(var9);
         if (var10 != 2) {
            if (var10 != 3) {
               if (var10 != 4) {
                  if (var10 != 5) {
                     SafeParcelReader.skipUnknownField(var1, var9);
                  } else {
                     var7 = SafeParcelReader.createTypedList(var1, var9, com.google.android.gms.drive.zzh.CREATOR);
                  }
               } else {
                  var8 = SafeParcelReader.readInt(var1, var9);
               }
            } else {
               var5 = SafeParcelReader.readLong(var1, var9);
            }
         } else {
            var3 = SafeParcelReader.readLong(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzfl(var3, var5, var8, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzfl[var1];
   }
}
