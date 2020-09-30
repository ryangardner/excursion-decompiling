package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzk implements Creator<DriveId> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = var3;
      String var7 = null;
      int var8 = -1;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         int var10 = SafeParcelReader.getFieldId(var9);
         if (var10 != 2) {
            if (var10 != 3) {
               if (var10 != 4) {
                  if (var10 != 5) {
                     SafeParcelReader.skipUnknownField(var1, var9);
                  } else {
                     var8 = SafeParcelReader.readInt(var1, var9);
                  }
               } else {
                  var5 = SafeParcelReader.readLong(var1, var9);
               }
            } else {
               var3 = SafeParcelReader.readLong(var1, var9);
            }
         } else {
            var7 = SafeParcelReader.createString(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new DriveId(var7, var3, var5, var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new DriveId[var1];
   }
}
