package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;

public final class zzgk implements Creator<zzgj> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      DriveId var4 = null;
      int var5 = 0;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 2) {
            if (var7 != 3) {
               if (var7 != 4) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.readInt(var1, var6);
               }
            } else {
               var3 = SafeParcelReader.readInt(var1, var6);
            }
         } else {
            var4 = (DriveId)SafeParcelReader.createParcelable(var1, var6, DriveId.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzgj(var4, var3, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzgj[var1];
   }
}
