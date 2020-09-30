package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveSpace;
import java.util.ArrayList;

public final class zzf implements Creator<zze> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      ArrayList var4 = null;
      boolean var5 = false;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 2) {
            if (var7 != 3) {
               if (var7 != 4) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var4 = SafeParcelReader.createTypedList(var1, var6, DriveSpace.CREATOR);
               }
            } else {
               var5 = SafeParcelReader.readBoolean(var1, var6);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zze(var3, var5, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zze[var1];
   }
}
