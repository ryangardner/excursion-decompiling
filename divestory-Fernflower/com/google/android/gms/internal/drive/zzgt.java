package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;

public final class zzgt implements Creator<zzgs> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DriveId var3 = null;
      com.google.android.gms.drive.events.zzt var4 = null;
      int var5 = 0;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 2) {
            if (var7 != 3) {
               if (var7 != 4) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var4 = (com.google.android.gms.drive.events.zzt)SafeParcelReader.createParcelable(var1, var6, com.google.android.gms.drive.events.zzt.CREATOR);
               }
            } else {
               var5 = SafeParcelReader.readInt(var1, var6);
            }
         } else {
            var3 = (DriveId)SafeParcelReader.createParcelable(var1, var6, DriveId.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzgs(var3, var5, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzgs[var1];
   }
}
