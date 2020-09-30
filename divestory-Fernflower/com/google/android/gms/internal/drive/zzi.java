package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;

public final class zzi implements Creator<zzh> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = var3;
      DriveId var7 = null;
      int var8 = 0;
      int var9 = 0;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         int var11 = SafeParcelReader.getFieldId(var10);
         if (var11 != 2) {
            if (var11 != 3) {
               if (var11 != 4) {
                  if (var11 != 5) {
                     if (var11 != 6) {
                        SafeParcelReader.skipUnknownField(var1, var10);
                     } else {
                        var5 = SafeParcelReader.readLong(var1, var10);
                     }
                  } else {
                     var3 = SafeParcelReader.readLong(var1, var10);
                  }
               } else {
                  var9 = SafeParcelReader.readInt(var1, var10);
               }
            } else {
               var7 = (DriveId)SafeParcelReader.createParcelable(var1, var10, DriveId.CREATOR);
            }
         } else {
            var8 = SafeParcelReader.readInt(var1, var10);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzh(var8, var7, var9, var3, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzh[var1];
   }
}
