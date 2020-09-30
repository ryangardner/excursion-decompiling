package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;

public final class zzfg implements Creator<zzff> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DataHolder var3 = null;
      ArrayList var4 = null;
      Object var5 = var4;
      boolean var6 = false;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 2) {
            if (var8 != 3) {
               if (var8 != 4) {
                  if (var8 != 5) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var6 = SafeParcelReader.readBoolean(var1, var7);
                  }
               } else {
                  var5 = (com.google.android.gms.drive.zza)SafeParcelReader.createParcelable(var1, var7, com.google.android.gms.drive.zza.CREATOR);
               }
            } else {
               var4 = SafeParcelReader.createTypedList(var1, var7, DriveId.CREATOR);
            }
         } else {
            var3 = (DataHolder)SafeParcelReader.createParcelable(var1, var7, DataHolder.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzff(var3, var4, (com.google.android.gms.drive.zza)var5, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzff[var1];
   }
}
